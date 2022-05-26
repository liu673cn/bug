package com.github.catvod.parser;

import android.util.Base64;

import com.github.catvod.crawler.SpiderDebug;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class MixDemo {

    /*
    "parses": [
    {
      "name": "聚合",
      "type": 3,
      "url": "Demo"
    },
    {
      "name": "解析",
      "type": 1,
      "url": "https://192.168.10.88/jx.php?url=",
      "ext": {
        "flag": [
          "qq",
          "iqiyi",
          "qiyi",
          "爱奇艺",
          "腾讯",
          "letv",
          "sohu",
          "tudou",
          "pptv",
          "mgtv",
          "wasu",
          "bilibili"
        ]
      }
     }]
     */

    public static HashMap<String, ArrayList<String>> flagWebJx = new HashMap<>();

    static HashMap<String, ArrayList<String>> configs = null;

    public static JSONObject parse(LinkedHashMap<String, HashMap<String, String>> jx, String nameMe, String flag, String url) {
        try {
            if (configs == null) {
                configs = new HashMap<>();
                Iterator<String> keys = jx.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    HashMap<String, String> parseBean = jx.get(key);
                    String type = parseBean.get("type");
                    if (type.equals("1") || type.equals("0")) {
                        try {
                            JSONArray flags = new JSONObject(parseBean.get("ext")).getJSONArray("flag");
                            for (int j = 0; j < flags.length(); j++) {
                                String flagKey = flags.getString(j);
                                ArrayList<String> flagJx = configs.get(flagKey);
                                if (flagJx == null) {
                                    flagJx = new ArrayList<>();
                                    configs.put(flagKey, flagJx);
                                }
                                flagJx.add(key);
                            }
                        } catch (Throwable th) {

                        }
                    }
                }
            }
            // 通过上面的配置获得解析列表
            LinkedHashMap<String, String> jsonJx = new LinkedHashMap<>();
            ArrayList<String> webJx = new ArrayList<>();
            ArrayList<String> flagJx = configs.get(flag);
            if (flagJx != null && !flagJx.isEmpty()) {
                for (int i = 0; i < flagJx.size(); i++) {
                    String key = flagJx.get(i);
                    HashMap<String, String> parseBean = jx.get(key);
                    String type = parseBean.get("type");
                    if (type.equals("1")) {
                        jsonJx.put(key, mixUrl(parseBean.get("url"), parseBean.get("ext")));
                    } else if (type.equals("0")) {
                        webJx.add(parseBean.get("url"));
                    }
                }
            } else {
                Iterator<String> keys = jx.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    HashMap<String, String> parseBean = jx.get(key);
                    String type = parseBean.get("type");
                    if (type.equals("1")) {
                        jsonJx.put(key, mixUrl(parseBean.get("url"), parseBean.get("ext")));
                    } else if (type.equals("0")) {
                        webJx.add(parseBean.get("url"));
                    }
                }
            }
            if (!webJx.isEmpty()) {
                flagWebJx.put(flag, webJx);
            }
            // 优先使用json并发解析
            JSONObject jsonResult = JsonParallel.parse(jsonJx, url);
            if (jsonResult != null && jsonResult.has("url")) {
                return jsonResult;
            }
            // json解析没有得到结果 用webview解析
            if (!webJx.isEmpty()) {
                JSONObject webResult = new JSONObject();
                webResult.put("url", "proxy://do=parseMix&flag=" + flag + "&url=" + Base64.encodeToString(url.getBytes(), Base64.DEFAULT | Base64.URL_SAFE | Base64.NO_WRAP));
                webResult.put("parse", 1);
                return webResult;
            }
        } catch (Throwable th) {
            SpiderDebug.log(th);
        }
        return new JSONObject();
    }

    private static String mixUrl(String url, String ext) {
        if (ext.trim().length() > 0) {
            int idx = url.indexOf("?");
            if (idx > 0) {
                return url.substring(0, idx + 1) + "cat_ext=" + Base64.encodeToString(ext.getBytes(), Base64.DEFAULT | Base64.URL_SAFE | Base64.NO_WRAP) + "&" + url.substring(idx + 1);
            }
        }
        return url;
    }

    public static Object[] loadHtml(String flag, String url) {
        try {
            url = new String(Base64.decode(url, Base64.DEFAULT | Base64.URL_SAFE | Base64.NO_WRAP), "UTF-8");
            String html = "\n" +
                    "<!doctype html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>解析</title>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                    "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE10\" />\n" +
                    "<meta name=\"renderer\" content=\"webkit|ie-comp|ie-stand\">\n" +
                    "<meta name=\"viewport\" content=\"width=device-width\">\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<script>\n" +
                    "var apiArray=[#jxs#];\n" +
                    "var urlPs=\"#url#\";\n" +
                    "var iframeHtml=\"\";\n" +
                    "for(var i=0;i<apiArray.length;i++){\n" +
                    "var URL=apiArray[i]+urlPs;\n" +
                    "iframeHtml=iframeHtml+\"<iframe sandbox='allow-scripts allow-same-origin allow-forms' frameborder='0' allowfullscreen='true' webkitallowfullscreen='true' mozallowfullscreen='true' src=\"+URL+\"></iframe>\";\n" +
                    "}\n" +
                    "document.write(iframeHtml);\n" +
                    "</script>\n" +
                    "</body>\n" +
                    "</html>";

            StringBuilder jxs = new StringBuilder();
            if (flagWebJx.containsKey(flag)) {
                ArrayList<String> jxUrls = flagWebJx.get(flag);
                for (int i = 0; i < jxUrls.size(); i++) {
                    jxs.append("\"");
                    jxs.append(jxUrls.get(i));
                    jxs.append("\"");
                    if (i < jxUrls.size() - 1) {
                        jxs.append(",");
                    }
                }
            }
            html = html.replace("#url#", url).replace("#jxs#", jxs.toString());
            Object[] result = new Object[3];
            result[0] = 200;
            result[1] = "text/html; charset=\"UTF-8\"";
            ByteArrayInputStream baos = new ByteArrayInputStream(html.toString().getBytes("UTF-8"));
            result[2] = baos;
            return result;
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }
}
