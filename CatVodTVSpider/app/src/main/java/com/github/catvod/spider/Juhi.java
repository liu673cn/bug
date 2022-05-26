package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;

import com.github.catvod.crawler.Spider;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.Misc;
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

public class Juhi extends Spider {

    private static final String siteUrl = "https://www.juhi.cc/";
    private static final String siteHost = "juhi.cc";

    protected JSONObject playerConfig;
    protected JSONObject filterConfig;

    protected Pattern regexCategory = Pattern.compile("/vodtype/(\\d+)/");
    protected Pattern regexVid = Pattern.compile("/voddetail/(\\d+)/");
    protected Pattern regexPlay = Pattern.compile("/vodplay/(\\d+)-(\\d+)-(\\d+)/");
    protected Pattern regexPage = Pattern.compile("/vodshow/(\\S+)/");

    @Override
    public void init(Context context) {
        super.init(context);
        try {
            playerConfig = new JSONObject("{\"tkm3u8\":{\"sh\":\"✦剧嗨专线\",\"pu\":\"https://jx.huishij.com/yun/?url=\",\"sn\":1,\"or\":999},\"if101\":{\"sh\":\"✦蓝光超清\",\"pu\":\"\",\"sn\":0,\"or\":999},\"dbm3u8\":{\"sh\":\"北美高清①\",\"pu\":\"\",\"sn\":0,\"or\":999},\"dbyun\":{\"sh\":\"北美高清②\",\"pu\":\"\",\"sn\":0,\"or\":999},\"wjm3u8\":{\"sh\":\"北美高清③\",\"pu\":\"\",\"sn\":0,\"or\":999},\"kbm3u8\":{\"sh\":\"全球线路①\",\"pu\":\"\",\"sn\":0,\"or\":999},\"hnyun\":{\"sh\":\"全球线路②\",\"pu\":\"\",\"sn\":0,\"or\":999},\"hnm3u8\":{\"sh\":\"全球线路③\",\"pu\":\"\",\"sn\":0,\"or\":999},\"kbzy\":{\"sh\":\"全球线路④\",\"pu\":\"\",\"sn\":0,\"or\":999},\"fanqie\":{\"sh\":\"全球线路⑤\",\"pu\":\"\",\"sn\":0,\"or\":999},\"kuaiyun\":{\"sh\":\"快速线路\",\"pu\":\"https://qimihe.com/?url=\",\"sn\":1,\"or\":999},\"leyum3u8\":{\"sh\":\"午夜专线1\",\"pu\":\"\",\"sn\":0,\"or\":999},\"lym3u8\":{\"sh\":\"午夜专线2\",\"pu\":\"\",\"sn\":0,\"or\":999},\"dplayer\":{\"sh\":\"DPlayer-H5播放器\",\"pu\":\"\",\"sn\":0,\"or\":999},\"videojs\":{\"sh\":\"videojs-H5播放器\",\"pu\":\"\",\"sn\":0,\"or\":999},\"iva\":{\"sh\":\"iva-H5播放器\",\"pu\":\"\",\"sn\":0,\"or\":999},\"iframe\":{\"sh\":\"iframe外链数据\",\"pu\":\"\",\"sn\":0,\"or\":999},\"link\":{\"sh\":\"外链数据\",\"pu\":\"\",\"sn\":0,\"or\":999},\"swf\":{\"sh\":\"Flash文件\",\"pu\":\"\",\"sn\":0,\"or\":999},\"flv\":{\"sh\":\"Flv文件\",\"pu\":\"\",\"sn\":0,\"or\":999},\"bjyun\":{\"sh\":\"bjyun\",\"pu\":\"\",\"sn\":0,\"or\":999},\"bjm3u8\":{\"sh\":\"bjm3u8\",\"pu\":\"\",\"sn\":0,\"or\":999},\"wasu\":{\"sh\":\"华数直连\",\"pu\":\"\",\"sn\":0,\"or\":999},\"funshion\":{\"sh\":\"风行直连\",\"pu\":\"\",\"sn\":0,\"or\":999},\"pptv\":{\"sh\":\"PPTV直连\",\"pu\":\"https://jx.m3u8.tv/jiexi/?url=\",\"sn\":1,\"or\":999},\"letv\":{\"sh\":\"乐视直连\",\"pu\":\"https://jx.m3u8.tv/jiexi/?url=\",\"sn\":1,\"or\":999},\"mgtv\":{\"sh\":\"芒果直连\",\"pu\":\"https://jx.m3u8.tv/jiexi/?url=\",\"sn\":1,\"or\":999},\"youku\":{\"sh\":\"优酷直连\",\"pu\":\"https://jx.m3u8.tv/jiexi/?url=\",\"sn\":1,\"or\":999},\"qiyi\":{\"sh\":\"爱奇艺直连\",\"pu\":\"https://jx.m3u8.tv/jiexi/?url=\",\"sn\":1,\"or\":999},\"qq\":{\"sh\":\"腾讯直连\",\"pu\":\"https://jx.m3u8.tv/jiexi/?url=\",\"sn\":1,\"or\":999},\"m1905\":{\"sh\":\"M1905直连\",\"pu\":\"https://jx.m3u8.tv/jiexi/?url=\",\"sn\":1,\"or\":999},\"sohu\":{\"sh\":\"搜狐直连\",\"pu\":\"https://jx.m3u8.tv/jiexi/?url=\",\"sn\":1,\"or\":999}}");
            filterConfig = new JSONObject("{\"20\":[{\"key\":\"0\",\"name\":\"类型\",\"value\":[{\"n\":\"全部\",\"v\":\"20\"},{\"n\":\"动作片\",\"v\":\"24\"},{\"n\":\"喜剧片\",\"v\":\"25\"},{\"n\":\"愛情片\",\"v\":\"26\"},{\"n\":\"科幻片\",\"v\":\"27\"},{\"n\":\"恐怖片\",\"v\":\"28\"},{\"n\":\"劇情片\",\"v\":\"11\"},{\"n\":\"戰爭片\",\"v\":\"30\"},{\"n\":\"动画电影\",\"v\":\"33\"}]},{\"key\":1,\"name\":\"地区\",\"value\":[{\"n\":\"全部\",\"v\":\"\"},{\"n\":\"大陆\",\"v\":\"大陆\"},{\"n\":\"香港\",\"v\":\"香港\"},{\"n\":\"台湾\",\"v\":\"台湾\"},{\"n\":\"美国\",\"v\":\"美国\"},{\"n\":\"法国\",\"v\":\"法国\"},{\"n\":\"英国\",\"v\":\"英国\"},{\"n\":\"日本\",\"v\":\"日本\"},{\"n\":\"韩国\",\"v\":\"韩国\"},{\"n\":\"德国\",\"v\":\"德国\"},{\"n\":\"泰国\",\"v\":\"泰国\"},{\"n\":\"印度\",\"v\":\"印度\"},{\"n\":\"意大利\",\"v\":\"意大利\"},{\"n\":\"西班牙\",\"v\":\"西班牙\"},{\"n\":\"加拿大\",\"v\":\"加拿大\"},{\"n\":\"其他\",\"v\":\"其他\"}]},{\"key\":11,\"name\":\"年份\",\"value\":[{\"n\":\"全部\",\"v\":\"\"},{\"n\":\"2021\",\"v\":\"2021\"},{\"n\":\"2020\",\"v\":\"2020\"},{\"n\":\"2019\",\"v\":\"2019\"},{\"n\":\"2018\",\"v\":\"2018\"},{\"n\":\"2017\",\"v\":\"2017\"},{\"n\":\"2016\",\"v\":\"2016\"},{\"n\":\"2015\",\"v\":\"2015\"},{\"n\":\"2014\",\"v\":\"2014\"},{\"n\":\"2013\",\"v\":\"2013\"},{\"n\":\"2012\",\"v\":\"2012\"},{\"n\":\"2011\",\"v\":\"2011\"},{\"n\":\"2010\",\"v\":\"2010\"}]},{\"key\":2,\"name\":\"排序\",\"value\":[{\"n\":\"时间\",\"v\":\"time\"},{\"n\":\"人气\",\"v\":\"hits\"},{\"n\":\"评分\",\"v\":\"score\"}]}],\"21\":[{\"key\":\"tid\",\"name\":\"类型\",\"value\":[{\"n\":\"全部\",\"v\":\"\"},{\"n\":\"国产剧\",\"v\":\"34\"},{\"n\":\"港剧\",\"v\":\"35\"},{\"n\":\"韩剧\",\"v\":\"37\"},{\"n\":\"欧美剧\",\"v\":\"36\"},{\"n\":\"日本剧\",\"v\":\"38\"},{\"n\":\"台湾剧\",\"v\":\"39\"},{\"n\":\"海外剧\",\"v\":\"40\"}]},{\"key\":11,\"name\":\"年份\",\"value\":[{\"n\":\"全部\",\"v\":\"\"},{\"n\":\"2021\",\"v\":\"2021\"},{\"n\":\"2020\",\"v\":\"2020\"},{\"n\":\"2019\",\"v\":\"2019\"},{\"n\":\"2018\",\"v\":\"2018\"},{\"n\":\"2017\",\"v\":\"2017\"},{\"n\":\"2016\",\"v\":\"2016\"},{\"n\":\"2015\",\"v\":\"2015\"},{\"n\":\"2014\",\"v\":\"2014\"},{\"n\":\"2013\",\"v\":\"2013\"},{\"n\":\"2012\",\"v\":\"2012\"},{\"n\":\"2011\",\"v\":\"2011\"},{\"n\":\"2010\",\"v\":\"2010\"},{\"n\":\"2009\",\"v\":\"2009\"},{\"n\":\"2008\",\"v\":\"2008\"},{\"n\":\"2007\",\"v\":\"2007\"},{\"n\":\"2006\",\"v\":\"2006\"},{\"n\":\"2005\",\"v\":\"2005\"},{\"n\":\"2004\",\"v\":\"2004\"}]},{\"key\":2,\"name\":\"排序\",\"value\":[{\"n\":\"时间\",\"v\":\"time\"},{\"n\":\"人气\",\"v\":\"hits\"},{\"n\":\"评分\",\"v\":\"score\"}]}],\"22\":[{\"key\":0,\"name\":\"类型\",\"value\":[{\"n\":\"全部\",\"v\":\"22\"},{\"n\":\"大陆综艺\",\"v\":\"41\"},{\"n\":\"港台综艺\",\"v\":\"42\"},{\"n\":\"日韩综艺\",\"v\":\"43\"},{\"n\":\"欧美综艺\",\"v\":\"44\"}]},{\"key\":11,\"name\":\"年份\",\"value\":[{\"n\":\"全部\",\"v\":\"\"},{\"n\":\"2021\",\"v\":\"2021\"},{\"n\":\"2020\",\"v\":\"2020\"},{\"n\":\"2019\",\"v\":\"2019\"},{\"n\":\"2018\",\"v\":\"2018\"},{\"n\":\"2017\",\"v\":\"2017\"},{\"n\":\"2016\",\"v\":\"2016\"},{\"n\":\"2015\",\"v\":\"2015\"},{\"n\":\"2014\",\"v\":\"2014\"},{\"n\":\"2013\",\"v\":\"2013\"},{\"n\":\"2012\",\"v\":\"2012\"},{\"n\":\"2011\",\"v\":\"2011\"},{\"n\":\"2010\",\"v\":\"2010\"},{\"n\":\"2009\",\"v\":\"2009\"},{\"n\":\"2008\",\"v\":\"2008\"},{\"n\":\"2007\",\"v\":\"2007\"},{\"n\":\"2006\",\"v\":\"2006\"},{\"n\":\"2005\",\"v\":\"2005\"},{\"n\":\"2004\",\"v\":\"2004\"}]},{\"key\":2,\"name\":\"排序\",\"value\":[{\"n\":\"时间\",\"v\":\"time\"},{\"n\":\"人气\",\"v\":\"hits\"},{\"n\":\"评分\",\"v\":\"score\"}]}],\"23\":[{\"key\":0,\"name\":\"类型\",\"value\":[{\"n\":\"全部\",\"v\":\"23\"},{\"n\":\"国产动漫\",\"v\":\"45\"},{\"n\":\"日本动漫\",\"v\":\"46\"},{\"n\":\"欧美动漫\",\"v\":\"47\"}]},{\"key\":11,\"name\":\"年份\",\"value\":[{\"n\":\"全部\",\"v\":\"\"},{\"n\":\"2021\",\"v\":\"2021\"},{\"n\":\"2020\",\"v\":\"2020\"},{\"n\":\"2019\",\"v\":\"2019\"},{\"n\":\"2018\",\"v\":\"2018\"},{\"n\":\"2017\",\"v\":\"2017\"},{\"n\":\"2016\",\"v\":\"2016\"},{\"n\":\"2015\",\"v\":\"2015\"},{\"n\":\"2014\",\"v\":\"2014\"},{\"n\":\"2013\",\"v\":\"2013\"},{\"n\":\"2012\",\"v\":\"2012\"},{\"n\":\"2011\",\"v\":\"2011\"},{\"n\":\"2010\",\"v\":\"2010\"},{\"n\":\"2009\",\"v\":\"2009\"},{\"n\":\"2008\",\"v\":\"2008\"},{\"n\":\"2007\",\"v\":\"2007\"},{\"n\":\"2006\",\"v\":\"2006\"},{\"n\":\"2005\",\"v\":\"2005\"},{\"n\":\"2004\",\"v\":\"2004\"}]},{\"key\":2,\"name\":\"排序\",\"value\":[{\"n\":\"时间\",\"v\":\"time\"},{\"n\":\"人气\",\"v\":\"hits\"},{\"n\":\"评分\",\"v\":\"score\"}]}]}");
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
            Document doc = Jsoup.parse(OkHttpUtil.string(siteUrl, getHeaders(siteUrl)));
            // 分类节点
            Elements elements = doc.select("ul.nav-menu > li > a");
            JSONArray classes = new JSONArray();
            for (Element ele : elements) {
                //分类名
                String name = ele.text();
                boolean show = name.equals("电影") ||
                        name.equals("电视剧") ||
                        name.equals("综艺") ||
                        name.equals("动漫");
                if (show) {
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
            if (filter) {
                result.put("filters", filterConfig);
            }
            result.put("class", classes);
            try {
                // 取首页推荐视频列表
                Element homeList = doc.select("ul.myui-vodlist").get(0);
                Elements list = homeList.select("li > div > a");
                JSONArray videos = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    Element vod = list.get(i);
                    String title = vod.attr("title");
                    String cover = vod.attr("data-original");
                    String remark = vod.selectFirst("span.pic-text").text();
                    Matcher matcher = regexVid.matcher(vod.attr("href"));
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
            String[] urlParams = new String[]{"", "", "", "", "", "", "", "", "", "", "", ""};
            urlParams[0] = tid;
            urlParams[8] = pg;
            if (extend != null && extend.size() > 0) {
                for (Iterator<String> it = extend.keySet().iterator(); it.hasNext(); ) {
                    String key = it.next();
                    String value = extend.get(key);
                    urlParams[Integer.parseInt(key)] = URLEncoder.encode(value);
                }
            }
            // 获取分类数据的url
            String url = siteUrl + "/vodshow/" + TextUtils.join("-", urlParams) + "/";
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
                Elements list = doc.select("ul.myui-vodlist > li > div > a");
                for (int i = 0; i < list.size(); i++) {
                    Element vod = list.get(i);
                    String title = vod.attr("title");
                    String cover = vod.attr("data-original");
                    String remark = vod.selectFirst("span.pic-text").text();
                    Matcher matcher = regexVid.matcher(vod.attr("href"));
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
            //System.out.println(url);
            Document doc = Jsoup.parse(OkHttpUtil.string(url, getHeaders(url)));
            JSONObject result = new JSONObject();
            JSONObject vodList = new JSONObject();

            // 取基本数据
            String cover = doc.selectFirst("div.myui-content__thumb > a > img").attr("src");
            String title = doc.selectFirst("div.myui-content__thumb > a").attr("title");
            String desc = Jsoup.parse(doc.selectFirst("meta[name=description]").attr("content")).text();
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
                    director = TextUtils.join(",", directors);
                } else if (info.equals("主演：")) {
                    List<String> actors = new ArrayList<>();
                    Elements aa = text.parent().select("a");
                    for (int j = 0; j < aa.size(); j++) {
                        actors.add(aa.get(j).text());
                    }
                    actor = TextUtils.join(",", actors);
                }
            }

            vodList.put("vod_id", ids.get(0));
            vodList.put("vod_name", title);
            vodList.put("vod_pic", cover);
            vodList.put("type_name", category);
            vodList.put("vod_year", year);
            vodList.put("vod_area", area);
            vodList.put("vod_remarks", remark);
            vodList.put("vod_actor", actor);
            vodList.put("vod_director", director);
            vodList.put("vod_content", desc);
            //System.out.println(vodList.toString());
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
            Elements sources = doc.select("ul.nav-btn > li > a");
            //System.out.println(sources.size());
            Elements sourceList = doc.select("div.tab-content > div");
            //System.out.println(sourceList.size());
            for (int i = 0; i < sources.size(); i++) {
                Element source = sources.get(i);
                //System.out.println(sources.text().split("：")[0].split("』")[1]);
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
                Elements playListA = sourceList.get(i).select("ul > li > a");
                //System.out.println(playListA.size());
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
            //headers.put("origin", " https://juhi.cc");
            //headers.put("User-Agent", " Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");
            //headers.put("Accept", " */*");
            //headers.put("Accept-Language", " zh-CN,zh;q=0.9,en-US;q=0.3,en;q=0.7");
            //headers.put("Accept-Encoding", " gzip, deflate, br");


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
                        if (Misc.isVip(videoUrl)) { // 使用jx:1
                            try {
                                result.put("parse", 1);
                                result.put("jx", "1");
                                result.put("url", videoUrl);
                                return result.toString();
                            } catch (Exception e) {
                                SpiderDebug.log(e);
                            }
                        }
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
            //Document doc = Jsoup.parse(srr.content);
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
