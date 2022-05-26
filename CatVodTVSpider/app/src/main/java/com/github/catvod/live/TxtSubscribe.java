package com.github.catvod.live;

import com.github.catvod.utils.okhttp.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class TxtSubscribe {

    public static void subscribe(LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> allLives, String url, HashMap<String, String> headers) {
        String content = OkHttpUtil.string(url, headers);
        parse(allLives, content);
    }

    public static void parse(LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> allLives, String txt) {
        try {
            BufferedReader br = new BufferedReader(new StringReader(txt));
            String line = br.readLine();
            LinkedHashMap<String, ArrayList<String>> noGroup = new LinkedHashMap<>();
            LinkedHashMap<String, ArrayList<String>> groupLives = noGroup;
            while (line != null) {
                if (line.trim().isEmpty()) {
                    line = br.readLine();
                    continue;
                }
                String[] lineInfo = line.split(",");
                if (lineInfo.length < 2) {
                    line = br.readLine();
                    continue;
                }
                if (line.contains("#genre#")) {
                    String group = lineInfo[0].trim();
                    if (!allLives.containsKey(group)) {
                        groupLives = new LinkedHashMap<>();
                        allLives.put(group, groupLives);
                    } else {
                        groupLives = allLives.get(group);
                    }
                } else {
                    String title = lineInfo[0].trim();
                    String[] urlMix = lineInfo[1].trim().split("#");
                    for (int j = 0; j < urlMix.length; j++) {
                        String url = urlMix[j].trim();
                        if (url.isEmpty())
                            continue;
                        if (url.startsWith("http") || url.startsWith("rtsp") || url.startsWith("rtmp")) {
                            ArrayList<String> urls = null;
                            if (!groupLives.containsKey(title)) {
                                urls = new ArrayList<>();
                                groupLives.put(title, urls);
                            } else {
                                urls = groupLives.get(title);
                            }
                            if (!urls.contains(url))
                                urls.add(url);
                        } else {
                            // SpiderDebug.log("Skip " + url);
                        }
                    }
                }
                line = br.readLine();
            }
            br.close();
            if (!noGroup.isEmpty()) {
                allLives.put("未分组", noGroup);
            }
        } catch (Throwable th) {

        }
    }

    public static String live2Json(LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> lives) {
        JSONArray groups = new JSONArray();
        Iterator<String> groupKeys = lives.keySet().iterator();
        while (groupKeys.hasNext()) {
            String group = groupKeys.next();
            JSONArray channels = new JSONArray();
            LinkedHashMap<String, ArrayList<String>> allChannel = lives.get(group);
            if (allChannel.isEmpty())
                continue;
            Iterator<String> channelKeys = allChannel.keySet().iterator();
            while (channelKeys.hasNext()) {
                String channel = channelKeys.next();
                ArrayList<String> allUrls = allChannel.get(channel);
                if (allUrls.isEmpty())
                    continue;
                JSONArray urls = new JSONArray();
                for (int i = 0; i < allUrls.size(); i++) {
                    urls.put(allUrls.get(i));
                }
                JSONObject newChannel = new JSONObject();
                try {
                    newChannel.put("name", channel);
                    newChannel.put("urls", urls);
                } catch (JSONException e) {
                }
                channels.put(newChannel);
            }
            JSONObject newGroup = new JSONObject();
            try {
                newGroup.put("group", group);
                newGroup.put("channels", channels);
            } catch (JSONException e) {
            }
            groups.put(newGroup);
        }
        return groups.toString();
    }

    public static Object[] load(String ext) {
        try {
            LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> allLives = new LinkedHashMap<>();
            TxtSubscribe.subscribe(allLives, ext, null);
            String json = TxtSubscribe.live2Json(allLives);
            Object[] result = new Object[3];
            result[0] = 200;
            result[1] = "text/plain; charset=utf-8";
            ByteArrayInputStream baos = new ByteArrayInputStream(json.getBytes("UTF-8"));
            result[2] = baos;
            return result;
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }
}
