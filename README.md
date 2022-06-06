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

https://raw-gh.gcdn.mirr.one/liu673cn/mao/main/sub/cat.json

### XBiubiu 使用方法
```markdown
{ 
  "name": "韩剧在线", //------填网站名称 
  "tihuan": "cnzz.com",  
  "url": "https://www.52kpop1.com",  //------填网站链接 
  "shouye": "4", 
  "fenlei": "电影$/vodshow/1--------#韩剧$/vodshow/2--------#韩综$/vodshow/3--------", //------网站列表的分类 
  "houzhui": "---.html", //------网站翻页链接的后缀 
  "shifouercijiequ": "0", 
  "jiequqian": "空", 
  "jiequhou": "空", 
  ```
![b01](https://raw.githubusercontents.com/liu673cn/mao/main/sub/Xbb/b01.jpg) <br />
```markdown
  "jiequshuzuqian": "class=\"stui-vodlist__box\"", //------截取的列表数组的前关键词,截取的关键词有 " 的用 \ 进行转义
  "jiequshuzuhou": "</span>", //------截取的列表数组的后关键词
  "tupianqian": "data-original=\"", //------列表中资源的图片前关键词,截取的关键词有 " 的用 \ 进行转义 
  "tupianhou": "\"", //------列表中资源的图片后关键词
  "biaotiqian": "title=\"",
  "biaotihou": "\"", 
  "lianjieqian": "href=\"",
  "lianjiehou": "\"", 
```
![b02](https://raw.githubusercontents.com/liu673cn/mao/main/sub/Xbb/b02.1.jpg)
```markdown
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
![b03](https://raw.githubusercontents.com/liu673cn/mao/main/sub/Xbb/b03.jpg) 
```markdown
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
![b04](https://raw.githubusercontents.com/liu673cn/mao/main/sub/Xbb/b04.jpg) <br /> 
```markdown
  "bfyjiequshuzuqian": "<a",//------下面的是播放链接关键词<a href="链接">第一集</a> 
  "bfyjiequshuzuhou": "/a>", 
  "bfbiaotiqian": ">", 
  "bfbiaotihou": "<", 
  "bflianjieqian": "href=\"",
  "bflianjiehou": "\"" 
} 
```
