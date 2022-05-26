package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;

import com.github.catvod.crawler.Spider;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.Misc;
import com.github.catvod.utils.okhttp.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 影视工厂
 * <p>
 * Author: 小黄瓜
 */
public class Ysgc extends Spider {

    private static final String siteUrl = "https://www.ik4.cc";
    private static final String siteHost = "www.ik4.cc";

    protected JSONObject playerConfig = new JSONObject();

    @Override
    public void init(Context context) {
        super.init(context);
    }

    protected HashMap<String, String> getHeaders(String url) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("user-agent", "Dart/2.14 (dart:io)");
        headers.put("version", "1.9.8");
        headers.put("copyright", "xiaogui");
        headers.put("host", siteHost);
        headers.put("platform", "android");
        return headers;
    }

    @Override
    public String homeContent(boolean filter) {
        try {
            String url = siteUrl + "/xgapp.php/v1/nav?token=";
            JSONObject jsonObject = new JSONObject(OkHttpUtil.string(url, getHeaders(url)));
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONArray classes = new JSONArray();
            JSONObject filterConfig = new JSONObject();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObj = jsonArray.getJSONObject(i);
                String typeName = jObj.getString("type_name");
                if (typeName.equals("电视直播"))
                    continue;
                String typeId = jObj.getString("type_id");
                JSONObject newCls = new JSONObject();
                newCls.put("type_id", typeId);
                newCls.put("type_name", typeName);
                classes.put(newCls);
                try {
                    JSONObject typeExtend = jObj.getJSONObject("type_extend");
                    Iterator<String> typeExtendKeys = typeExtend.keys();
                    JSONArray extendsAll = new JSONArray();
                    while (typeExtendKeys.hasNext()) {
                        String typeExtendKey = typeExtendKeys.next();
                        String typeExtendName = null;
                        switch (typeExtendKey) {
                            case "class":
                                typeExtendName = "类型";
                                break;
                            case "area":
                                typeExtendName = "地区";
                                break;
                            case "lang":
                                typeExtendName = "语言";
                                break;
                            case "year":
                                typeExtendName = "年代";
                                break;
                        }
                        if (typeExtendName == null) {
                            SpiderDebug.log(typeExtendKey);
                            continue;
                        }
                        String typeExtendStr = typeExtend.getString(typeExtendKey);
                        if (typeExtendStr.trim().length() == 0)
                            continue;
                        String[] newTypeExtendKeys = typeExtendStr.split(",");
                        JSONObject newTypeExtend = new JSONObject();
                        newTypeExtend.put("key", typeExtendKey);
                        newTypeExtend.put("name", typeExtendName);
                        JSONArray newTypeExtendKV = new JSONArray();
                        {
                            JSONObject kvAll = new JSONObject();
                            kvAll.put("n", "全部");
                            kvAll.put("v", "");
                            newTypeExtendKV.put(kvAll);
                        }
                        for (String k : newTypeExtendKeys) {
                            if (typeExtendName.equals("伦理"))
                                continue;
                            JSONObject kv = new JSONObject();
                            kv.put("n", k);
                            kv.put("v", k);
                            newTypeExtendKV.put(kv);
                        }
                        newTypeExtend.put("value", newTypeExtendKV);
                        extendsAll.put(newTypeExtend);
                    }
                    filterConfig.put(typeId, extendsAll);
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }

            }
            JSONObject result = new JSONObject();
            result.put("class", classes);
            if (filter) {
                result.put("filters", filterConfig);
            }
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public String homeVideoContent() {
        try {
            String url = siteUrl + "/xgapp.php/v1/index_video?token=";
            JSONObject jsonObject = new JSONObject(OkHttpUtil.string(url, getHeaders(url)));
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONArray videos = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObj = jsonArray.getJSONObject(i);
                JSONArray videoList = jObj.getJSONArray("vlist");
                for (int j = 0; j < videoList.length() && j < 6; j++) {
                    JSONObject vObj = videoList.getJSONObject(j);
                    JSONObject v = new JSONObject();
                    v.put("vod_id", vObj.getString("vod_id"));
                    v.put("vod_name", vObj.getString("vod_name"));
                    v.put("vod_pic", vObj.getString("vod_pic"));
                    v.put("vod_remarks", vObj.getString("vod_remarks"));
                    videos.put(v);
                }
            }
            JSONObject result = new JSONObject();
            result.put("list", videos);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) {
        try {
            String url = siteUrl + "/xgapp.php/v1/video?tid=" + tid + "&pg=" + pg + "&token=";
            Set<String> keys = extend.keySet();
            for (String key : keys) {
                url += "&" + key + "=" + URLEncoder.encode(extend.get(key));
            }
            JSONObject dataObject = new JSONObject(OkHttpUtil.string(url, getHeaders(url)));
            JSONArray jsonArray = dataObject.getJSONArray("data");
            JSONArray videos = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vObj = jsonArray.getJSONObject(i);
                JSONObject v = new JSONObject();
                v.put("vod_id", vObj.getString("vod_id"));
                v.put("vod_name", vObj.getString("vod_name"));
                v.put("vod_pic", vObj.getString("vod_pic"));
                v.put("vod_remarks", vObj.getString("vod_remarks"));
                videos.put(v);
            }
            JSONObject result = new JSONObject();
            int limit = 20;
            int page = dataObject.getInt("page");
            int total = dataObject.getInt("total");
            int pageCount = dataObject.getInt("pagecount");
            result.put("page", page);
            result.put("pagecount", pageCount);
            result.put("limit", limit);
            result.put("total", total);
            result.put("list", videos);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public String detailContent(List<String> ids) {
        try {
            String url = siteUrl + "/xgapp.php/v1/video_detail?id=" + ids.get(0) + "&token=";
            JSONObject jsonObject = new JSONObject(OkHttpUtil.string(url, getHeaders(url)));
            JSONObject dataObject = jsonObject.getJSONObject("data");
            if (dataObject.has("vod_info")) {
                try {
                    dataObject = dataObject.getJSONObject("vod_info");
                } catch (Exception e) {

                }
            }
            JSONObject vodList = new JSONObject();
            vodList.put("vod_id", dataObject.getString("vod_id"));
            vodList.put("vod_name", dataObject.getString("vod_name"));
            vodList.put("vod_pic", dataObject.getString("vod_pic"));
            vodList.put("type_name", dataObject.getString("vod_class"));
            vodList.put("vod_year", dataObject.getString("vod_year"));
            vodList.put("vod_area", dataObject.getString("vod_area"));
            vodList.put("vod_remarks", dataObject.getString("vod_remarks"));
            vodList.put("vod_actor", dataObject.getString("vod_actor"));
            vodList.put("vod_director", dataObject.getString("vod_director"));
            vodList.put("vod_content", dataObject.getString("vod_content"));
            JSONArray playerList = dataObject.getJSONArray("vod_url_with_player");
            List<String> playFlags = new ArrayList<>();
            HashMap<String, String> playUrls = new HashMap<>();
            for (int i = 0; i < playerList.length(); i++) {
                JSONObject playerListObj = playerList.getJSONObject(i);
                String from = playerListObj.getString("code");
                playUrls.put(from, playerListObj.getString("url"));
                playerListObj.remove("url");
                playerConfig.put(from, playerListObj);
                playFlags.add(from);
            }

            Map<String, String> vod_play = new TreeMap<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    int sort1 = playFlags.indexOf(o1);
                    int sort2 = playFlags.indexOf(o2);

                    if (sort1 == sort2) {
                        return 1;
                    }
                    return sort1 - sort2 > 0 ? 1 : -1;
                }
            });

            String[] vod_play_from_list = dataObject.getString("vod_play_from").split("\\$\\$\\$");
            String[] vod_play_url_list = dataObject.getString("vod_play_url").split("\\$\\$\\$");

            for (int i = 0; i < vod_play_from_list.length; i++) {
                String from = vod_play_from_list[i];
                if (i >= vod_play_url_list.length || vod_play_url_list[i].trim().length() == 0) {
                    if (playUrls.containsKey(from) && playUrls.get(from).trim().length() > 0) {
                        vod_play.put(vod_play_from_list[i], playUrls.get(from));
                    }
                    continue;
                }
                vod_play.put(vod_play_from_list[i], vod_play_url_list[i]);
            }

            String vod_play_from = TextUtils.join("$$$", vod_play.keySet());
            String vod_play_url = TextUtils.join("$$$", vod_play.values());
            vodList.put("vod_play_from", vod_play_from);
            vodList.put("vod_play_url", vod_play_url);

            JSONObject result = new JSONObject();
            JSONArray list = new JSONArray();
            list.put(vodList);
            result.put("list", list);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    private HashMap<String, String> getHeaderJxs(String url) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("platform_version", "LMY47I");
        headers.put("user-agent", "Dart/2.12 (dart:io)");
        headers.put("version", "1.6.4");
        headers.put("copyright", "xiaogui");
        headers.put("platform", "android");
        headers.put("client_name", "6L+95Ymn6L6+5Lq6");
        return headers;
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) {
        try {
            JSONObject result = new JSONObject();
            if (id.contains("hsl.ysgc.xyz")) {
                String uuu = "https://www.ysgc.cc/static/player/dplayer.php?url=" + id;
                HashMap<String, String> headers = new HashMap();
                headers.put("referer", "https://www.ysgc.cc/");
                String content = OkHttpUtil.string(uuu, headers);
                Matcher matcher = Pattern.compile("url: url\\+'(.+?)',").matcher(content);
                if (matcher.find()) {
                    result.put("parse", 0);
                    result.put("playUrl", "");
                    result.put("url", id + matcher.group(1));
                    result.put("header", "{\"Referer\":\" https://www.ysgc.cc\"}");
                } else {
                    result.put("parse", 1);
                    result.put("playUrl", "");
                    result.put("url", uuu);
                    result.put("header", "{\"Referer\":\"https://www.ysgc.cc/\"}");
                }
                return result.toString();
            } else if (id.contains("duoduozy.com") || id.contains("m3u8.cache.suoyo.cc")) {
                String uuu = "https://www.6080kan.cc/app.php?url=" + id;
                String content = OkHttpUtil.string(uuu, null);
                return Misc.jsonParse(id, content).toString();
            }
            if (vipFlags.contains(flag)) {
                try {
                    result.put("parse", 1);
                    result.put("playUrl", "");
                    result.put("url", id);
                    return result.toString();
                } catch (Exception ee) {
                    SpiderDebug.log(ee);
                }
            }
            JSONObject playerObj = playerConfig.getJSONObject(flag);
            String parseUrl = playerObj.getString("parse_api") + id;
            String content = OkHttpUtil.string(parseUrl, getHeaderJxs(parseUrl));
            JSONObject playerData = new JSONObject(content);
            JSONObject headers = new JSONObject();
            String ua = playerData.optString("user-agent", "");
            if (ua.trim().length() > 0) {
                headers.put("User-Agent", " " + ua);
            }
            String referer = playerData.optString("referer", "");
            if (referer.trim().length() > 0) {
                headers.put("Referer", " " + referer);
            }
            result.put("parse", 0);
            result.put("header", headers.toString());
            result.put("playUrl", "");
            result.put("url", playerData.getString("url"));
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
            if (vipFlags.contains(flag)) {
                try {
                    JSONObject result = new JSONObject();
                    result.put("parse", 1);
                    result.put("playUrl", "");
                    result.put("url", id);
                    return result.toString();
                } catch (Exception ee) {
                    SpiderDebug.log(ee);
                }
            }
        }
        return "";
    }

    @Override
    public String searchContent(String key, boolean quick) {
        if (quick)
            return "";
        try {
            String url = siteUrl + "/xgapp.php/v1/search?text=" + URLEncoder.encode(key) + "&pg=1";
            String content = OkHttpUtil.string(url, getHeaders(url));
            JSONObject dataObject = new JSONObject(content);
            JSONArray jsonArray = dataObject.getJSONArray("data");
            JSONArray videos = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vObj = jsonArray.getJSONObject(i);
                JSONObject v = new JSONObject();
                v.put("vod_id", vObj.getString("vod_id"));
                v.put("vod_name", vObj.getString("vod_name"));
                v.put("vod_pic", vObj.getString("vod_pic"));
                v.put("vod_remarks", vObj.getString("vod_remarks"));
                videos.put(v);
            }
            JSONObject result = new JSONObject();
            result.put("list", videos);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }
}
