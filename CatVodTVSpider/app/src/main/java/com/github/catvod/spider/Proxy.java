package com.github.catvod.spider;

import android.util.Base64;

import com.github.catvod.crawler.Spider;
import com.github.catvod.live.TxtSubscribe;

import java.util.Map;

public class Proxy extends Spider {

    public static Object[] proxy(Map<String, String> params) {
        try {
            String what = params.get("do");
            if (what.equals("nekk")) {
                String pic = params.get("pic");
                return Nekk.loadPic(pic);
            } else if (what.equals("live")) {
                String type = params.get("type");
                if (type.equals("txt")) {
                    String ext = params.get("ext");
                    ext = new String(Base64.decode(ext, Base64.DEFAULT | Base64.URL_SAFE | Base64.NO_WRAP), "UTF-8");
                    return TxtSubscribe.load(ext);
                }
            }
        } catch (Throwable th) {

        }
        return null;
    }

}
