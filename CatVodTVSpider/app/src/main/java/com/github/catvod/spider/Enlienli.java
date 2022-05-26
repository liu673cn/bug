package com.github.catvod.spider;

import android.content.Context;

import com.github.catvod.crawler.Spider;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.okhttp.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 嗯哩嗯哩
 * <p>
 * Author: 群友 不负此生
 */
public class Enlienli extends Spider {

    private String siteUrl = "https://api.app.kongbuya.com";

    @Override
    public void init(Context context) {
        super.init(context);
    }

    private HashMap<String, String> getHeaders(String url) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Connection", "Keep-Alive");
        headers.put("User-Agent", "okhttp/4.0.1");
        return headers;
    }

    @Override
    public String homeContent(boolean filter) {
        try {
            JSONArray jsonArray = new JSONArray(OkHttpUtil.string(siteUrl + "/api.php/provide/home_nav?", getHeaders(siteUrl)));
            JSONArray classes = new JSONArray();
            JSONObject filterConfig = new JSONObject();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObj = jsonArray.getJSONObject(i);
                String typeName = jObj.getString("name");
                String typeId = jObj.getString("id");
                if (typeName.equals("推荐")) {
                    continue;
                }
                JSONObject newCls = new JSONObject();
                newCls.put("type_id", typeId);
                newCls.put("type_name", typeName);
                classes.put(newCls);
                try {
                    JSONArray typeExtend = jObj.getJSONArray("msg");
                    JSONArray extendsAll = new JSONArray();
                    for (int j = 0; j < typeExtend.length(); j++) {
                        JSONObject typeExtendObj = typeExtend.getJSONObject(j);
                        String typeExtendKey = typeExtendObj.getString("name");
                        JSONArray newTypeExtendKeys = typeExtendObj.getJSONArray("data");
                        if (newTypeExtendKeys.length() <= 1)
                            continue;
                        String typeExtendName = newTypeExtendKeys.getString(0);
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
                        for (int k = 1; k < newTypeExtendKeys.length(); k++) {
                            JSONObject kv = new JSONObject();
                            kv.put("n", newTypeExtendKeys.getString(k));
                            kv.put("v", newTypeExtendKeys.getString(k));
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
            String url = siteUrl + "/api.php/provide/home_data?page=1&id=0";
            JSONObject jsonObject = new JSONObject(OkHttpUtil.string(url, getHeaders(url)));
            JSONArray jsonArray = new JSONArray();
            if (jsonObject.has("tv")) {
                JSONArray data = jsonObject.getJSONObject("tv").getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    jsonArray.put(data.getJSONObject(i));
                }
            }

            if (jsonObject.has("video")) {
                JSONArray vs = jsonObject.getJSONArray("video");
                for (int i = 0; i < vs.length(); i++) {
                    JSONArray data = vs.getJSONObject(i).getJSONArray("data");
                    for (int j = 0; j < data.length(); j++) {
                        jsonArray.put(data.getJSONObject(j));
                    }
                }
            }

            JSONArray videos = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vObj = jsonArray.getJSONObject(i);
                JSONObject v = new JSONObject();
                v.put("vod_id", vObj.getString("id"));
                v.put("vod_name", vObj.getString("name"));
                v.put("vod_pic", vObj.getString("img"));
                v.put("vod_remarks", vObj.getString("qingxidu"));
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

    @Override
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) {
        try {
            String url = siteUrl + "/api.php/provide/vod_list?page=" + pg + "&id=" + tid;
            Set<String> keys = extend.keySet();
            for (String key : keys) {
                url += "&" + key + "=" + URLEncoder.encode(extend.get(key));
            }
            JSONArray jsonArray = new JSONArray(OkHttpUtil.string(url, getHeaders(url)));
            JSONArray videos = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vObj = jsonArray.getJSONObject(i);
                JSONObject v = new JSONObject();
                v.put("vod_id", vObj.getString("id"));
                v.put("vod_name", vObj.getString("name"));
                v.put("vod_pic", vObj.getString("img"));
                v.put("vod_remarks", vObj.getString("qingxidu"));
                videos.put(v);
            }
            JSONObject result = new JSONObject();
            int limit = 20;
            int page = Integer.parseInt(pg);
            int total = Integer.MAX_VALUE;
            int pageCount = videos.length() < 20 ? page : page + 1;
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
            String url = siteUrl + "/api.php/provide/vod_detail?token=&id=" + ids.get(0) + "&ac=vod_detail";
            JSONObject jsonObject = new JSONObject(OkHttpUtil.string(url, getHeaders(url)));
            JSONObject vodList = new JSONObject();
            vodList.put("vod_id", ids.get(0));
            vodList.put("vod_name", jsonObject.getString("name"));
            vodList.put("vod_pic", jsonObject.getString("img"));
            vodList.put("type_name", jsonObject.getString("type"));
            vodList.put("vod_year", "");
            vodList.put("vod_area", "");
            vodList.put("vod_remarks", jsonObject.getString("remarks"));
            vodList.put("vod_actor", jsonObject.getString("actor"));
            vodList.put("vod_director", jsonObject.getString("director"));
            vodList.put("vod_content", jsonObject.getString("info"));

            vodList.put("vod_play_from", jsonObject.getString("playcode"));
            vodList.put("vod_play_url", jsonObject.getString("playlist"));

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


    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) {
        try {
            JSONObject result = new JSONObject();
            result.put("parse", 0);
            result.put("playUrl", "");
            result.put("url", id);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public String searchContent(String key, boolean quick) {
        if (quick)
            return "";
        try {
            String url = siteUrl + "/api.php/provide/search_result?video_name=" + URLEncoder.encode(key);
            JSONObject jsonObject = new JSONObject(OkHttpUtil.string(url, getHeaders(url)));
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("search_result");
            JSONArray videos = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vObj = jsonArray.getJSONObject(i);
                JSONObject v = new JSONObject();
                v.put("vod_id", vObj.getString("id"));
                v.put("vod_name", vObj.getString("video_name"));
                v.put("vod_pic", vObj.getString("img"));
                //v.put("vod_remarks", vObj.getString(""));
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
