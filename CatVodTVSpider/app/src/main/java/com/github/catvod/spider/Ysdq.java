package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;

import com.github.catvod.crawler.Spider;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.okhttp.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Ysdq extends Spider {
    private static HashMap<String, JSONObject> sites = new HashMap<>();

    protected void fetchRule() {
        if (sites.size() == 0) {
            try {
                String json = OkHttpUtil.string("https://pj567.coding.net/p/source/d/source/git/raw/master/mobile/config.json", null);
                JSONArray sources = new JSONObject(json).optJSONArray("source");
                for (int i = 0; i < sources.length(); i++) {
                    JSONObject obj = sources.getJSONObject(i);
                    String type = obj.optString("type");
                    if (type.equals("AppV0") || type.equals("AppTv") || type.equals("aiKanTv")) {
                        String scName = obj.optString("sourceName");
                        sites.put(scName, obj);
                        SpiderDebug.log("{\"key\":\"csp_ysdq_" + scName + "\", \"name\":\"" + scName + "(搜)\", \"type\":3, \"api\":\"csp_Ysdq\",\"searchable\":1,\"quickSearch\":0, \"ext\":\"" + scName + "\"},");
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    private JSONObject getJson() {
        if (sites.containsKey(sourceName)) {
            return sites.get(sourceName);
        }
        return null;
    }

    private String sourceName = "";

    @Override
    public void init(Context context, String extend) {
        super.init(context, extend);
        this.sourceName = extend;
    }

    private String jsonArr2Str(JSONArray array) {
        try {
            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                strings.add(array.getString(i));
            }
            return TextUtils.join(",", strings);
        } catch (JSONException e) {
        }
        return "";
    }

    @Override
    public String detailContent(List<String> ids) {
        try {
            fetchRule();
            JSONObject cfg = getJson();
            if (cfg != null) {
                String type = cfg.optString("type");
                JSONObject result = new JSONObject();
                JSONObject vod = new JSONObject();
                if (type.equals("AppV0")) {
                    String url = cfg.optString("detailUrl").replace("%s", ids.get(0));
                    String json = OkHttpUtil.string(url, null);
                    JSONObject obj = new JSONObject(json).optJSONObject("data");
                    vod.put("vod_id", obj.getString("vod_id"));
                    vod.put("vod_name", obj.getString("vod_name"));
                    vod.put("vod_pic", obj.getString("vod_pic"));
                    vod.put("type_name", obj.optString("vod_class"));
                    vod.put("vod_year", obj.optString("vod_year"));
                    vod.put("vod_lang", obj.optString("vod_lang"));
                    vod.put("vod_area", obj.optString("vod_area"));
                    vod.put("vod_remarks", obj.optString("vod_remarks"));
                    vod.put("vod_actor", obj.optString("vod_actor"));
                    vod.put("vod_director", obj.optString("vod_director"));
                    vod.put("vod_content", obj.optString("vod_content"));
                    vod.put("vod_play_from", obj.getString("vod_play_from"));
                    vod.put("vod_play_url", obj.getString("vod_play_url"));
                } else if (type.equals("AppTV")) {
                    String url = ids.get(0);
                    String json = OkHttpUtil.string(url, null);
                    JSONObject obj = new JSONObject(json);
                    vod.put("vod_id", obj.getString("id"));
                    vod.put("vod_name", obj.getString("title"));
                    vod.put("vod_pic", obj.getString("img_url"));
                    vod.put("type_name", jsonArr2Str(obj.optJSONArray("type")));
                    vod.put("vod_year", obj.optString("pubtime"));
                    vod.put("vod_area", jsonArr2Str(obj.optJSONArray("area")));
                    vod.put("vod_remarks", obj.optString("trunk"));
                    vod.put("vod_actor", jsonArr2Str(obj.optJSONArray("actor")));
                    vod.put("vod_director", jsonArr2Str(obj.optJSONArray("director")));
                    vod.put("vod_content", obj.optString("intro"));
                    Map<String, String> vod_play = new LinkedHashMap<>();
                    JSONObject playList = obj.getJSONObject("videolist");
                    Iterator<String> playListKeys = playList.keys();
                    while (playListKeys.hasNext()) {
                        String from = playListKeys.next();
                        JSONArray playListUrls = playList.getJSONArray(from);
                        ArrayList<String> urls = new ArrayList<>();
                        for (int j = 0; j < playListUrls.length(); j++) {
                            JSONObject urlObj = playListUrls.getJSONObject(j);
                            urls.add(urlObj.getString("title") + "$" + urlObj.getString("url"));
                        }
                        vod_play.put(from, TextUtils.join("#", urls));
                    }
                    String vod_play_from = TextUtils.join("$$$", vod_play.keySet());
                    String vod_play_url = TextUtils.join("$$$", vod_play.values());
                    vod.put("vod_play_from", vod_play_from);
                    vod.put("vod_play_url", vod_play_url);
                } else if (type.equals("aiKanTv")) {
                    String url = cfg.optString("detailUrl").replace("%s", ids.get(0));
                    String json = OkHttpUtil.string(url, null);
                    JSONObject obj = new JSONObject(json).optJSONObject("data");
                    vod.put("vod_id", obj.getString("vod_id"));
                    vod.put("vod_name", obj.getString("vod_name"));
                    vod.put("vod_pic", obj.getString("vod_pic"));
                    vod.put("type_name", obj.optString("vod_class"));
                    vod.put("vod_year", obj.optString("vod_year"));
                    vod.put("vod_lang", obj.optString("vod_lang"));
                    vod.put("vod_area", obj.optString("vod_area"));
                    vod.put("vod_remarks", obj.optString("vod_remarks"));
                    vod.put("vod_actor", obj.optString("vod_actor"));
                    vod.put("vod_director", obj.optString("vod_director"));
                    vod.put("vod_content", obj.optString("vod_content"));

                    Map<String, String> vod_play = new LinkedHashMap<>();
                    JSONArray playList = obj.getJSONArray("vod_play_list");
                    for (int i = 0; i < playList.length(); i++) {
                        JSONObject playListObj = playList.getJSONObject(i);
                        String from = playListObj.getString("from");
                        String playUrls = playListObj.getString("url");
                        if (playUrls.length() > 0) {
                            vod_play.put(from, playUrls);
                        }
                    }
                    String vod_play_from = TextUtils.join("$$$", vod_play.keySet());
                    String vod_play_url = TextUtils.join("$$$", vod_play.values());
                    vod.put("vod_play_from", vod_play_from);
                    vod.put("vod_play_url", vod_play_url);
                }
                JSONArray list = new JSONArray();
                list.put(vod);
                result.put("list", list);
                return result.toString();
            }
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public String searchContent(String key, boolean quick) {
        try {
            if (quick)
                return "";
            fetchRule();
            JSONObject cfg = getJson();
            if (cfg != null) {
                String type = cfg.optString("type");
                String url = cfg.optString("searchUrl");
                if (!url.isEmpty()) {
                    JSONObject result = new JSONObject();
                    JSONArray videos = new JSONArray();
                    url = url.replace("%s", URLEncoder.encode(key));
                    String json = OkHttpUtil.string(url, null);
                    if (type.equals("AppV0")) {
                        JSONArray list = new JSONObject(json).optJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject obj = list.getJSONObject(i);
                            JSONObject v = new JSONObject();
                            v.put("vod_id", obj.getString("vod_id"));
                            v.put("vod_name", obj.getString("vod_name"));
                            v.put("vod_pic", obj.getString("vod_pic"));
                            v.put("vod_remarks", obj.getString("vod_remarks"));
                            videos.put(v);
                        }
                    } else if (type.equals("AppTV")) {
                        JSONArray list = new JSONObject(json).optJSONArray("data");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject obj = list.getJSONObject(i);
                            JSONObject v = new JSONObject();
                            v.put("vod_id", obj.getString("nextlink"));
                            v.put("vod_name", obj.getString("title"));
                            v.put("vod_pic", obj.getString("pic"));
                            v.put("vod_remarks", obj.getString("state"));
                            videos.put(v);
                        }
                    } else if (type.equals("aiKanTv")) {
                        JSONArray list = new JSONObject(json).optJSONObject("data").optJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject obj = list.getJSONObject(i);
                            JSONObject v = new JSONObject();
                            v.put("vod_id", obj.getString("vod_id"));
                            v.put("vod_name", obj.getString("vod_name"));
                            v.put("vod_pic", obj.getString("vod_pic"));
                            v.put("vod_remarks", obj.getString("vod_remarks"));
                            videos.put(v);
                        }
                    }
                    result.put("list", videos);
                    return result.toString();
                }
            }
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) {
        try {
            fetchRule();
            if (vipFlags.contains(flag)) {
                try {
                    JSONObject result = new JSONObject();
                    result.put("parse", 1);
                    result.put("playUrl", "");
                    result.put("url", id);
                    result.put("header", "");
                    return result.toString();
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
            // 如果是视频直连 直接返回免解
            else if (isVideoFormat(id)) {
                try {
                    JSONObject result = new JSONObject();
                    result.put("parse", 0);
                    result.put("playUrl", "");
                    result.put("url", id);
                    result.put("header", "");
                    return result.toString();
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        // 尝试解析
        try {
            JSONObject result = new JSONObject();
            result.put("parse", 1);
            result.put("playUrl", "");
            result.put("url", id);
            result.put("header", "");
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }
}
