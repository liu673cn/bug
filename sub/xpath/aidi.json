{
  "author":"takagen99",
  "ua": "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1",
  "homeUrl": "https://aidi.tv/",
  "cateManual": {
    "电影": "dianying",
    "国产剧": "guochanju",
    "港台剧": "gangtaiju",
    "欧美剧": "meiju",
    "日本剧": "riju",
    "韩国剧": "hanju",
    "综艺": "zongyi",
    "动漫": "dongman"
  },

// Home Summary ====================================================
  "homeVodNode": "//div[@class='cbox_list']/div/ul/li[contains(@class, 'vodlist_item')]",
  "homeVodName": "/a/@title",
  "homeVodId": "/a/@href",
  "homeVodIdR": "/movie/(\\w+).html",
  "homeVodImg": "/a/@data-original",
  "homeVodMark": "/span[2]/text()",

// Category Summary ================================================
  "cateUrl": "https://aidi.tv/show/{cateId}--------{catePg}---.html",
  "cateVodNode": "//ul[contains(@class,'vodlist vodlist_wi')]/li/a",
  "cateVodName": "/@title",
  "cateVodId": "/@href",
  "cateVodIdR": "/movie/(\\w+).html",
  "cateVodImg": "/@data-original",
  "cateVodMark": "/span[2]/text()",

// Category Details ================================================
  "dtUrl": "https://aidi.tv/movie/{vid}.html",
  "dtNode": "//div[@class='content_box clearfix']",				// must be unique
  "dtName": "/div/a/@title",
  "dtImg": "/div/a/img/@data-original",
  "dtImgR": "\\S+(http\\S+)",
  "dtCate": "//div[contains(@class,'content_detail content_min fl')]/ul/li/span[contains(text(), '状态')]/following-sibling::a/text()",
  "dtDirector": "//div[contains(@class,'content_detail content_min fl')]/ul/li/span[contains(text(), '导演')]/following-sibling::a/text()",
  "dtActor": "//div[contains(@class,'content_detail content_min fl')]/ul/li/span[contains(text(), '主演')]/following-sibling::a/text()",
  "dtDesc": "//div[contains(@class,'content_detail content_min fl')]/ul/li[5]",

// Playlist ========================================================
  "dtFromNode": "//a[contains(.,'云') or contains(.,'旋') or contains(.,'爱')]",
  "dtFromName": "/@alt",
  "dtUrlNode": "//div[contains(@id,'playlistbox')]",
  "dtUrlSubNode": "/ul/li/a",
  "dtUrlId": "/@href",
  "dtUrlName": "/text()",
  "dtUrlNameR": "",
  "playUrl": "{playUrl}",
  "playUa": "",

// Search Results ==================================================
  "searchUrl": "https://aidi.tv/vsearch/-------------.html?wd={wd}&submit=",
  "scVodNode": "//a[contains(@class,'myui-vodlist__thumb')]",
  "scVodName": "/@title",
  "scVodId": "/@href",
  "scVodIdR": "",
  "scVodImg": "/@data-original",
  "scVodMark": ""
}
