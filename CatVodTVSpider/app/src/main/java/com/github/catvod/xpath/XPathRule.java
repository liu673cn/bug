package com.github.catvod.xpath;

import com.github.catvod.crawler.SpiderDebug;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XPathRule {
    /**
     * user-agent
     */
    private String ua;
    /**
     * 获取分类和首页推荐的Url
     */
    private String homeUrl;
    /**
     * 分类节点 xpath
     */
    private String cateNode;
    /**
     * 分类节点名 xpath
     */
    private String cateName;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern cateNameR;
    /**
     * 分类节点id xpath
     */
    private String cateId;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern cateIdR;
    /**
     * 手动指定 分类 如果有则不从homeUrl中获取分类
     */
    private LinkedHashMap<String, String> cateManual = new LinkedHashMap<>();

    /**
     * 筛选
     */
    private JSONObject filter;

    /**
     * 更新推荐视频节点 xpath
     */
    private String homeVodNode;
    /**
     * 更新推荐视频名称 xpath
     */
    private String homeVodName;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern homeVodNameR;
    /**
     * 更新推荐视频id xpath
     */
    private String homeVodId;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern homeVodIdR;
    /**
     * 更新推荐视频图片 xpath
     */
    private String homeVodImg;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern homeVodImgR;
    /**
     * 更新推荐视频简介 xpath
     */
    private String homeVodMark;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern homeVodMarkR;
    /**
     * 分类页地址
     */
    private String cateUrl;
    /**
     * 分类叶视频节点 xpath
     */
    private String cateVodNode;
    /**
     * 分类叶视频名称 xpath
     */
    private String cateVodName;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern cateVodNameR;
    /**
     * 分类叶视频视频id xpath
     */
    private String cateVodId;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern cateVodIdR;
    /**
     * 分类叶视频视频图片 xpath
     */
    private String cateVodImg;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern cateVodImgR;
    /**
     * 分类叶视频视频简介 xpath
     */
    private String cateVodMark;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern cateVodMarkR;

    /**
     * 详情页面
     */
    private String dtUrl;
    /**
     * 详情节点 xpath
     */
    private String dtNode;
    /**
     * 详情 视频名 xpath
     */
    private String dtName;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern dtNameR;
    /**
     * 详情视频图片 xpath
     */
    private String dtImg;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern dtImgR;
    /**
     * 详情视频分类 xpath
     */
    private String dtCate;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern dtCateR;
    /**
     * 详情视频年份 xpath
     */
    private String dtYear;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern dtYearR;
    /**
     * 详情视频地区 xpath
     */
    private String dtArea;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern dtAreaR;
    /**
     * 详情视频简介 xpath
     */
    private String dtMark;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern dtMarkR;
    /**
     * 详情演员 xpath
     */
    private String dtActor;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern dtActorR;
    /**
     * 详情导演 xpath
     */
    private String dtDirector;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern dtDirectorR;
    /**
     * 详情 说明 长  xpath
     */
    private String dtDesc;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern dtDescR;

    /**
     * 详情播放来源节点
     */
    private String dtFromNode;
    /**
     * 详情播放来源名称 xpath
     */
    private String dtFromName;
    /**
     * 详情
     */
    private Pattern dtFromNameR;
    /**
     * 详情播放地址列表节点  xpath
     */
    private String dtUrlNode;
    /**
     * 详情播放地址节点  xpath
     */
    private String dtUrlSubNode;
    /**
     * 详情播放地址id  xpath
     */
    private String dtUrlId;
    /**
     * 详情
     */
    private Pattern dtUrlIdR;
    /**
     * 详情播放地址名称  xpath
     */
    private String dtUrlName;
    /**
     * 详情
     */
    private Pattern dtUrlNameR;
    /**
     * 播放页面url
     */
    private String playUrl;
    /**
     * 播放解析调用ua
     */
    private String playUa;

    /**
     * 搜索页地址
     */
    private String searchUrl;

    /**
     * 搜索页视频节点 xpath
     */
    private String scVodNode;
    /**
     * 搜索页视频名称 xpath
     */
    private String scVodName;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern scVodNameR;
    /**
     * 搜索页视频id xpath
     */
    private String scVodId;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern scVodIdR;
    /**
     * 搜索页视频图片 xpath
     */
    private String scVodImg;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern scVodImgR;
    /**
     * 搜索页视频简介 xpath
     */
    private String scVodMark;
    /**
     * 正则对取到的数据进行二次处理
     */
    private Pattern scVodMarkR;

    private static Pattern getPattern(JSONObject json, String key) {
        String v = json.optString(key).trim();
        if (v.isEmpty())
            return null;
        else {
            try {
                return Pattern.compile(v);
            } catch (Exception e) {
                SpiderDebug.log(e);
            }
        }
        return null;
    }

    private static String doReplaceRegex(Pattern pattern, String src) {
        if (pattern == null)
            return src;
        try {
            Matcher matcher = pattern.matcher(src);
            if (matcher.find()) {
                return matcher.group(1).trim();
            }
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return src;
    }

    public static XPathRule fromJson(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            XPathRule rule = new XPathRule();
            rule.ua = jsonObj.optString("ua");
            rule.homeUrl = jsonObj.optString("homeUrl").trim();
            rule.cateNode = jsonObj.optString("cateNode").trim();
            rule.cateName = jsonObj.optString("cateName").trim();
            rule.cateNameR = getPattern(jsonObj, "cateNameR");
            rule.cateId = jsonObj.optString("cateId").trim();
            rule.cateIdR = getPattern(jsonObj, "cateIdR");
            JSONObject navs = jsonObj.optJSONObject("cateManual");
            if (navs != null) {
                Iterator<String> keys = navs.keys();
                while (keys.hasNext()) {
                    String name = keys.next();
                    rule.cateManual.put(name.trim(), navs.getString(name).trim());
                }
            }
            rule.filter = jsonObj.optJSONObject("filter");
            rule.homeVodNode = jsonObj.optString("homeVodNode").trim();
            rule.homeVodName = jsonObj.optString("homeVodName").trim();
            rule.homeVodNameR = getPattern(jsonObj, "homeVodNameR");
            rule.homeVodId = jsonObj.optString("homeVodId").trim();
            rule.homeVodIdR = getPattern(jsonObj, "homeVodIdR");
            rule.homeVodImg = jsonObj.optString("homeVodImg").trim();
            rule.homeVodImgR = getPattern(jsonObj, "homeVodImgR");
            rule.homeVodMark = jsonObj.optString("homeVodMark").trim();
            rule.homeVodMarkR = getPattern(jsonObj, "homeVodMarkR");
            rule.cateUrl = jsonObj.optString("cateUrl").trim();
            rule.cateVodNode = jsonObj.optString("cateVodNode").trim();
            rule.cateVodName = jsonObj.optString("cateVodName").trim();
            rule.cateVodNameR = getPattern(jsonObj, "cateVodNameR");
            rule.cateVodId = jsonObj.optString("cateVodId").trim();
            rule.cateVodIdR = getPattern(jsonObj, "cateVodIdR");
            rule.cateVodImg = jsonObj.optString("cateVodImg").trim();
            rule.cateVodImgR = getPattern(jsonObj, "cateVodImgR");
            rule.cateVodMark = jsonObj.optString("cateVodMark").trim();
            rule.cateVodMarkR = getPattern(jsonObj, "cateVodMarkR");
            rule.dtUrl = jsonObj.optString("dtUrl");
            rule.dtNode = jsonObj.optString("dtNode");
            rule.dtName = jsonObj.optString("dtName");
            rule.dtNameR = getPattern(jsonObj, "dtNameR");
            rule.dtImg = jsonObj.optString("dtImg");
            rule.dtImgR = getPattern(jsonObj, "dtImgR");
            rule.dtCate = jsonObj.optString("dtCate");
            rule.dtCateR = getPattern(jsonObj, "dtCateR");
            rule.dtYear = jsonObj.optString("dtYear");
            rule.dtYearR = getPattern(jsonObj, "dtYearR");
            rule.dtArea = jsonObj.optString("dtArea");
            rule.dtAreaR = getPattern(jsonObj, "dtAreaR");
            rule.dtMark = jsonObj.optString("dtMark");
            rule.dtMarkR = getPattern(jsonObj, "dtMarkR");
            rule.dtActor = jsonObj.optString("dtActor");
            rule.dtActorR = getPattern(jsonObj, "dtActorR");
            rule.dtDirector = jsonObj.optString("dtDirector");
            rule.dtDirectorR = getPattern(jsonObj, "dtDirectorR");
            rule.dtDesc = jsonObj.optString("dtDesc");
            rule.dtDescR = getPattern(jsonObj, "dtDescR");
            rule.dtFromNode = jsonObj.optString("dtFromNode");
            rule.dtFromName = jsonObj.optString("dtFromName");
            rule.dtFromNameR = getPattern(jsonObj, "dtFromNameR");
            rule.dtUrlNode = jsonObj.optString("dtUrlNode");
            rule.dtUrlSubNode = jsonObj.optString("dtUrlSubNode");
            rule.dtUrlId = jsonObj.optString("dtUrlId");
            rule.dtUrlIdR = getPattern(jsonObj, "dtUrlIdR");
            rule.dtUrlName = jsonObj.optString("dtUrlName");
            rule.dtUrlNameR = getPattern(jsonObj, "dtUrlNameR");
            rule.playUrl = jsonObj.optString("playUrl");
            rule.playUa = jsonObj.optString("playUa");
            rule.searchUrl = jsonObj.optString("searchUrl");
            rule.scVodNode = jsonObj.optString("scVodNode").trim();
            rule.scVodName = jsonObj.optString("scVodName").trim();
            rule.scVodNameR = getPattern(jsonObj, "scVodNameR");
            rule.scVodId = jsonObj.optString("scVodId").trim();
            rule.scVodIdR = getPattern(jsonObj, "scVodIdR");
            rule.scVodImg = jsonObj.optString("scVodImg").trim();
            rule.scVodImgR = getPattern(jsonObj, "scVodImgR");
            rule.scVodMark = jsonObj.optString("scVodMark").trim();
            rule.scVodMarkR = getPattern(jsonObj, "scVodMarkR");
            return rule;
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return null;
    }

    public String getUa() {
        return ua;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public String getCateNode() {
        return cateNode;
    }

    public String getCateName() {
        return cateName;
    }

    public String getCateNameR(String src) {
        return doReplaceRegex(cateNameR, src);
    }

    public String getCateId() {
        return cateId;
    }

    public String getCateIdR(String src) {
        return doReplaceRegex(cateIdR, src);
    }

    public LinkedHashMap<String, String> getCateManual() {
        return cateManual;
    }

    public JSONObject getFilter() {
        return filter;
    }

    public String getHomeVodNode() {
        return homeVodNode;
    }

    public String getHomeVodName() {
        return homeVodName;
    }

    public String getHomeVodNameR(String src) {
        return doReplaceRegex(homeVodNameR, src);
    }

    public String getHomeVodId() {
        return homeVodId;
    }

    public String getHomeVodIdR(String src) {
        return doReplaceRegex(homeVodIdR, src);
    }

    public String getHomeVodImg() {
        return homeVodImg;
    }

    public String getHomeVodImgR(String src) {
        return doReplaceRegex(homeVodImgR, src);
    }

    public String getHomeVodMark() {
        return homeVodMark;
    }

    public String getHomeVodMarkR(String src) {
        return doReplaceRegex(homeVodMarkR, src);
    }

    public String getCateUrl() {
        return cateUrl;
    }

    public String getCateVodNode() {
        return cateVodNode;
    }

    public String getCateVodName() {
        return cateVodName;
    }

    public String getCateVodNameR(String src) {
        return doReplaceRegex(cateVodNameR, src);
    }

    public String getCateVodId() {
        return cateVodId;
    }

    public String getCateVodIdR(String src) {
        return doReplaceRegex(cateVodIdR, src);
    }

    public String getCateVodImg() {
        return cateVodImg;
    }

    public String getCateVodImgR(String src) {
        return doReplaceRegex(cateVodImgR, src);
    }

    public String getCateVodMark() {
        return cateVodMark;
    }

    public String getCateVodMarkR(String src) {
        return doReplaceRegex(cateVodNameR, src);
    }

    public String getDetailUrl() {
        return dtUrl;
    }

    public String getDetailNode() {
        return dtNode;
    }

    public String getDetailName() {
        return dtName;
    }

    public String getDetailNameR(String src) {
        return doReplaceRegex(dtNameR, src);
    }

    public String getDetailImg() {
        return dtImg;
    }

    public String getDetailImgR(String src) {
        return doReplaceRegex(dtImgR, src);
    }

    public String getDetailCate() {
        return dtCate;
    }

    public String getDetailCateR(String src) {
        return doReplaceRegex(dtCateR, src);
    }

    public String getDetailYear() {
        return dtYear;
    }

    public String getDetailYearR(String src) {
        return doReplaceRegex(dtYearR, src);
    }

    public String getDetailArea() {
        return dtArea;
    }

    public String getDetailAreaR(String src) {
        return doReplaceRegex(dtAreaR, src);
    }

    public String getDetailMark() {
        return dtMark;
    }

    public String getDetailMarkR(String src) {
        return doReplaceRegex(dtMarkR, src);
    }

    public String getDetailActor() {
        return dtActor;
    }

    public String getDetailActorR(String src) {
        return doReplaceRegex(dtActorR, src);
    }

    public String getDetailDirector() {
        return dtDirector;
    }

    public String getDetailDirectorR(String src) {
        return doReplaceRegex(dtDirectorR, src);
    }

    public String getDetailDesc() {
        return dtDesc;
    }

    public String getDetailDescR(String src) {
        return doReplaceRegex(dtDescR, src);
    }

    public String getDetailFromNode() {
        return dtFromNode;
    }

    public String getDetailFromName() {
        return dtFromName;
    }

    public String getDetailFromNameR(String src) {
        return doReplaceRegex(dtFromNameR, src);
    }

    public String getDetailUrlNode() {
        return dtUrlNode;
    }

    public String getDetailUrlSubNode() {
        return dtUrlSubNode;
    }

    public String getDetailUrlId() {
        return dtUrlId;
    }

    public String getDetailUrlIdR(String src) {
        return doReplaceRegex(dtUrlIdR, src);
    }

    public String getDetailUrlName() {
        return dtUrlName;
    }

    public String getDetailUrlNameR(String src) {
        return doReplaceRegex(dtUrlNameR, src);
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public String getPlayUa() {
        return playUa;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public String getSearchVodNode() {
        return scVodNode;
    }

    public String getSearchVodName() {
        return scVodName;
    }

    public String getSearchVodNameR(String src) {
        return doReplaceRegex(scVodNameR, src);
    }

    public String getSearchVodId() {
        return scVodId;
    }

    public String getSearchVodIdR(String src) {
        return doReplaceRegex(scVodIdR, src);
    }

    public String getSearchVodImg() {
        return scVodImg;
    }

    public String getSearchVodImgR(String src) {
        return doReplaceRegex(scVodImgR, src);
    }

    public String getSearchVodMark() {
        return scVodMark;
    }

    public String getSearchVodMarkR(String src) {
        return doReplaceRegex(scVodMarkR, src);
    }
}
