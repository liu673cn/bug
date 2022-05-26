package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;

import com.github.catvod.crawler.Spider;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.okhttp.OKCallBack;
import com.github.catvod.utils.okhttp.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

public class YydsAli1 extends Spider {
    private static final String siteUrl = "https://cmn.yyds.fans";

    @Override
    public void init(Context context) {
        super.init(context);
    }

    private HashMap<String, String> getHeaders(String url) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("origin", "https://yyds.fans");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.54 Safari/537.36");
        return headers;
    }

    @Override
    public String homeContent(boolean filter) {
        try {
            String url = siteUrl + "/api/categories";
            String content = OkHttpUtil.string(url, getHeaders(url));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONArray classes = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObj = jsonArray.getJSONObject(i);
                String typeName = jObj.getString("title");
                if (typeName.equals("首页") || typeName.equals("热门精选")) {
                    continue;
                }
                String typeId = jObj.getString("id");
                JSONObject newCls = new JSONObject();
                newCls.put("type_id", typeId);
                newCls.put("type_name", typeName);
                classes.put(newCls);
            }
            JSONObject result = new JSONObject();
            result.put("class", classes);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public String homeVideoContent() {
        try {
            JSONArray videos = new JSONArray();
            try {
                String url = siteUrl + "/api/posts";
                JSONObject json = new JSONObject();
                json.put("category_id", "0");
                json.put("skip", "0");
                json.put("limit", "30");
                json.put("keyword", "");
                OkHttpUtil.postJson(OkHttpUtil.defaultClient(), url, json.toString(), getHeaders(url), new OKCallBack.OKCallBackString() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject vObj = jsonArray.getJSONObject(i);
                                JSONObject v = new JSONObject();
                                v.put("vod_id", vObj.getString("id"));
                                v.put("vod_name", vObj.getString("title"));
                                v.put("vod_pic", vObj.optString("cover"));
                                String mark = vObj.optString("subtitle");
                                if (mark.equals("null"))
                                    mark = "";
                                v.put("vod_remarks", mark);
                                videos.put(v);
                            }
                        } catch (JSONException e) {
                        }
                    }
                });
            } catch (Exception e) {

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
            int limit = 30;
            int page = Integer.parseInt(pg);
            if (page == 0) {
                page = 1;
            }
            String url = siteUrl + "/api/posts";
            JSONObject json = new JSONObject();
            json.put("category_id", tid);
            json.put("skip", ((page - 1) * limit) + "");
            json.put("limit", limit + "");
            json.put("keyword", "");
            JSONArray videos = new JSONArray();
            OkHttpUtil.postJson(OkHttpUtil.defaultClient(), url, json.toString(), getHeaders(url), new OKCallBack.OKCallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                }

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject vObj = jsonArray.getJSONObject(i);
                            JSONObject v = new JSONObject();
                            v.put("vod_id", vObj.getString("id"));
                            v.put("vod_name", vObj.getString("title"));
                            v.put("vod_pic", vObj.optString("cover"));
                            String mark = vObj.optString("subtitle");
                            if (mark.equals("null"))
                                mark = "";
                            v.put("vod_remarks", mark);
                            videos.put(v);
                        }
                    } catch (JSONException e) {
                    }
                }
            });
            JSONObject result = new JSONObject();
            result.put("page", page);
            int pageCount = videos.length() == limit ? page + 1 : page;
            result.put("pagecount", pageCount);
            result.put("limit", limit);
            result.put("total", Integer.MAX_VALUE);
            result.put("list", videos);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    Pattern aliyun = Pattern.compile(".*?aliyundrive.com/s/(\\w+)[^\\w]?");
    Pattern aliyunShort = Pattern.compile(".*?alywp.net/(\\w+)[^\\w]?");

    @Override
    public String detailContent(List<String> ids) {
        try {
            String url = siteUrl + "/api/post-info";
            JSONObject json = new JSONObject();
            json.put("id", ids.get(0));
            JSONObject vodAtom = new JSONObject();
            OkHttpUtil.postJson(OkHttpUtil.defaultClient(), url, json.toString(), getHeaders(url), new OKCallBack.OKCallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                }

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject dataObject = new JSONObject(response);
                        JSONObject vObj = dataObject.getJSONObject("data");

                        vodAtom.put("vod_id", vObj.getString("id"));
                        vodAtom.put("vod_name", vObj.getString("title"));
                        vodAtom.put("vod_pic", vObj.getString("cover"));
                        vodAtom.put("type_name", "");
                        vodAtom.put("vod_year", vObj.getString("year"));
                        vodAtom.put("vod_area", vObj.getString("region"));
                        String mark = vObj.optString("subtitle");
                        if (mark.equals("null"))
                            mark = "";
                        vodAtom.put("vod_remarks", mark);
                        vodAtom.put("vod_actor", vObj.getString("actors"));
                        vodAtom.put("vod_director", vObj.getString("director"));
                        String desc = vObj.optString("desc");
                        if (desc.equals("null"))
                            desc = "";
                        vodAtom.put("vod_content", desc);

                        Map<String, String> vod_play = new LinkedHashMap<>();
                        JSONArray jsonArray = vObj.getJSONArray("links");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String link = obj.optString("link", "");
                            updatePlaylist(link, vod_play);
                        }
                        if (vod_play.size() == 0 && vObj.optString("content").length() > 0) {
                            updatePlaylist(vObj.optString("content"), vod_play);
                        }
                        if (vod_play.size() > 0) {
                            String vod_play_from = TextUtils.join("$$$", vod_play.keySet());
                            String vod_play_url = TextUtils.join("$$$", vod_play.values());
                            vodAtom.put("vod_play_from", vod_play_from);
                            vodAtom.put("vod_play_url", vod_play_url);
                        }
                    } catch (JSONException e) {
                    }
                }
            });
            JSONObject result = new JSONObject();
            JSONArray list = new JSONArray();
            list.put(vodAtom);
            result.put("list", list);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    private void updatePlaylist(String link, Map<String, String> vod_play) {
        String shareId = null;
        Matcher matcher = aliyun.matcher(link);
        if (matcher.find()) {
            shareId = matcher.group(1);
        } else {
            matcher = aliyunShort.matcher(link);
            if (matcher.find()) {
                shareId = matcher.group(1);
                Map<String, List<String>> respHeaders = new TreeMap<>();
                OkHttpUtil.stringNoRedirect("https://alywp.net/" + shareId, null, respHeaders);
                shareId = OkHttpUtil.getRedirectLocation(respHeaders);
                if (shareId != null) {
                    matcher = aliyun.matcher(shareId);
                    if (matcher.find()) {
                        shareId = matcher.group(1);
                    }
                }
            }
        }
        if (shareId != null) {
            String shareToken = getShareTk(shareId, "");
            ArrayList<String> vodItems = new ArrayList<>();
            getFileList(shareToken, shareId, "", "root", vodItems);
            vod_play.put("阿里云盘" + (vod_play.size() > 0 ? vod_play.size() : ""), TextUtils.join("#", vodItems));
        }
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) {
        try {
            String[] infos = id.split("\\+");
            String shareTk = getShareTk(infos[0], "");
            refreshTk();
            if (!accessTk.isEmpty()) {
                JSONObject json = new JSONObject();
                json.put("share_id", infos[0]);
                json.put("category", "live_transcoding");
                json.put("file_id", infos[1]);
                json.put("template_id", "");
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-share-token", shareTk);
                headers.put("authorization", accessTk);
                OKCallBack.OKCallBackString callback = new OKCallBack.OKCallBackString() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        String videoUrl = "";
                        try {
                            JSONArray playList = new JSONObject(response).getJSONObject("video_preview_play_info").getJSONArray("live_transcoding_task_list");
                            String[] orders = new String[]{"FHD", "HD", "SD"};
                            for (String or : orders) {
                                for (int i = 0; i < playList.length(); i++) {
                                    JSONObject obj = playList.getJSONObject(i);
                                    if (obj.optString("template_id").equals(or)) {
                                        videoUrl = obj.getString("url");
                                        break;
                                    }
                                }
                                if (!videoUrl.isEmpty())
                                    break;
                            }
                            if (videoUrl.isEmpty() && playList.length() > 0) {
                                videoUrl = playList.getJSONObject(0).getString("url");
                            }
                        } catch (JSONException e) {
                        } finally {
                            setResult(videoUrl);
                        }
                    }
                };
                OkHttpUtil.postJson(OkHttpUtil.defaultClient(), "https://api.aliyundrive.com/v2/file/get_share_link_video_preview_play_info", json.toString(), headers, callback);
                JSONArray playList = new JSONObject(callback.getResult()).getJSONObject("video_preview_play_info").getJSONArray("live_transcoding_task_list");
                String videoUrl = "";
                String[] orders = new String[]{"FHD", "HD", "SD"};
                for (String or : orders) {
                    for (int i = 0; i < playList.length(); i++) {
                        JSONObject obj = playList.getJSONObject(i);
                        if (obj.optString("template_id").equals(or)) {
                            videoUrl = obj.getString("url");
                            break;
                        }
                    }
                    if (!videoUrl.isEmpty())
                        break;
                }
                if (videoUrl.isEmpty() && playList.length() > 0) {
                    videoUrl = playList.getJSONObject(0).getString("url");
                }
                JSONObject headerObj = new JSONObject();
                headerObj.put("user-agent", " Dalvik/2.1.0 (Linux; U; Android 7.0; ZTE BA520 Build/MRA58K)");
                headerObj.put("referer", " https://www.aliyundrive.com/");
                JSONObject result = new JSONObject();
                result.put("parse", 0);
                result.put("playUrl", "");
                result.put("url", videoUrl);
                result.put("header", headerObj.toString());
                return result.toString();
            }
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public String searchContent(String key, boolean quick) {
        try {
            if (quick)
                return "";
            JSONArray videos = new JSONArray();
            try {
                String url = siteUrl + "/api/posts";
                JSONObject json = new JSONObject();
                json.put("category_id", "-1");
                json.put("skip", "0");
                json.put("limit", "30");
                json.put("keyword", key);
                OkHttpUtil.postJson(OkHttpUtil.defaultClient(), url, json.toString(), getHeaders(url), new OKCallBack.OKCallBackString() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject vObj = jsonArray.getJSONObject(i);
                                JSONObject v = new JSONObject();
                                v.put("vod_id", vObj.getString("id"));
                                v.put("vod_name", vObj.getString("title"));
                                v.put("vod_pic", vObj.getString("cover"));
                                v.put("vod_remarks", vObj.getString("subtitle"));
                                videos.put(v);
                            }
                        } catch (JSONException e) {
                        }
                    }
                });

            } catch (Exception e) {

            }
            JSONObject result = new JSONObject();
            result.put("list", videos);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    private String aliTk = "";

    private void fetchAliTk() {
        if (aliTk.isEmpty()) {
            try {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "okhttp/4.5.0");
                aliTk = new JSONObject(OkHttpUtil.string("http://81.68.244.5/tv/alitk", headers)).optString("alitk");
            } catch (JSONException e) {
                SpiderDebug.log(e);
            }
        }
    }

    private String accessTk = "";

    private void refreshTk() {
        fetchAliTk();
        if (!aliTk.isEmpty() && accessTk.isEmpty()) {
            try {
                JSONObject json = new JSONObject();
                json.put("refresh_token", aliTk);
                OkHttpUtil.postJson(OkHttpUtil.defaultClient(), "https://api.aliyundrive.com/token/refresh", json.toString(), new OKCallBack.OKCallBackString() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            accessTk = obj.getString("token_type") + " " + obj.getString("access_token");
                        } catch (Exception e) {
                            SpiderDebug.log(e);
                        }
                    }
                });
            } catch (JSONException e) {
                SpiderDebug.log(e);
            }
        }
    }

    private String getShareTk(String shareId, String sharePwd) {
        try {
            JSONObject json = new JSONObject();
            json.put("share_id", shareId);
            json.put("share_pwd", "");
            OKCallBack.OKCallBackString callback = new OKCallBack.OKCallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void onResponse(String response) {

                }
            };
            OkHttpUtil.postJson(OkHttpUtil.defaultClient(), "https://api.aliyundrive.com/v2/share_link/get_share_token", json.toString(), callback);
            return new JSONObject(callback.getResult()).optString("share_token");
        } catch (JSONException e) {
            SpiderDebug.log(e);
        }
        return "";
    }


    private void getFileList(String shareTk, String shareId, String sharePwd, String root, ArrayList<String> vodItems) {
        try {
            // 取文件夹
            JSONObject json = new JSONObject();
            json.put("share_id", shareId);
            json.put("parent_file_id", root);
            json.put("limit", 100);
            json.put("image_thumbnail_process", "image/resize,w_160/format,jpeg");
            json.put("image_url_process", "image/resize,w_1920/format,jpeg");
            json.put("video_thumbnail_process", "video/snapshot,t_1000,f_jpg,ar_auto,w_300");
            json.put("order_by", "name");
            json.put("order_direction", "ASC");
            HashMap<String, String> headers = new HashMap<>();
            headers.put("x-share-token", shareTk);
            OKCallBack.OKCallBackString callback = new OKCallBack.OKCallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void onResponse(String response) {

                }
            };
            OkHttpUtil.postJson(OkHttpUtil.defaultClient(), "https://api.aliyundrive.com/adrive/v3/file/list", json.toString(), headers, callback);
            JSONArray rootList = new JSONObject(callback.getResult()).optJSONArray("items");
            if (rootList != null && rootList.length() > 0) {
                for (int i = 0; i < rootList.length(); i++) {
                    JSONObject item = rootList.getJSONObject(i);
                    if (item.getString("type").equals("folder")) {
                        String dirId = item.getString("file_id");
                        getFileList(shareTk, shareId, sharePwd, dirId, vodItems);
                    } else {
                        if (item.getString("type").equals("file") && !item.getString("file_extension").equals("txt")) {
                            String fileId = item.getString("file_id");
                            String fileName = item.getString("name");
                            vodItems.add(fileName + "$" + shareId + "+" + fileId);
                        }
                    }
                }
            }
            // 取文件列表
        } catch (JSONException e) {
            SpiderDebug.log(e);
        }
    }
}
