# youqu_master
友趣app，一款可以交朋友并且十分有趣的应用。Rxjava+Retrofit+Glide+ijkplayer结合的MVP框架（精选图书，妹纸福利图片，火爆视频，各种炫酷特效）。

app功能gif展示:
-------
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/app_gif.gif)

app主要截图:
-------

![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/one.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/two.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/three.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/four.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/five.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/six.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/seven.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/eight.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/nine.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/ten.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/11.jpg)
![image](https://github.com/wangfeng19930909/youqu_master/blob/master/screenshot/12.jpg)

*****功能模块：
-------

图书：采用豆瓣图书api,可增加频道订阅进行分类管理
妹子：美女壁纸，来自Gank
视频：包括热点，搞笑，娱乐，精品四个分类，来自网易视频
我的：增加一点背景view动画特效和下雪效果，提供分享，日夜模式切换功能

*****项目亮点：
-------

启动页动画效果，引导页的指示界面美观
封装改造启动页动画效果库
采用material-intro-screen美化管理引导页，集成权限授予
通过config.grade进行各引用库版本管理
mvp模式：简单的mvp一目了然,解耦model和view层，契约类管理mvp，一目了然，实现纵向解耦，基类完美封装，避免频繁new对象
RxJava:包括Rx处理服务器请求、缓存、线程调度的完美封装
复杂列表处理，使用type进行区分，在BaseREcycleviewAdapter中体现
组件化开发，横向解耦
封装各种工具类，比如压缩图片、轮播器、查看大图、缓存工具、图片选择器，以common的module形式依赖
自定义分享布局，更接近需求定制
各种封装好的依赖库，比如Irecyclerview：包含万能适配器、recyclerview的下拉刷新上拉加载更多、自定义刷新头和加载更多头；selectordialog：经常使用到的几种Dialog；oneKeyShareSDK：社交分享；微信和支付宝封装等等
无关业务内容封装成model，基于此框架可以快速开发一个app

*****用到的开源库有：
-------

org.jsoup:jsoup:1.10.2'//网页解析器

com.flaviofaria:kenburnsview:1.0.7'//浮动view

com.github.florent37:diagonallayout:1.0.2'

com.balysv.materialmenu:material-menu:2.0.0'

org.jetbrains:annotations:15.0'

me.shaohui:bottomdialog:1.1.9'

om.flyco.tablayout:FlycoTabLayout_Lib:2.0.8@aar

com.github.clans:fab:1.6.4

com.squareup.retrofit2:retrofit:2.0.0-beta3

io.reactivex:rxjava:1.0.1

io.reactivex:rxandroid:1.0.1

com.github.bumptech.glide:glide:3.6.1

fm.jiecao:jiecaovideoplayer:4.7.0

com.squareup.picasso:picasso:2.5.2

com.kyleduo.switchbutton:library:2.0.0

com.github.jetradarmobile:android-snowfall:1.2.0
...


本人会坚持在这个项目上实践最新的技术，也会争取拓展更多的阅读内容，欢迎各位关注！ 注意：本项目还在测试阶段，发现 bug 或有好的建议欢迎issue、email(wangfengkxhp@foxmail.com),如果感觉对你有帮助也欢迎点个 star、fork，本项目仅做学习交流使用，请勿用于其他用途,如若发现资源存在侵权，请第一时间联系删除。

MIT License
=================================== 
Copyright 2017-2018, wangfeng19930909

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
