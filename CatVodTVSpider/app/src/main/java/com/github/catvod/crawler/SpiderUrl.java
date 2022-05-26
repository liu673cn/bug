package com.github.catvod.crawler;

import java.util.HashMap;

public class SpiderUrl {

    public static SpiderUrl Empty = new SpiderUrl();

    public String url = "";
    public HashMap<String, String> headers = new HashMap<>();

    public SpiderUrl() {
    }

    public SpiderUrl(String url, HashMap<String, String> headers) {
        this.url = url;
        if (headers != null)
            this.headers = headers;
    }
}
