## 猫影视TV211正式版

本版依旧是做细节改善，图解新功能，优化用户体验。

1、配置本地缓存：
在设置页面-当前配置接口，多了一个配置缓存（上个版本就已经具备），戳一下可以调出菜单选项，开启后，以后用新版覆盖安装，可以保留原来的配置接口，也就是说用户的配置接口是支持缓存在本地的，如果关闭了，那就不会缓存到本地。

2、内置壁纸更新：
更丰富多样了，以前基本就几种，现在有好多种了。

3、新增推送功能：
手机“推送”电视观看方式（前提条件：必须是手机、电视同一局域网）

### 最新版下载地址

https://pan.lanzoui.com/b0c3cb92f

### Github RAW 加速服务
https://www.7ed.net/#/raw-cdn

https://raw.githubusercontents.com/名字/仓库/main(分支)/子目录/txt.txt

https://raw.githubusercontents.com/liu673cn/mao/main/sub/cat.json

<pre><code class="language-自定义规则讲解演示">

{
  "name": "真不卡影院-不支持搜索",
  "url": "https://www.zhenbuka5.com",  //填网站链接
  "tihuan": "cnzz.com",  //这个不用动，是个别网站嗅探时过滤地址用的
  "User": "空",  //这个不用动，是个别网站播放需要请求头时才用到
  "shouye": "1", //这个不用动，首页显示
  "fenlei": "电视剧$/vodshow/2--------#电影$/vodshow/1--------#综艺$/vodshow/3--------#动漫$/vodshow/4--------#动作片$/vodshow/6--------#喜剧片$/vodshow/7--------#爱情片$/vodshow/8--------#科幻片$/vodshow/9--------#恐怖片$/vodshow/10--------#剧情片$/vodshow/11--------#战争片$/vodshow/12--------#国产剧$/vodshow/13--------#港台剧$/vodshow/14--------#日韩剧$/vodshow/15--------#欧美剧$/vodshow/16--------", //网站列表的分类
  "houzhui": "---/",  //网站翻页链接的后缀，有的是（.html）
  "shifouercijiequ": "0",  //截取的列表数组是否需要二次截取，0不需要，1需要
  "jiequqian": "空",  //不需要二次截取就填空
  "jiequhou": "空",  //不需要二次截取就填空
  "jiequshuzuqian": "class=\"stui-vodlist__box",  //截取的列表数组的前关键词,截取的关键词有 " 的用 \ 进行转义
  "jiequshuzuhou": "</span>",  //截取的列表数组的后关键词
  "tupianqian": "original=\"",  //列表中资源的图片前关键词
  "tupianhou": "\"",  //列表中资源的图片后关键词
  "biaotiqian": "title=\"",  //列表中资源的标题前关键词
  "biaotihou": "\"",  //列表中资源的标题后关键词,截取的关键词有
  "lianjieqian": "href=\"",  //列表中资源的详情页跳转链接前关键词
  "lianjiehou": "\"",  //列表中资源的详情页跳转链接后关键词
  //
  //搜索部分基本不用动，现在网站基本都是苹果CMS，所有搜索是固定的。
  "sousuoqian": "/index.php/ajax/suggest?mid=1&wd=", 
  "sousuohou": "&limit=50",
  "sousuohouzhui": "/voddetail/",  //搜索页影片跳转详情页的中间标识链接部分
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
  "bfshifouercijiequ": "0",
  "bfjiequqian": "空",
  "bfjiequhou": "空",
  "bfjiequshuzuqian": "class=\"stui-content__playlist",  //播放截取的列表数组的前关键词
  "bfjiequshuzuhou": "</ul>",  //播放截取的列表数组的后关键词
  "zhuangtaiqian": "更新：</span>",  //状态前关键词
  "zhuangtaihou": "</p>",  //状态后关键词
  "daoyanqian": "导演：</span>",  //导演前关键词
  "daoyanhou": "</、p>",  //导演后
  "zhuyanqian": "主演：</span>",  //主演前关键词
  "zhuyanhou": "</、p>",  //主演后
  "juqingqian": "简介：</span>",  //剧情前关键词
  "juqinghou": "</span>",  //剧情后
  "bfyshifouercijiequ": "0",  //二次截取的播放列表数组，是否需要，0不需要，1需要
  "bfyjiequqian": "空",  //二次截取前,不需要就填空
  "bfyjiequhou": "空",  //二次截取后
  "bfyjiequshuzuqian": "<a",  //播放剧集数组前关键词
  "bfyjiequshuzuhou": "/a>",  //播放剧集数组后
  "bfbiaotiqian": ">",  //播放剧集标题前关键词
  "bfbiaotihou": "<",  //状播放剧集标题后
  "bflianjieqian": "href=\"",  //播放剧集链接前关键词
  "bflianjiehou": "\""  //播放剧集链接后
}

</code></pre>
