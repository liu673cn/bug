TVBox的XBiubiu规则
------------------
### XBiubiu `使用方法`
```网站分类
{
  "author": "模板1",
  "name": "voflix HD",
  "url": "https://www.voflix.com",
  "tihuan": "51.la",
  // 这个不用动，是个别网站播放需要请求头时才用到
  //"User": "User-Agent:Dart/2.14 (dart:io)",
  "User": "空",
  "shouye": "1",
  // 网站列表分类                
  "fenlei": "电影$/show/1--------#剧集$/show/2--------#动漫$/show/3--------#综艺$/show/4--------",
  // 网站翻页链接的后缀
  "houzhui": "---.html",
```
  ![b01](https://liu673cn.github.io/box/sub/img/xbb01.jpg) <br />
  ------------------
```列表数组截取
  // 截取的列表数组是否需要二次截取，0不需要，1需要，空
  "shifouercijiequ": "1",
  "jiequqian": "odule-page",
  "jiequhou": "<div id=\"page\">",
  // 截取的列表数组的前，后关键词,截取的关键词有 " 的用 \ 进行转义
  "jiequshuzuqian": "<a",
  "jiequshuzuhou": "no-referrer",
  // 列表中资源的图片前,后关键词,截取的关键词有 " 的用 \ 进行转义
  "tupianqian": "data-original=\"",
  "tupianhou": "\"",
  //列表中资源的标题前,后关键词,截取的关键词有 " 的用 \ 进行转义
  "biaotiqian": "title=\"",
  "biaotihou": "\"",
  // 列表中资源的详情页跳转链接前关键词,截取的关键词有 " 的用 \ 进行转义
  "lianjieqian": "href=\"",
  "lianjiehou": "\"",
  ```
  ![b01](https://liu673cn.github.io/box/sub/img/xbb02.jpg) <br />
  ------------------
```网站搜索部分
  // 搜索部分基本不用动，现在网站基本都是苹果CMS，所有搜索是固定的。/vodsearch/-------------.html?wd=
  "sousuoqian": "/index.php/ajax/suggest?mid=1&wd=",
  "sousuohou": "&limit=500",
  // 搜索页，影片跳转详情页的中间标识链接部分
  "sousuohouzhui": "/detail/",
```
  ![b01](https://liu673cn.github.io/box/sub/img/xbb03.jpg) <br />
------------------
```搜索参数不用改
  "ssmoshi": "0",
  "sousuoshifouercijiequ": "0",
  "jspic": "pic",
  "jsname": "name",
  "jsid": "id",
  "ssjiequqian": "空",
  "ssjiequhou": "空",
  "ssjiequshuzuqian": "空",
  "ssjiequshuzuhou": "空",
  "sstupianqian": "空",
  "sstupianhou": "空",
  "ssbiaotiqian": "空",
  "ssbiaotihou": "空",
  "sslianjieqian": "空",
  "sslianjiehou": "空",
```
```播放列表截取
   // 截取的播放列表数组是否需要二次截取，0不需要，1需要，空
  "bfshifouercijiequ": "0",
  "bfjiequqian": "空",
  "bfjiequhou": "空",
  // 播放截取的列表数组的前,后关键词
  "bfjiequshuzuqian": "class=\"module-list",
  "bfjiequshuzuhou": "</div>",
```
  ![b01](https://liu673cn.github.io/box/sub/img/xbb04.jpg) <br />
------------------
```详情页状态
  // 详情页状态,导演,主演,简介关键词
  "zhuangtaiqian": "更新：</span>",
  "zhuangtaihou": "</div>",
  "daoyanqian": "导演：</span>",
  "daoyanhou": "</div>",
  "zhuyanqian": "主演：</span>",
  "zhuyanhou": "</div>",
  "juqingqian": "class=\"module-info-introduction-content\"",
  "juqinghou": "</div>",
```
  ![b01](https://liu673cn.github.io/box/sub/img/xbb05.jpg) <br />
  ------------------
```播放剧集数组
  // 播放页是否需要二次截取，0不需要，1需要，空
  "bfyshifouercijiequ": "0",
  "bfyjiequqian": "空",
  "bfyjiequhou": "空",
  // 播放剧集数组前,后关键词
  "bfyjiequshuzuqian": "<a",
  "bfyjiequshuzuhou": "/a>",
  // 播放剧集标题前,后关键词
  "bfbiaotiqian": "<span>",
  "bfbiaotihou": "</span>",
  // 播放剧集链接前,后关键词
  "bflianjieqian": "href=\"",
  "bflianjiehou": "\""
}
```
{"key":"csp_Alist1","name":"🗂Alist合集1","type":3,"api":"csp_Alist","searchable":0,"quickSearch":0,"filterable":0,"ext":"https://alist.youte.ml"},

{"key":"csp_alist2","name":"🗂Alist合集2","type": 3, "api": "csp_Alist", "searchable": 1,"quickSearch": 1,"filterable": 0, "ext": "晴园$https://alist.52qy.repl.co#一只鱼$https://alist.youte.ml#讯维云盘$https://pan.xwbeta.com#神族九帝$https://alist.shenzjd.com#姬路白雪の资源小站$https://pan.jlbx.xyz#七米蓝$https://al.chirmyram.com#9T(Adult)$https://drive.9t.ee#小兵组$https://6vv.app#梓澪妙妙$https://xn--i0v44m.xyz#Alist测试$https://w.liucn.repl.co"},

{"key":"csp_Alist3","name":"🗂Alist合集3","type":3,"api":"csp_Alist","searchable":1,"quickSearch":0,"filterable":0,"ext":"https://raw.iqiq.io/liu673cn/box/main/sub/ext/Alist.json"},
