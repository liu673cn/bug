### XPath规则套娃（需v2.0.4及以上版本）
----
套娃依赖自定义爬虫jar，同样需要在自定义json中加入相应的配置，**type=3, api为csp_XPath**，套娃相关规则配置在`ext`字段中，注意：ext字段值**只能是字符串**

为控制配置文件容量，同时支持在ext字段中直接配置规则和拉取规则的网址。 2021.10.21 by 小黄瓜

```json
    {
      "key": "csp_xpath_94sm",
      "name": "94神马(XPath)",
      "type": 3,
      "api": "csp_XPath",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/94sm.json"
    },
    {
      "key": "csp_xpath_jpys",
      "name": "极品影视(XPath)",
      "type": 3,
      "api": "csp_XPath",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/jpys.json"
    },
    {
      "key": "csp_xpath_age",
      "name": "AGE动漫(XPath)",
      "type": 3,
      "api": "csp_XPath",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/agefans.json"
    },
    {
      "key": "csp_xpath_jpyszl",
      "name": "极品直链(XPath)",
      "type": 3,
      "api": "csp_XPathMac",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/jpys.json"
    },
    {
      "key": "csp_xpath_cjt",
      "name": "CJT影视(XPath)",
      "type": 3,
      "api": "csp_XPathMac",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/cjtys.json"
    },
    {
      "key": "csp_xpath_tvci",
      "name": "大师兄(XPath)",
      "type": 3,
      "api": "csp_XPathFilter",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 1,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/tvci.json"
    },
    {
      "key": "csp_xpath_saohuotv",
      "name": "骚火电影(XPath)",
      "type": 3,
      "api": "csp_XPath",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/saohuotv2.json"
    },
    {
      "key": "csp_xpath_dm84",
      "name": "动漫巴士(XPath)",
      "type": 3,
      "api": "csp_XPath",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/dm84.json"
    },
    {
      "key": "csp_xpath_egg",
      "name": "蛋蛋影院(XPath)",
      "type": 3,
      "api": "csp_XPathEgg",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/egg.json"
    },
    {
      "key": "csp_xpath_555",
      "name": "555电影(XPath)",
      "type": 3,
      "api": "csp_XPath",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/555.json"
    },
    {
      "key": "csp_xpath_4kyu",
      "name": "一只鱼4K(XPath)",
      "type": 3,
      "api": "csp_XPath",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/4kyu.json"
    },
    {
      "key": "csp_xpath_miniku",
      "name": "Miniku(XPath)",
      "type": 3,
      "api": "csp_XPath",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/miniku.json"
    },
    {
      "key": "csp_xpath_pianba",
      "name": "Pianba(XPath)",
      "type": 3,
      "api": "csp_XPath",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/pianba.json"
    },
    {
      "key": "csp_xpath_Ole",
      "name": "Ole(EXO)",
      "type": 3,
      "api": "csp_XPath",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 0,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/olevod.json"
    },
    {
      "key": "唐人街",
      "name": "唐人街(P)",
      "type": 3,
      "api": "csp_XPathMacFilter",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 1,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/Renjie.json"
    },
    {
      "key": "Gimy",
      "name": "Gimy(P)",
      "type": 3,
      "api": "csp_XPathMacFilter",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 1,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/Gimy.json"
    },
    {
      "key": "Jumi",
      "name": "Jumi(P)",
      "type": 3,
      "api": "csp_XPathMacFilter",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 1,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/Jumi.json"
    },
    {
      "key": "Djx",
      "name": "瓜皮TV(P)",
      "type": 3,
      "api": "csp_XPathFilter",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 1,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/Djx.json"
    },
    {
      "key": "独播库",
      "name": "独播库(P)",
      "type": 3,
      "api": "csp_XPathMacFilter",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 1,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/Duboku.json"
    },
    {
      "key": "Sky4k",
      "name": "Sky4k(P)[en]",
      "type": 3,
      "api": "csp_XPathMacFilter",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 1,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/Sky4k.json"
    },
    {
      "key": "Nfuxs",
      "name": "南府(P)",
      "type": 3,
      "api": "csp_XPathFilter",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 1,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/Nfuxs.json"
    },
    {
      "key": "Aixixi",
      "name": "爱西西(P)",
      "type": 3,
      "api": "csp_XPathFilter",
      "searchable": 1,
      "quickSearch": 1,
      "filterable": 1,
      "ext": "https://cdn.jsdelivr.net/gh/catvod/CatVodTVSpider@master/xpath/Aixixi.json"
    }

```

### 套娃规则

demo配置写的比较细，不一定所有字段都要有，具体还是自己多试一试

```json
{
	"ua": "",
    // 首页地址 用于获取 分类和首页推荐
	"homeUrl": "http://www.9rmb.com",
    // 分类节点
	"cateNode": "//ul[contains(@class,'navbar-nav')]/li/a[contains(@href, '.html') and not(contains(@href, '6'))]",
    // 分类名
	"cateName": "/text()",
    // 分类id
	"cateId": "/@href",
    // 分类id二次处理正则
	"cateIdR": "/type/(\\d+).html",
    // 手动设置分类，如果手动设置了分类则不使用上面的分类xpath获取分类  例如 "cateManual": {"电影": "1", "电视剧": "2"},
	"cateManual": {},
    // 首页推荐视频的节点
	"homeVodNode": "//div[@class='col-md-12 movie-item-out']//a[not(contains(@href, '6'))]/parent::*/parent::*/parent::*/div[contains(@class, 'movie-item-out') and position()<10]/div[@class='movie-item']/a",
    // 首页推荐视频的名称
	"homeVodName": "/@title",
    // 二次处理正则
    "homeVodNameR": "",
    // 首页推荐视频的id
	"homeVodId": "/@href",
    // 二次处理正则
	"homeVodIdR": "/show/(\\w+).html",
    // 首页推荐视频的图片
	"homeVodImg": "/img/@src",
    // 二次处理正则
    "homeVodImgR": "",
    // 首页推荐视频的简介
	"homeVodMark": "/button/text()",
    // 二次处理正则
    "homeVodMarkR": "",
    // 分类页地址 {cateId} 分类id {catePg} 当前页
	"cateUrl": "http://www.9rmb.com/type/{cateId}/{catePg}.html",
    // 同上面的homeVod字段 分类列表中的视频信息
	"cateVodNode": "//div[@class='movie-item']/a",
	"cateVodName": "/@title",
	"cateVodId": "/@href",
	"cateVodIdR": "/show/(\\w+).html",
	"cateVodImg": "/img/@src",
	"cateVodMark": "/button/text()",
    // 详情页地址 用于获取详情页信息 及 播放列表和地址
	"dtUrl": "http://www.9rmb.com/show/{vid}.html",
    // 详情节点
	"dtNode": "//div[@class='container-fluid']",
    // 视频名
	"dtName": "//div[@class='col-md-9']//div[@class='col-md-4']//img/@alt",
	"dtNameR": "",
    // 视频图片
	"dtImg": "//div[@class='col-md-9']//div[@class='col-md-4']//img/@src",
	"dtImgR": "",
    // 视频分类
	"dtCate": "//div[@class='col-md-8']//span[@class='info-label' and contains(text(), '类型')]/parent::*/following-sibling::*/text()",
	"dtCateR": "",
    // 视频年份
	"dtYear": "//div[@class='col-md-8']//span[@class='info-label' and contains(text(), '日期')]/parent::*/following-sibling::*/text()",
	"dtYearR": "",
    // 视频地区
	"dtArea": "//div[@class='col-md-8']//span[@class='info-label' and contains(text(), '国家')]/parent::*/following-sibling::*/text()",
	"dtAreaR": "",
    // 视频状态
	"dtMark": "//div[@class='col-md-8']//span[@class='info-label' and contains(text(), '状态')]/parent::*/following-sibling::*/text()",
	"dtMarkR": "",
    // 主演
	"dtActor": "//div[@class='col-md-8']//span[@class='info-label' and contains(text(), '主演')]/parent::*/following-sibling::*/text()",
	"dtActorR": "",
    // 导演
	"dtDirector": "//div[@class='col-md-8']//span[@class='info-label' and contains(text(), '导演')]/parent::*/following-sibling::*/text()",
	"dtDirectorR": "",
    // 视频简介
	"dtDesc": "//p[@class='summary']/text()",
	"dtDescR": "",
    // 播放源节点
	"dtFromNode": "//div[contains(@class,'resource-list')]/div[@class='panel-heading']/strong",
    // 播放源名称
	"dtFromName": "/text()",
    // 二次处理正则
	"dtFromNameR": "\\S+\\.(\\S+) \\(",
    // 播放列表节点
	"dtUrlNode": "//div[contains(@class,'resource-list')]/ul[@class='dslist-group']",
    // 播放地址节点
	"dtUrlSubNode": "/li/a",
    // 播放地址
	"dtUrlId": "@href",
    // 二次处理正则
	"dtUrlIdR": "/play/(\\S+).html",
    // 剧集名称
	"dtUrlName": "/text()",
    // 二次处理正则
	"dtUrlNameR": "",
    // 播放页面的地址 {playUrl} 对应上面 dtUrlId 获取到的地址
	"playUrl": "http://www.9rmb.com/play/{playUrl}.html",
    // 解析webview的user-agent
	"playUa": "",
    // 搜索地址
	"searchUrl": "http://www.9rmb.com/search?wd={wd}",
    // 同上面的homeVod字段 搜索结果中的视频信息, 这里有对苹果cms suggest搜索接口的特殊支持，参考示例中的极品影视
	"scVodNode": "//div[@class='movie-item']/a",
	"scVodName": "/@title",
	"scVodId": "/@href",
	"scVodIdR": "/show/(\\w+).html",
	"scVodImg": "/img/@src",
	"scVodMark": "/button/text()"
}
```
