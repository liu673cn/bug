package com.github.catvod.spider;

import com.github.catvod.crawler.Spider;
import com.github.catvod.utils.Misc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Author: @SDL
 */
public class PushAgent extends Spider {
    @Override
    public String detailContent(List<String> ids) {
        try {
            String url = ids.get(0);
            if (Misc.isVip(url)) {
                JSONObject result = new JSONObject();
                JSONArray list = new JSONArray();
                JSONObject vodAtom = new JSONObject();
                vodAtom.put("vod_id", url);
                vodAtom.put("vod_name", url);
                vodAtom.put("vod_pic", "https://pic.rmb.bdstatic.com/bjh/1d0b02d0f57f0a42201f92caba5107ed.jpeg");
                vodAtom.put("type_name", "官源");
                vodAtom.put("vod_year", "");
                vodAtom.put("vod_area", "");
                vodAtom.put("vod_remarks", "");
                vodAtom.put("vod_actor", "");
                vodAtom.put("vod_director", "");
                vodAtom.put("vod_content", "");
                vodAtom.put("vod_play_from", "jx");
                vodAtom.put("vod_play_url", "立即播放$" + url);
                list.put(vodAtom);
                result.put("list", list);
                return result.toString();
            } else if (Misc.isVideoFormat(url)) {
                JSONObject result = new JSONObject();
                JSONArray list = new JSONArray();
                JSONObject vodAtom = new JSONObject();
                vodAtom.put("vod_id", url);
                vodAtom.put("vod_name", url);
                vodAtom.put("vod_pic", "https://pic.rmb.bdstatic.com/bjh/1d0b02d0f57f0a42201f92caba5107ed.jpeg");
                vodAtom.put("type_name", "直连");
                vodAtom.put("vod_play_from", "player");
                vodAtom.put("vod_play_url", "立即播放$" + url);
                list.put(vodAtom);
                result.put("list", list);
                return result.toString();
            } else if (url.startsWith("http://") || url.startsWith("https://")) {
                JSONObject result = new JSONObject();
                JSONArray list = new JSONArray();
                JSONObject vodAtom = new JSONObject();
                vodAtom.put("vod_id", url);
                vodAtom.put("vod_name", url);
                vodAtom.put("vod_pic", "https://pic.rmb.bdstatic.com/bjh/1d0b02d0f57f0a42201f92caba5107ed.jpeg");
                vodAtom.put("type_name", "网页");
                vodAtom.put("vod_play_from", "parse");
                vodAtom.put("vod_play_url", "立即播放$" + url);
                list.put(vodAtom);
                result.put("list", list);
                return result.toString();
            }
        } catch (Throwable throwable) {

        }
        return "";
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) {
        try {
            if (flag.equals("jx")) {
                JSONObject result = new JSONObject();
                result.put("parse", 1);
                result.put("jx", "1");
                result.put("url", id);
                return result.toString();
            } else if (flag.equals("parse")) {
                JSONObject result = new JSONObject();
                result.put("parse", 1);
                result.put("playUrl", "");
                result.put("url", id);
                return result.toString();
            } else if (flag.equals("player")) {
                JSONObject result = new JSONObject();
                result.put("parse", 0);
                result.put("playUrl", "");
                result.put("url", id);
                return result.toString();
            }
        } catch (Throwable throwable) {

        }
        return "";
    }
}
