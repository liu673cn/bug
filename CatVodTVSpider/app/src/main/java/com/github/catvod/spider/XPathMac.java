package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.Misc;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XPathMac extends XPath {
    // 尝试分析直连
    private boolean decodePlayUrl;
    // 尝试匹配官源标识以调用应用配置中的解析列表
    private boolean decodeVipFlag;
    // 播放器配置js
    private String playerConfigJs = "";
    // 播放器配置js取值正则
    private String playerConfigJsRegex = "[\\W|\\S|.]*?MacPlayerConfig.player_list[\\W|\\S|.]*?=([\\W|\\S|.]*?),MacPlayerConfig.downer_list";
    // 站点里播放源对应的真实官源
    private HashMap<String, String> show2VipFlag = new HashMap<>();

    /**
     * mac cms 直连和官源调用应用内播放列表支持
     *
     * @param context
     * @param extend
     */
    public void init(Context context, String extend) {
        super.init(context, extend);

    }

    @Override
    protected void loadRuleExt(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            decodePlayUrl = jsonObj.optBoolean("dcPlayUrl", false);
            decodeVipFlag = jsonObj.optBoolean("dcVipFlag", false);
            JSONObject dcShow2Vip = jsonObj.optJSONObject("dcShow2Vip");
            if (dcShow2Vip != null) {
                Iterator<String> keys = dcShow2Vip.keys();
                while (keys.hasNext()) {
                    String name = keys.next();
                    show2VipFlag.put(name.trim(), dcShow2Vip.getString(name).trim());
                }
            }
            playerConfigJs = jsonObj.optString("pCfgJs").trim();
            playerConfigJsRegex = jsonObj.optString("pCfgJsR", playerConfigJsRegex).trim();
        } catch (JSONException e) {
            SpiderDebug.log(e);
        }
    }

    @Override
    public String homeContent(boolean filter) {
        String result = super.homeContent(filter);
        if (result.length() > 0 && playerConfigJs.length() > 0) { // 尝试通过playerConfigJs获取展示和flag匹配关系
            String webContent = fetch(playerConfigJs);
            Matcher matcher = Pattern.compile(playerConfigJsRegex).matcher(webContent);
            if (matcher.find()) {
                try {
                    JSONObject jsonObject = new JSONObject(matcher.group(1));
                    Iterator<String> keys = jsonObject.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        JSONObject keyObj = jsonObject.optJSONObject(key);
                        if (keyObj == null)
                            continue;
                        String show = keyObj.optString("show").trim();
                        if (show.isEmpty())
                            continue;
                        show2VipFlag.put(show, key);
                    }
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
            // SpiderDebug.log(webContent);
        }
        return result;
    }

    @Override
    public String detailContent(List<String> ids) {
        String result = super.detailContent(ids);
        if (decodeVipFlag && result.length() > 0) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String playFrom[] = jsonObject.optJSONArray("list").getJSONObject(0).optString("vod_play_from").split("\\$\\$\\$");
                if (playFrom.length > 0) {
                    for (int i = 0; i < playFrom.length; i++) {
                        if (show2VipFlag.containsKey(playFrom[i])) {
                            playFrom[i] = show2VipFlag.get(playFrom[i]);
                        }
                    }
                    jsonObject.optJSONArray("list").getJSONObject(0).put("vod_play_from", TextUtils.join("$$$", playFrom));
                    result = jsonObject.toString();
                }
            } catch (Throwable th) {
                SpiderDebug.log(th);
            }
        }
        return result;
    }


    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) {
        fetchRule();
        String webUrl = rule.getPlayUrl().isEmpty() ? id : rule.getPlayUrl().replace("{playUrl}", id);
        String videoUrl = null;
        // 尝试分析直连
        if (decodePlayUrl) {
            try {
                Document doc = Jsoup.parse(fetch(webUrl));
                Elements allScript = doc.select("script");
                for (int i = 0; i < allScript.size(); i++) {
                    String scContent = allScript.get(i).html().trim();
                    if (scContent.startsWith("var player_")) {
                        int start = scContent.indexOf('{');
                        int end = scContent.lastIndexOf('}') + 1;
                        String json = scContent.substring(start, end);
                        JSONObject player = new JSONObject(json);
                        String videoUrlTmp = player.getString("url");
                        if (player.has("encrypt")) {
                            int encrypt = player.getInt("encrypt");
                            if (encrypt == 1) {
                                videoUrlTmp = URLDecoder.decode(videoUrlTmp);
                            } else if (encrypt == 2) {
                                videoUrlTmp = new String(Base64.decode(videoUrlTmp, Base64.DEFAULT));
                                videoUrlTmp = URLDecoder.decode(videoUrlTmp);
                            }
                        }
                        videoUrl = videoUrlTmp;
                        break;
                    }
                }
            } catch (Exception e) {
                SpiderDebug.log(e);
            }
        }
        if (videoUrl != null) {
            // 适配2.0.6的调用应用内解析列表的支持, 需要配合直连分析和匹配官源解析一起使用，参考cjt影视和极品直连
            if (decodeVipFlag && Misc.isVip(videoUrl)) { // 使用jx:1
                try {
                    JSONObject result = new JSONObject();
                    result.put("parse", 1);
                    result.put("jx", "1");
                    result.put("url", videoUrl);
                    return result.toString();
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            } else if (decodeVipFlag && vipFlags.contains(flag)) { // 是否使用应用内解析列表解析官源
                try {
                    JSONObject result = new JSONObject();
                    result.put("parse", 1);
                    result.put("playUrl", "");
                    result.put("url", videoUrl);
                    result.put("header", "");
                    return result.toString();
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
            // 如果是视频直连 直接返回免解
            else if (isVideoFormat(videoUrl)) {
                try {
                    JSONObject result = new JSONObject();
                    result.put("parse", 0);
                    result.put("playUrl", "");
                    result.put("url", videoUrl);
                    result.put("header", "");
                    return result.toString();
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
        }
        // 上述都失败了就按默认模式走
        return super.playerContent(flag, id, vipFlags);
    }
}
