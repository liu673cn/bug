package com.github.catvod.spider;

import android.util.Base64;

import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.okhttp.OKCallBack;
import com.github.catvod.utils.okhttp.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 需配合猫大的解密接口使用，没有接口不用尝试。
 */

public class XPathBde4 extends XPath {

    String tk = "";

    @Override
    protected void loadRuleExt(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            tk = jsonObj.optString("decodeTk").trim();
        } catch (JSONException e) {
            SpiderDebug.log(e);
        }
    }

    @Override
    protected String categoryUrl(String tid, String pg, boolean filter, HashMap<String, String> extend) {
        String url = rule.getCateUrl().replace("{catePg}", pg);
        String s = "all";
        if (extend != null) {
            if (extend.containsKey("s")) {
                s = extend.get("s");
            }
        }
        url = url.replace("{cateId}", s);
        url += "?";
        if (extend != null && extend.size() > 0) {
            for (Iterator<String> it = extend.keySet().iterator(); it.hasNext(); ) {
                String key = it.next();
                if (key.equals("s"))
                    continue;
                String value = extend.get(key);
                if (value.length() > 0) {
                    url += (key + "=" + URLEncoder.encode(value) + "&");
                }
            }
        }
        if (!tid.equals("*")) {
            url += ("type=" + tid);
        }
        return url;
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) {
        try {
            fetchRule();
            String webUrl = rule.getPlayUrl().isEmpty() ? id : rule.getPlayUrl().replace("{playUrl}", id);
            SpiderDebug.log(webUrl);
            String content = OkHttpUtil.string(webUrl, getHeaders(webUrl));
            String startFlag = "var m3u8 = \"";
            int start = content.indexOf(startFlag);
            start = start + startFlag.length();
            int end = content.indexOf("\";", start);
            String m3u8 = content.substring(start, end).replace("\\", "").replace("https", "http");
            SpiderDebug.log(m3u8);
            HashMap<String, String> headers = getHeaders(m3u8);

            OKCallBack<String> m3u8Callback = new OKCallBack<String>() {
                @Override
                public String onParseResponse(Call call, Response response) {
                    try {
                        return new String(Base64.encode(response.body().bytes(), Base64.DEFAULT));
                    } catch (IOException e) {
                        return "";
                    }
                }

                @Override
                public void onFailure(Call call, Exception e) {
                    setResult("");
                    SpiderDebug.log(e);
                }

                @Override
                public void onResponse(String response) {
                }
            };
            OkHttpUtil.get(OkHttpUtil.defaultClient(), m3u8, null, headers, m3u8Callback);
            String m3u8Data = m3u8Callback.getResult();
            if (m3u8Data != null && !m3u8Data.isEmpty()) {
                JSONObject result = new JSONObject();
                JSONObject json = new JSONObject();
                json.put("data", m3u8Data);
                json.put("t", tk);
                OkHttpUtil.postJson(OkHttpUtil.defaultClient(), "https://cat.idontcare.top/ssr/bde4", json.toString(), new OKCallBack.OKCallBackString() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            result.put("url", response);
                        } catch (JSONException e) {
                        }
                    }
                });
                result.put("parse", 0);
                result.put("playUrl", "");
                if (!rule.getPlayUa().isEmpty()) {
                    result.put("ua", rule.getPlayUa());
                }
                return result.toString();

            }
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }
}
