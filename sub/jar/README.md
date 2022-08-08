## jar包缝合教程：暴力缝合，非代码修改!-----他人整理

由于某个软件开源的原因，各路大神真的是各种玩法层出不穷，今天给大家分享的是某大神分享的jar包缝合教程，具体缝合不只是这一种方法，但是这种方式也是可行的，比较简单，有兴趣的朋友可以自己试试！

准备工作：MT管理器或者NP管理器，将jar2---缝合到--->jar1的过程，图中说的是 B包要用的包，A包要导出的包。

开始：jar2中merge重命名，展开重命名的merge，选中需要导出的文件，导出。打开jar1，导入刚才导出的重命名的merge文件，保存退出。jar1就是缝合好的包。

多试几次，关键是找个好用的MT或者NP。不是你不会，是因为你的MT或着NP没那个功能。

1-4 | 2-5 | 3-6
---------|---------|---------
![jar](https://liu673cn.github.io/box/sub/img/jar01.png)1、打开需要导出的包。 | ![jar](https://liu673cn.github.io/box/sub/img/jar02.png)2、给他重命名。 | ![jar](https://liu673cn.github.io/box/sub/img/jar03.png)3、选中，并导出你需要的文件。
![jar](https://liu673cn.github.io/box/sub/img/jar04.png)4、随便取个名字，自己能找到就行，理解就好。 | ![jar](https://liu673cn.github.io/box/sub/img/jar05.png) 5、打开你最终要用的包，导入刚才那个包导出的东西。| ![jar](https://liu673cn.github.io/box/sub/img/jar06.png)6、导入完成后的样子。

另外补充一点，有些api可能不只是需要merge，那种情况就要尝试给parser也改名一起导出，或者api本身还会调用其他api的要一起导出，比如阿里系列api，不是单纯导出一个小纸条就能直接用的，要带着ali，教程大概就是这样，喜欢折腾的朋友就去研究一下，不喜欢折腾的直接用多jar也是可以的。

## 他人教学，喜欢折腾的折腾吧。
