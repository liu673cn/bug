猫影视的XBiubiu规则
------------------
### XBiubiu `使用方法`
```网站分类
{ 
  "name": "韩剧在线", //------填网站名称 
  "tihuan": "cnzz.com",  
  "url": "https://www.52kpop1.com",  //------填网站链接 
  "shouye": "4", 
  "fenlei": "电影$/vodshow/1--------#韩剧$/vodshow/2--------#韩综$/vodshow/3--------", //------网站列表的分类 
  "houzhui": "---.html", //------网站翻页链接的后缀 
```
  ![b01](https://liu673cn.github.io/mao/sub/Xbb/b01.jpg) <br />
  ------------------
```列表数组二次截取
  "shifouercijiequ": "0",  //------截取的列表数组是否需要二次截取，0不需要，1需要
  "jiequqian": "空", //------截取关键词前
  "jiequhou": "空", //------截取关键词后
```
  ![b01.1](https://liu673cn.github.io/mao/sub/Xbb/b01.1.jpg) <br />
------------------
```列表数组
  "jiequshuzuqian": "class=\"stui-vodlist__box\"", //------截取的列表数组的前关键词,截取的关键词有 " 的用 \ 进行转义
  "jiequshuzuhou": "</span>", //------截取的列表数组的后关键词
```
![b02.1](https://liu673cn.github.io/mao/sub/Xbb/b02.1.jpg)<br />
------------------
```资源图片
  "tupianqian": "data-original=\"", //------列表中资源的图片前关键词,截取的关键词有 " 的用 \ 进行转义 
  "tupianhou": "\"", //------列表中资源的图片后关键词
  "biaotiqian": "title=\"",
  "biaotihou": "\"", 
  "lianjieqian": "href=\"",
  "lianjiehou": "\"", 
```
![b02.2](https://liu673cn.github.io/mao/sub/Xbb/b02.2.jpg)<br />
------------------
```搜索部分
  "sousuoqian": "/index.php/ajax/suggest?mid=1&wd=", //------搜索部分基本不用动，现在网站基本都是苹果CMS，所有搜索是固定的。
  "sousuohou": "&limit=500",
  "sousuohouzhui": "/play/", //------搜索页影片跳转详情页的中间标识链接部分
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
```
![b03](https://liu673cn.github.io/mao/sub/Xbb/b03.jpg) <br />
------------------
```播放列表数组
  "bfjiequshuzuqian": "class=\"stui-content__playlist", //------播放截取的列表数组的前关键词 
  "bfjiequshuzuhou": "</ul>", //------播放截取的列表数组的后关键词
  "zhuangtaiqian": "更新：</span>", //------下面的是详情内容可以不动 
  "zhuangtaihou": "</p>", 
  "daoyanqian": "导演：</span>", 
  "daoyanhou": "</p>",
  "zhuyanqian": "主演：</span>", 
  "zhuyanhou": "</p>", <br />
  "juqingqian": "简介：</span>",
  "juqinghou": "</div>",
  "bfyshifouercijiequ": "0", 
  "bfyjiequqian": "空", 
  "bfyjiequhou": "空", 
```
![b04](https://liu673cn.github.io/mao/sub/Xbb/b04.jpg) <br /> 
------------------
```播放链接
  "bfyjiequshuzuqian": "<a",//------下面的是播放链接关键词<a href="链接">第一集</a> 
  "bfyjiequshuzuhou": "/a>", 
  "bfbiaotiqian": ">", 
  "bfbiaotihou": "<", 
  "bflianjieqian": "href=\"",
  "bflianjiehou": "\"" 
} 
```
