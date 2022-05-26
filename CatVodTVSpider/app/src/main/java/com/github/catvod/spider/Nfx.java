package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;

import com.github.catvod.crawler.Spider;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.okhttp.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Demo for self study
 * <p>
 * Source from Author: CatVod
 */

public class Nfx extends Spider {

    private static final String siteUrl = "https://nfxhd.com/";
    private static final String siteHost = "nfxhd.com";

    protected JSONObject playerConfig;

    protected Pattern regexCategory = Pattern.compile("/vodtype/(\\w+)/");
    protected Pattern regexVid = Pattern.compile("/voddetail/(\\w+)/");
    protected Pattern regexPlay = Pattern.compile("/vodplay/(\\w+)-(\\d+)-(\\d+)/");
    protected Pattern regexPage = Pattern.compile("/vodshow/(\\S+)/");

    @Override
    public void init(Context context) {
        super.init(context);
        try {
            playerConfig = new JSONObject("{\"adaplay\":{\"sh\":\"菜逼播放器\",\"sn\":\"1\",\"pu\":\"https://nfxhd.com/dp.php/?url=\",\"or\":999},\"xigua\":{\"sh\":\"手动点播放才能播放的播放器\",\"sn\":\"1\",\"pu\":\"https://nfxhd.com/dp.php/?url=\",\"or\":999},\"danmuku\":{\"sh\":\"海王播放器\",\"sn\":\"1\",\"pu\":\"\",\"or\":999},\"dplayer\":{\"sh\":\"冥王星播放器\",\"sn\":\"0\",\"pu\":\"\",\"or\":999},\"bdxm3u8\":{\"sh\":\"北斗星m3u8\",\"sn\":\"1\",\"pu\":\"https://player.bdxzym3u8.com/dplayer/?url=\",\"or\":999},\"bdxyun\":{\"sh\":\"北斗星云播放\",\"sn\":\"1\",\"pu\":\"\",\"or\":999},\"gangtiexia\":{\"sh\":\"土星播放器\",\"sn\":\"1\",\"pu\":\"https://wy.bigmao.top/api/ShowVideoWy/3bb24322f78b47dfb8723c13d46d45ee/\",\"or\":999},\"tt10\":{\"sh\":\"木星播放器\",\"sn\":\"1\",\"pu\":\"https://wy.bigmao.top/api/ShowVideoMu/3bb24322f78b47dfb8723c13d46d45ee/\",\"or\":999},\"docker\":{\"sh\":\"彗星播放器\",\"sn\":\"1\",\"pu\":\"https://wy.bigmao.top/api/ShowVideoDoc/3bb24322f78b47dfb8723c13d46d45ee/\",\"or\":999},\"qq\":{\"sh\":\"腾讯视频\",\"sn\":\"1\",\"pu\":\"\",\"or\":999},\"youku\":{\"sh\":\"优酷\",\"sn\":\"1\",\"pu\":\"\",\"or\":999},\"qiyi\":{\"sh\":\"爱奇艺\",\"sn\":\"1\",\"pu\":\"\",\"or\":999},\"mgtv\":{\"sh\":\"仅限APP播放\",\"sn\":\"1\",\"pu\":\"\",\"or\":999},\"m3u8\":{\"sh\":\"m3u8\",\"sn\":\"1\",\"pu\":\"\",\"or\":999},\"mx771\":{\"sh\":\"站长采集的播放器\",\"sn\":\"0\",\"pu\":\"\",\"or\":999},\"ppayun2\":{\"sh\":\"白鸟座播放器\",\"sn\":\"1\",\"pu\":\"https://wy.bigmao.top/api/ShowVideoMu/3bb24322f78b47dfb8723c13d46d45ee/\",\"or\":999},\"sohu\":{\"sh\":\"搜狐\",\"sn\":\"1\",\"pu\":\"\",\"or\":999},\"iframe\":{\"sh\":\"iframe外链数据\",\"sn\":\"0\",\"pu\":\"\",\"or\":999}}");
        } catch (JSONException e) {
            SpiderDebug.log(e);
        }
    }

    /**
     * 爬虫headers
     *
     * @param url
     * @return
     */
    protected HashMap<String, String> getHeaders(String url) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("method", "GET");
        headers.put("Host", siteHost);
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("DNT", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        return headers;
    }

    /**
     * 获取分类数据 + 首页最近更新视频列表数据
     *
     * @param filter 是否开启筛选 关联的是 软件设置中 首页数据源里的筛选开关
     * @return
     */
    @Override
    public String homeContent(boolean filter) {
        try {
            String url = siteUrl;
            Document doc = Jsoup.parse(OkHttpUtil.string(url, getHeaders(url)));
            Elements elements = doc.select("ul.nav-menu>li>a");
            JSONArray classes = new JSONArray();
            ArrayList<String> allClass = new ArrayList<>();
            for (Element ele : elements) {
                String name = ele.text();
                boolean show = !filter || (name.equals("电影") || name.equals("美剧") || name.equals("日韩") || name.equals("动漫"));
                if (allClass.contains(name))
                    show = false;
                if (show) {
                    allClass.add(name);
                    Matcher mather = regexCategory.matcher(ele.attr("href"));
                    if (!mather.find())
                        continue;
                    // 把分类的id和名称取出来加到列表里
                    String id = mather.group(1).trim();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("type_id", id);
                    jsonObject.put("type_name", name);
                    classes.put(jsonObject);
                }
            }
            JSONObject result = new JSONObject();
            result.put("class", classes);
            try {
                // 取首页推荐视频列表
                Elements list = doc.select("ul.myui-vodlist>li>div");
                JSONArray videos = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    Element vod = list.get(i);
                    String title = vod.selectFirst(".title").text();
                    String cover = vod.selectFirst(".myui-vodlist__thumb").attr("data-original");
                    String remark = vod.selectFirst("span.pic-text").text();

                    Matcher matcher = regexVid.matcher(vod.selectFirst(".myui-vodlist__thumb").attr("href"));
                    if (!matcher.find())
                        continue;
                    String id = matcher.group(1);
                    JSONObject v = new JSONObject();
                    v.put("vod_id", id);
                    v.put("vod_name", title);
                    v.put("vod_pic", cover);
                    v.put("vod_remarks", remark);
                    videos.put(v);
                }
                result.put("list", videos);
            } catch (Exception e) {
                SpiderDebug.log(e);
            }
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }


    /**
     * 获取分类信息数据
     *
     * @param tid    分类id
     * @param pg     页数
     * @param filter 同homeContent方法中的filter
     * @param extend 筛选参数{k:v, k1:v1}
     * @return
     */
    @Override
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) {
        try {
            // 获取分类数据的url
            String url = siteUrl + "/vodtype/" + tid + "-" + pg + "/";
            String html = OkHttpUtil.string(url, getHeaders(url));
            Document doc = Jsoup.parse(html);
            JSONObject result = new JSONObject();
            int pageCount = 0;
            int page = -1;

            // 取页码相关信息
            Elements pageInfo = doc.select("ul.myui-page>li");
            if (pageInfo.size() == 0) {
                page = Integer.parseInt(pg);
                pageCount = page;
            } else {
                for (int i = 0; i < pageInfo.size(); i++) {
                    Element li = pageInfo.get(i);
                    Element a = li.selectFirst("a");
                    if (a == null)
                        continue;
                    String name = a.text();
                    if (page == -1 && a.hasClass("btn-warm")) {
                        Matcher matcher = regexPage.matcher(a.attr("href"));
                        if (matcher.find()) {
                            page = Integer.parseInt(matcher.group(1).split("-")[8]);
                        } else {
                            page = 0;
                        }
                    }
                    if (name.equals("尾页")) {
                        Matcher matcher = regexPage.matcher(a.attr("href"));
                        if (matcher.find()) {
                            pageCount = Integer.parseInt(matcher.group(1).split("-")[8]);
                        } else {
                            pageCount = 0;
                        }
                        break;
                    }
                }
            }


            JSONArray videos = new JSONArray();
            if (!html.contains("没有找到您想要的结果哦")) {
                // 取当前分类页的视频列表
                Elements list = doc.select("div.myui-panel_bd>ul>li>div");
                for (int i = 0; i < list.size(); i++) {
                    Element vod = list.get(i);
                    String title = vod.selectFirst(".title").text();
                    String cover = vod.selectFirst(".myui-vodlist__thumb").attr("data-original");
                    String remark = vod.selectFirst("span.pic-text").text();

                    Matcher matcher = regexVid.matcher(vod.selectFirst(".myui-vodlist__thumb").attr("href"));
                    if (!matcher.find())
                        continue;
                    String id = matcher.group(1);
                    JSONObject v = new JSONObject();
                    v.put("vod_id", id);
                    v.put("vod_name", title);
                    v.put("vod_pic", cover);
                    v.put("vod_remarks", remark);
                    videos.put(v);
                }
            }
            result.put("page", page);
            result.put("pagecount", pageCount);
            result.put("limit", 48);
            result.put("total", pageCount <= 1 ? videos.length() : pageCount * 48);

            result.put("list", videos);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    /**
     * 视频详情信息
     *
     * @param ids 视频id
     * @return
     */
    @Override
    public String detailContent(List<String> ids) {
        try {
            // 视频详情url
            String url = siteUrl + "/voddetail/" + ids.get(0) + "/";
            Document doc = Jsoup.parse(OkHttpUtil.string(url, getHeaders(url)));
            JSONObject result = new JSONObject();
            JSONObject vodList = new JSONObject();

            // 取基本数据
            String vid = doc.selectFirst("span.mac_hits").attr("data-id");

            String cover = doc.selectFirst("a.myui-vodlist__thumb img").attr("data-original");
            String title = doc.selectFirst("div.myui-content__detail h1.title").text();
            String desc = doc.selectFirst("div.col-pd>span.sketch").text();
            String category = "", area = "", year = "", remark = "", director = "", actor = "";
            Elements span_text_muted = doc.select("div.myui-content__detail span.text-muted");
            for (int i = 0; i < span_text_muted.size(); i++) {
                Element text = span_text_muted.get(i);
                String info = text.text();
                if (info.equals("分类：")) {
                    category = text.nextElementSibling().text();
                } else if (info.equals("年份：")) {
                    year = text.nextElementSibling().text();
                } else if (info.equals("地区：")) {
                    area = text.nextElementSibling().text();
                } else if (info.equals("更新：")) {
                    remark = text.nextElementSibling().text();
                } else if (info.equals("导演：")) {
                    List<String> directors = new ArrayList<>();
                    Elements aa = text.parent().select("a");
                    for (int j = 0; j < aa.size(); j++) {
                        directors.add(aa.get(j).text());
                    }
                } else if (info.equals("主演：")) {
                    List<String> actors = new ArrayList<>();
                    Elements aa = text.parent().select("a");
                    for (int j = 0; j < aa.size(); j++) {
                        actors.add(aa.get(j).text());
                    }
                    actor = TextUtils.join(",", actors);
                }
            }

            vodList.put("vod_id", vid);
            vodList.put("vod_name", title);
            vodList.put("vod_pic", cover);
            vodList.put("type_name", category);
            vodList.put("vod_year", year);
            vodList.put("vod_area", area);
            vodList.put("vod_remarks", remark);
            vodList.put("vod_actor", actor);
            vodList.put("vod_director", director);
            vodList.put("vod_content", desc);

            Map<String, String> vod_play = new TreeMap<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    try {
                        int sort1 = playerConfig.getJSONObject(o1).getInt("or");
                        int sort2 = playerConfig.getJSONObject(o2).getInt("or");

                        if (sort1 == sort2) {
                            return 1;
                        }
                        return sort1 - sort2 > 0 ? 1 : -1;
                    } catch (JSONException e) {
                        SpiderDebug.log(e);
                    }
                    return 1;
                }
            });

            // 取播放列表数据
            Elements sources = doc.select("div.myui-panel__head").get(1).select("h3");
            Elements sourceList = doc.select("ul.myui-content__list");

            for (int i = 0; i < sources.size(); i++) {
                Element source = sources.get(i);
                String sourceName = source.text();
                boolean found = false;
                for (Iterator<String> it = playerConfig.keys(); it.hasNext(); ) {
                    String flag = it.next();
                    if (playerConfig.getJSONObject(flag).getString("sh").equals(sourceName)) {
                        sourceName = flag;
                        found = true;
                        break;
                    }
                }
                if (!found)
                    continue;
                String playList = "";
                Elements playListA = sourceList.get(i).select("li>a");
                List<String> vodItems = new ArrayList<>();

                for (int j = 0; j < playListA.size(); j++) {
                    Element vod = playListA.get(j);
                    Matcher matcher = regexPlay.matcher(vod.attr("href"));
                    if (!matcher.find())
                        continue;
                    String playURL = matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3);
                    vodItems.add(vod.text() + "$" + playURL);
                }
                if (vodItems.size() > 0)
                    playList = TextUtils.join("#", vodItems);

                if (playList.length() == 0)
                    continue;

                vod_play.put(sourceName, playList);
            }

            if (vod_play.size() > 0) {
                String vod_play_from = TextUtils.join("$$$", vod_play.keySet());
                String vod_play_url = TextUtils.join("$$$", vod_play.values());
                vodList.put("vod_play_from", vod_play_from);
                vodList.put("vod_play_url", vod_play_url);
            }
            JSONArray list = new JSONArray();
            list.put(vodList);
            result.put("list", list);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }

    /**
     * 获取视频播放信息
     *
     * @param flag     播放源
     * @param id       视频id
     * @param vipFlags 所有可能需要vip解析的源
     * @return
     */
    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) {
        try {
            //定义播放用的headers
            JSONObject headers = new JSONObject();
            //headers.put("Host", " cokemv.co");
            headers.put("origin", " https://nfxhd.com");
            headers.put("User-Agent", " Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");
            headers.put("Accept", " */*");
            headers.put("Accept-Language", " zh-CN,zh;q=0.9,en-US;q=0.3,en;q=0.7");
            headers.put("Accept-Encoding", " gzip, deflate");


            // 播放页 url
            String url = siteUrl + "/vodplay/" + id + "/";
            Document doc = Jsoup.parse(OkHttpUtil.string(url, getHeaders(url)));
            Elements allScript = doc.select("script");
            JSONObject result = new JSONObject();
            for (int i = 0; i < allScript.size(); i++) {
                String scContent = allScript.get(i).html().trim();
                if (scContent.startsWith("var player_")) { // 取直链
                    int start = scContent.indexOf('{');
                    int end = scContent.lastIndexOf('}') + 1;
                    String json = scContent.substring(start, end);
                    JSONObject player = new JSONObject(json);
                    if (playerConfig.has(player.getString("from"))) {
                        JSONObject pCfg = playerConfig.getJSONObject(player.getString("from"));
                        String videoUrl = player.getString("url");
                        String playUrl = pCfg.getString("pu");
                        result.put("parse", pCfg.getInt("sn"));
                        result.put("playUrl", playUrl);
                        result.put("url", videoUrl);
                        result.put("header", headers.toString());
                    }
                    break;
                }
            }
            return result.toString();
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
            long currentTime = System.currentTimeMillis();
            String url = siteUrl + "/index.php/ajax/suggest?mid=1&wd=" + URLEncoder.encode(key) + "&limit=10&timestamp=" + currentTime;
            JSONObject searchResult = new JSONObject(OkHttpUtil.string(url, getHeaders(url)));
            JSONObject result = new JSONObject();
            JSONArray videos = new JSONArray();
            if (searchResult.getInt("total") > 0) {
                JSONArray lists = new JSONArray(searchResult.getString("list"));
                for (int i = 0; i < lists.length(); i++) {
                    JSONObject vod = lists.getJSONObject(i);
                    String id = vod.getString("id");
                    String title = vod.getString("name");
                    String cover = vod.getString("pic");
                    JSONObject v = new JSONObject();
                    v.put("vod_id", id);
                    v.put("vod_name", title);
                    v.put("vod_pic", cover);
                    v.put("vod_remarks", "");
                    videos.put(v);
                }
            }
            result.put("list", videos);
            return result.toString();
        } catch (Exception e) {
            SpiderDebug.log(e);
        }
        return "";
    }
}
