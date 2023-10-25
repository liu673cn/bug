### TVBox进阶必备利器 

直播源搜索  
[https://foodieguide.com/iptvsearch/](https://foodieguide.com/iptvsearch/)  

直播源批量检测工具  
[https://liucn.lanzouv.com/ikpYA097ol3a](https://liucn.lanzouv.com/ikpYA097ol3a)  
[https://tsq.lanzouf.com/b0c4p2fba](https://tsq.lanzouf.com/b0c4p2fba) 密 123  

#### 1.直播地址需要加密才能使用：首先准备网址https://base64.us/ 或者其他base64 加解码工具
![TV01](https://liu673cn.github.io/box/sub/img/tv01.jpg) <br />

#### 2.在json文件里找到直播地址即live 这一行，红框内的乱码就是我们要编码内容及被替换内容

![TV01](https://liu673cn.github.io/box/sub/img/tv02.jpg) <br />
#### 3.把tv直播的接口文件下载后，找到自己所放的文件夹，然后手动更改存储位置，复制到编码网页里，编辑编码我们即可得到一个看似乱码样的地址，把json里的地址替换即可，注意：后面的等号也是地址内容。

  "lives": [
    {
      "name": "影视",
      "url": "https://agit.ai/leevi/duo/raw/branch/master/v.txt",
      "type": 0,
      "epg": "http://epg.51zmt.top:8000/api/diyp/?ch={name}&date={date}",
      "logo": "https://epg.112114.xyz/logo/{name}.png"
    },
    {
      "group": "redirect",
      "channels": [
        {
          "name": "live",
          "epg": "http://epg.51zmt.top:8000/api/diyp/?ch={name}&date={date}",
          "urls": [
            "proxy://do=live&type=txt&ext=aHR0cHM6Ly9hZ2l0LmFpL2xlZXZpL2R1by9yYXcvYnJhbmNoL21hc3Rlci92LnR4dA=="
          ]
        }
      ]
    }
  ],


    "lives": [    {      "name": "live",      "type": 0,      "url": "./v.txt",      "playerType": 1,      "ua": "okhttp/3.12.13",      "epg": "http://epg.112114.xyz/?ch={name}&date={date}",      "logo": "https://epg.112114.xyz/logo/{name}.png"    }  ],
