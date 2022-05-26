package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;

import com.github.catvod.crawler.Spider;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.Misc;
import com.github.catvod.utils.okhttp.OkHttpUtil;
import com.github.catvod.xpath.XPathRule;

import org.json.JSONArray;
import org.json.JSONObject;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class XPath extends Spider {

    @Override
    public void init(Context context) {
        super.init(context);
    }

    public void init(Context context, String extend) {
        super.init(context, extend);
        this.ext = extend;
    }

    @Override
    public String homeContent(boolean filter) {
        try {
            fetchRule();
            JSONObject result = new JSONObject();
            JSONArray classes = new JSONArray();
            if (rule.getCateManual().size() > 0) {
                Set<String> keys = rule.getCateManual().keySet();
                for (String k : keys) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("type_name", k);
                    jsonObject.put("type_id", rule.getCateManual().get(k));
                    classes.put(jsonObject);
                }
            }
            try {
                String webUrl = rule.getHomeUrl();
                JXDocument doc = JXDocument.create(fetch(webUrl));
                if (rule.getCateManual().size() == 0) {
                    List<JXNode> navNodes = doc.selN(rule.getCateNode());
                    for (int i = 0; i < navNodes.size(); i++) {
                        String name = navNodes.get(i).selOne(rule.getCateName()).asString().trim();
                        name = rule.getCateNameR(name);
                        String id = navNodes.get(i).selOne(rule.getCateId()).asString().trim();
                        id = rule.getCateIdR(id);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type_id", id);
                        jsonObject.put("type_name", name);
                        classes.put(jsonObject);
                    }
                }
                if (!rule.getHomeVodNode().isEmpty()) {
                    try {
                        JSONArray videos = new JSONArray();
                        List<JXNode> vodNodes = doc.selN(rule.getHomeVodNode());
                        for (int i = 0; i < vodNodes.size(); i++) {
                            String name = vodNodes.get(i).selOne(rule.getHomeVodName()).asString().trim();
                            name = rule.getHomeVodNameR(name);
                            String id = vodNodes.get(i).selOne(rule.getHomeVodId()).asString().trim();
                            id = rule.getHomeVodIdR(id);
                            String pic = vodNodes.get(i).selOne(rule.getHomeVodImg()).asString().trim();
                            pic = rule.getHomeVodImgR(pic);
                            pic = Misc.fixUrl(webUrl, pic);
                            String mark = "";
                            if (!rule.getHomeVodMark().isEmpty()) {
                                try {
                                    mark = vodNodes.get(i).selOne(rule.getHomeVodMark()).asString().trim();
                                    mark = rule.getHomeVodMarkR(mark);
                                } catch (Exception e) {
                                    SpiderDebug.log(e);
                                }
                            }
                            JSONObject v = new JSONObject();
                            v.put("vod_id", id);
                            v.put("vod_name", name);
                            v.put("vod_pic", pic);
                            v.put("vod_remarks", mark);
                            videos.put(v);
                        }
                        result.put("list", videos);
                    } catch (Exception e) {
                        SpiderDebug.log(e);
                    }
                }
            } catch (Exception e) {
                SpiderDebug.log(e);
            }
            result.put("class", classes);
            if (filter && rule.getFilter() != null) {
                result.put("filters", rule.getFilter());
            }
            return result.toString();
        } catch (
                Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    protected HashMap<String, String> getHeaders(String url) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("User-Agent", rule.getUa().isEmpty()
                ? "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.54 Safari/537.36"
                : rule.getUa());
        return headers;
    }

    @Override
    public String homeVideoContent() {
        try {
            fetchRule();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    protected String categoryUrl(String tid, String pg, boolean filter, HashMap<String, String> extend) {
        return rule.getCateUrl().replace("{cateId}", tid).replace("{catePg}", pg);
    }

    @Override
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) {
        try {
            fetchRule();
            String webUrl = categoryUrl(tid, pg, filter, extend);
            JSONArray videos = new JSONArray();
            JXDocument doc = JXDocument.create(fetch(webUrl));
            List<JXNode> vodNodes = doc.selN(rule.getCateVodNode());
            for (int i = 0; i < vodNodes.size(); i++) {
                String name = vodNodes.get(i).selOne(rule.getCateVodName()).asString().trim();
                name = rule.getCateVodNameR(name);
                String id = vodNodes.get(i).selOne(rule.getCateVodId()).asString().trim();
                id = rule.getCateVodIdR(id);
                String pic = vodNodes.get(i).selOne(rule.getCateVodImg()).asString().trim();
                pic = rule.getCateVodImgR(pic);
                pic = Misc.fixUrl(webUrl, pic);
                String mark = "";
                if (!rule.getCateVodMark().isEmpty()) {
                    try {
                        mark = vodNodes.get(i).selOne(rule.getCateVodMark()).asString().trim();
                        mark = rule.getCateVodMarkR(mark);
                    } catch (Exception e) {
                        SpiderDebug.log(e);
                    }
                }
                JSONObject v = new JSONObject();
                v.put("vod_id", id);
                v.put("vod_name", name);
                v.put("vod_pic", pic);
                v.put("vod_remarks", mark);
                videos.put(v);
            }
            JSONObject result = new JSONObject();
            result.put("page", pg);
            result.put("pagecount", Integer.MAX_VALUE);
            result.put("limit", 90);
            result.put("total", Integer.MAX_VALUE);
            result.put("list", videos);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    protected void detailContentExt(String content, JSONObject vod) {

    }

    @Override
    public String detailContent(List<String> ids) {
        try {
            fetchRule();
            String webUrl = rule.getDetailUrl().replace("{vid}", ids.get(0));
            String webContent = fetch(webUrl);
            JXDocument doc = JXDocument.create(webContent);
            JXNode vodNode = doc.selNOne(rule.getDetailNode());

            String cover = "", title = "", desc = "", category = "", area = "", year = "", remark = "", director = "", actor = "";

            title = vodNode.selOne(rule.getDetailName()).asString().trim();
            title = rule.getDetailNameR(title);

            cover = vodNode.selOne(rule.getDetailImg()).asString().trim();
            cover = rule.getDetailImgR(cover);
            cover = Misc.fixUrl(webUrl, cover);

            if (!rule.getDetailCate().isEmpty()) {
                try {
                    category = vodNode.selOne(rule.getDetailCate()).asString().trim();
                    category = rule.getDetailCateR(category);
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
            if (!rule.getDetailYear().isEmpty()) {
                try {
                    year = vodNode.selOne(rule.getDetailYear()).asString().trim();
                    year = rule.getDetailYearR(year);
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
            if (!rule.getDetailArea().isEmpty()) {
                try {
                    area = vodNode.selOne(rule.getDetailArea()).asString().trim();
                    area = rule.getDetailAreaR(area);
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
            if (!rule.getDetailMark().isEmpty()) {
                try {
                    remark = vodNode.selOne(rule.getDetailMark()).asString().trim();
                    remark = rule.getDetailMarkR(remark);
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
            if (!rule.getDetailActor().isEmpty()) {
                try {
                    actor = vodNode.selOne(rule.getDetailActor()).asString().trim();
                    actor = rule.getDetailActorR(actor);
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
            if (!rule.getDetailDirector().isEmpty()) {
                try {
                    director = vodNode.selOne(rule.getDetailDirector()).asString().trim();
                    director = rule.getDetailDirectorR(director);
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }
            if (!rule.getDetailDesc().isEmpty()) {
                try {
                    desc = vodNode.selOne(rule.getDetailDesc()).asString().trim();
                    desc = rule.getDetailDescR(desc);
                } catch (Exception e) {
                    SpiderDebug.log(e);
                }
            }

            JSONObject vod = new JSONObject();
            vod.put("vod_id", ids.get(0));
            vod.put("vod_name", title);
            vod.put("vod_pic", cover);
            vod.put("type_name", category);
            vod.put("vod_year", year);
            vod.put("vod_area", area);
            vod.put("vod_remarks", remark);
            vod.put("vod_actor", actor);
            vod.put("vod_director", director);
            vod.put("vod_content", desc);

            ArrayList<String> playFrom = new ArrayList<>();

            List<JXNode> fromNodes = doc.selN(rule.getDetailFromNode());
            for (int i = 0; i < fromNodes.size(); i++) {
                String name = fromNodes.get(i).selOne(rule.getDetailFromName()).asString().trim();
                name = rule.getDetailFromNameR(name);
                playFrom.add(name);
            }

            ArrayList<String> playList = new ArrayList<>();
            List<JXNode> urlListNodes = doc.selN(rule.getDetailUrlNode());
            for (int i = 0; i < urlListNodes.size(); i++) {
                List<JXNode> urlNodes = urlListNodes.get(i).sel(rule.getDetailUrlSubNode());
                List<String> vodItems = new ArrayList<>();
                for (int j = 0; j < urlNodes.size(); j++) {
                    String name = urlNodes.get(j).selOne(rule.getDetailUrlName()).asString().trim();
                    name = rule.getDetailUrlNameR(name);
                    String id = urlNodes.get(j).selOne(rule.getDetailUrlId()).asString().trim();
                    id = rule.getDetailUrlIdR(id);
                    vodItems.add(name + "$" + id);
                }
                // 排除播放列表为空的播放源
                if (vodItems.size() == 0 && playFrom.size() > i) {
                    playFrom.set(i, "");
                }
                playList.add(TextUtils.join("#", vodItems));
            }
            // 排除播放列表为空的播放源
            for (int i = playFrom.size() - 1; i >= 0; i--) {
                if (playFrom.get(i).isEmpty())
                    playFrom.remove(i);
            }
            for (int i = playList.size() - 1; i >= 0; i--) {
                if (playList.get(i).isEmpty())
                    playList.remove(i);
            }
            for (int i = playList.size() - 1; i >= 0; i--) {
                if (i >= playFrom.size())
                    playList.remove(i);
            }
            String vod_play_from = TextUtils.join("$$$", playFrom);
            String vod_play_url = TextUtils.join("$$$", playList);
            vod.put("vod_play_from", vod_play_from);
            vod.put("vod_play_url", vod_play_url);

            detailContentExt(webContent, vod);

            JSONObject result = new JSONObject();
            JSONArray list = new JSONArray();
            list.put(vod);
            result.put("list", list);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) {
        try {
            fetchRule();
            String webUrl = rule.getPlayUrl().isEmpty() ? id : rule.getPlayUrl().replace("{playUrl}", id);
            SpiderDebug.log(webUrl);
            JSONObject result = new JSONObject();
            result.put("parse", 1);
            result.put("playUrl", "");
            if (!rule.getPlayUa().isEmpty()) {
                result.put("ua", rule.getPlayUa());
            }
            result.put("url", webUrl);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public String searchContent(String key, boolean quick) {
        try {
            fetchRule();
            if (rule.getSearchUrl().isEmpty()) {
                return "";
            }
            String webUrl = rule.getSearchUrl().replace("{wd}", URLEncoder.encode(key));
            String webContent = fetch(webUrl);
            JSONObject result = new JSONObject();
            JSONArray videos = new JSONArray();
            // add maccms suggest search api support
            if (rule.getSearchVodNode().startsWith("json:")) {
                String[] node = rule.getSearchVodNode().substring(5).split(">");
                JSONObject data = new JSONObject(webContent);
                for (int i = 0; i < node.length; i++) {
                    if (i == node.length - 1) {
                        JSONArray vodArray = data.getJSONArray(node[i]);
                        for (int j = 0; j < vodArray.length(); j++) {
                            JSONObject vod = vodArray.getJSONObject(j);
                            String name = vod.optString(rule.getSearchVodName()).trim();
                            name = rule.getSearchVodNameR(name);
                            String id = vod.optString(rule.getSearchVodId()).trim();
                            id = rule.getSearchVodIdR(id);
                            String pic = vod.optString(rule.getSearchVodImg()).trim();
                            pic = rule.getSearchVodImgR(pic);
                            pic = Misc.fixUrl(webUrl, pic);
                            String mark = vod.optString(rule.getSearchVodMark()).trim();
                            mark = rule.getSearchVodMarkR(mark);
                            JSONObject v = new JSONObject();
                            v.put("vod_id", id);
                            v.put("vod_name", name);
                            v.put("vod_pic", pic);
                            v.put("vod_remarks", mark);
                            videos.put(v);
                        }
                    } else {
                        data = data.getJSONObject(node[i]);
                    }
                }
            } else {
                JXDocument doc = JXDocument.create(webContent);
                List<JXNode> vodNodes = doc.selN(rule.getSearchVodNode());
                for (int i = 0; i < vodNodes.size(); i++) {
                    String name = vodNodes.get(i).selOne(rule.getSearchVodName()).asString().trim();
                    name = rule.getSearchVodNameR(name);
                    String id = vodNodes.get(i).selOne(rule.getSearchVodId()).asString().trim();
                    id = rule.getSearchVodIdR(id);
                    String pic = vodNodes.get(i).selOne(rule.getSearchVodImg()).asString().trim();
                    pic = rule.getSearchVodImgR(pic);
                    pic = Misc.fixUrl(webUrl, pic);
                    String mark = "";
                    if (!rule.getCateVodMark().isEmpty()) {
                        try {
                            mark = vodNodes.get(i).selOne(rule.getSearchVodMark()).asString().trim();
                            mark = rule.getSearchVodMarkR(mark);
                        } catch (Exception e) {
                            SpiderDebug.log(e);
                        }
                    }
                    JSONObject v = new JSONObject();
                    v.put("vod_id", id);
                    v.put("vod_name", name);
                    v.put("vod_pic", pic);
                    v.put("vod_remarks", mark);
                    videos.put(v);
                }
            }
            result.put("list", videos);
            return result.toString();
        } catch (
                Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    @Override
    public boolean manualVideoCheck() {
        return false;
    }

    private String[] videoFormatList = new String[]{".m3u8", ".mp4", ".mpeg", ".flv"};

    @Override
    public boolean isVideoFormat(String url) {
        url = url.toLowerCase();
        if (url.contains("=http") || url.contains("=https") || url.contains("=https%3a%2f") || url.contains("=http%3a%2f")) {
            return false;
        }
        for (String format : videoFormatList) {
            if (url.contains(format)) {
                return true;
            }
        }
        return false;
    }

    protected String ext = null;
    protected XPathRule rule = null;

    protected void fetchRule() {
        if (rule == null) {
            if (ext != null) {
                if (ext.startsWith("http")) {
                    String json = OkHttpUtil.string(ext, null);
                    rule = XPathRule.fromJson(json);
                    loadRuleExt(json);
                } else {
                    rule = XPathRule.fromJson(ext);
                    loadRuleExt(ext);
                }
            }
        }
    }

    protected void loadRuleExt(String json) {

    }

    protected String fetch(String webUrl) {
        SpiderDebug.log(webUrl);
        return OkHttpUtil.string(webUrl, getHeaders(webUrl));
    }
}
