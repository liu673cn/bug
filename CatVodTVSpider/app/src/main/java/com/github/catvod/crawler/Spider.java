package com.github.catvod.crawler;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public abstract class Spider {

    public static JSONObject empty = new JSONObject();

    public void init(Context context) {
    }

    public void init(Context context, String extend) {
        init(context);
    }

    /**
     * 首页数据内容
     *
     * @param filter 是否开启筛选
     * @return
     */
    public String homeContent(boolean filter) {
        return "";
    }

    /**
     * 首页最近更新数据 如果上面的homeContent中不包含首页最近更新视频的数据 可以使用这个接口返回
     *
     * @return
     */
    public String homeVideoContent() {
        return "";
    }

    /**
     * 分类数据
     *
     * @param tid
     * @param pg
     * @param filter
     * @param extend
     * @return
     */
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) {
        return "";
    }

    /**
     * 详情数据
     *
     * @param ids
     * @return
     */
    public String detailContent(List<String> ids) {
        return "";
    }

    /**
     * 搜索数据内容
     *
     * @param key
     * @param quick
     * @return
     */
    public String searchContent(String key, boolean quick) {
        return "";
    }

    /**
     * 播放信息
     *
     * @param flag
     * @param id
     * @return
     */
    public String playerContent(String flag, String id, List<String> vipFlags) {
        return "";
    }

    /**
     * webview解析时使用 可自定义判断当前加载的 url 是否是视频
     *
     * @param url
     * @return
     */
    public boolean isVideoFormat(String url) {
        return false;
    }

    /**
     * 是否手动检测webview中加载的url
     *
     * @return
     */
    public boolean manualVideoCheck() {
        return false;
    }
}
