package com.github.catvod.spider;

import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.okhttp.OkHttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class XPathNfMov extends XPath {

    private String cookies = "";

    @Override
    protected HashMap<String, String> getHeaders(String url) {
        HashMap<String, String> hashMap = super.getHeaders(url);
        if (cookies.length() > 0)
            hashMap.put("Cookie", cookies);
        return hashMap;
    }

    @Override
    protected String fetch(String webUrl) {
        SpiderDebug.log(webUrl);
        Map<String, List<String>> respHeaders = new TreeMap<>();
        String content = OkHttpUtil.string(webUrl, getHeaders(webUrl), respHeaders);
        if (content.contains("http-equiv=\"refresh\"")) {
            if (respHeaders.containsKey("set-cookie")) {
                cookies = respHeaders.get("set-cookie").get(0);
            }
            content = OkHttpUtil.string(webUrl, getHeaders(webUrl));
        }
        return content;
    }
}
