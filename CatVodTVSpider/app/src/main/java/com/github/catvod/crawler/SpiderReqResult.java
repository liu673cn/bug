package com.github.catvod.crawler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpiderReqResult {
    SpiderReqResult(Map<String, List<String>> headers, String content) {
        this.headers = headers;
        this.content = content;
    }

    public static SpiderReqResult empty = new SpiderReqResult(new HashMap<String, List<String>>(), "");

    public Map<String, List<String>> headers;
    public String content;
}
