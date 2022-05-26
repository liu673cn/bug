package com.github.catvod.utils;

import android.net.Uri;

import com.github.catvod.crawler.SpiderDebug;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Misc {
    public static boolean isVip(String url) {
        // 适配2.0.6的调用应用内解析列表的支持, 需要配合直连分析一起使用，参考cjt影视和极品直连
        try {
            boolean isVip = false;
            String host = Uri.parse(url).getHost();
            String[] vipWebsites = new String[]{"iqiyi.com", "v.qq.com", "youku.com", "le.com", "tudou.com", "mgtv.com", "sohu.com", "acfun.cn", "bilibili.com", "baofeng.com", "pptv.com"};
            for (int b = 0; b < vipWebsites.length; b++) {
                if (host.contains(vipWebsites[b])) {
                    if ("iqiyi.com".equals(vipWebsites[b])) {
                        //爱奇艺需要特殊处理
                        if (url.contains("iqiyi.com/a_") || url.contains("iqiyi.com/w_") || url.contains("iqiyi.com/v_")) {
                            isVip = true;
                            break;
                        }
                    } else {
                        isVip = true;
                        break;
                    }
                }
            }
            return isVip;
        } catch (Exception e) {
        }
        return false;
    }

    private static final Pattern snifferMatch = Pattern.compile("http((?!http).){26,}?\\.(m3u8|mp4)\\?.*|http((?!http).){26,}\\.(m3u8|mp4)|http((?!http).){26,}?/m3u8\\?pt=m3u8.*|http((?!http).)*?default\\.ixigua\\.com/.*|http((?!http).)*?cdn-tos[^\\?]*|http((?!http).)*?/obj/tos[^\\?]*|http.*?/player/m3u8play\\.php\\?url=.*|http.*?/player/.*?[pP]lay\\.php\\?url=.*|http.*?/playlist/m3u8/\\?vid=.*|http.*?\\.php\\?type=m3u8&.*|http.*?/download.aspx\\?.*|http.*?/api/up_api.php\\?.*|https.*?\\.66yk\\.cn.*|http((?!http).)*?netease\\.com/file/.*");

    public static boolean isVideoFormat(String url) {
        if (snifferMatch.matcher(url).find()) {
            if (url.contains("cdn-tos") && url.contains(".js")) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static String fixUrl(String base, String src) {
        try {
            if (src.startsWith("//")) {
                Uri parse = Uri.parse(base);
                src = parse.getScheme() + ":" + src;
            } else if (!src.contains("://")) {
                Uri parse = Uri.parse(base);
                src = parse.getScheme() + "://" + parse.getHost() + src;
            }
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return src;
    }

    public static boolean isBlackVodUrl(String input, String url) {
        if (url.contains("973973.xyz") || url.contains(".fit:"))
            return true;
        return false;
    }

    public static final String UaWinChrome = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.54 Safari/537.36";

    public static JSONObject fixJsonVodHeader(JSONObject headers, String input, String url) throws JSONException {
        if (headers == null)
            headers = new JSONObject();
        if (input.contains("www.mgtv.com")) {
            headers.put("Referer", " ");
            headers.put("User-Agent", " Mozilla/5.0");
        } else if (url.contains("titan.mgtv")) {
            headers.put("Referer", " ");
            headers.put("User-Agent", " Mozilla/5.0");
        } else if (input.contains("bilibili")) {
            headers.put("Referer", " https://www.bilibili.com/");
            headers.put("User-Agent", " " + Misc.UaWinChrome);
        }
        return headers;
    }

    public static JSONObject jsonParse(String input, String json) throws JSONException {
        JSONObject jsonPlayData = new JSONObject(json);
        String url = jsonPlayData.getString("url");
        if (url.startsWith("//")) {
            url = "https:" + url;
        }
        if (!url.startsWith("http")) {
            return null;
        }
        if (url.equals(input)) {
            if (isVip(url) || !isVideoFormat(url)) {
                return null;
            }
        }
        if (Misc.isBlackVodUrl(input, url)) {
            return null;
        }
        JSONObject headers = new JSONObject();
        String ua = jsonPlayData.optString("user-agent", "");
        if (ua.trim().length() > 0) {
            headers.put("User-Agent", " " + ua);
        }
        String referer = jsonPlayData.optString("referer", "");
        if (referer.trim().length() > 0) {
            headers.put("Referer", " " + referer);
        }
        headers = Misc.fixJsonVodHeader(headers, input, url);
        JSONObject taskResult = new JSONObject();
        taskResult.put("header", headers);
        taskResult.put("url", url);
        return taskResult;
    }
}
