# Picture Editor
## 项目简介
Picture Editor(图片编辑器), 主页如下:   

![](https://raw.githubusercontent.com/zone-1614/pic/main/img/Snipaste_2022-10-19_12-25-14.png)   

包括四个功能, Edit(编辑图片), Draw(创建一个空的画板, 画图, 这个功能暂定, 可以改), Convert To PDF(转化为 PDF), Settings(设置)
## 参考教程及采用的技术
android项目只有两种选择: java 和 kotlin, 分别参考教程 《第一行代码》的第二版和第三版, ~~我建议用kotlin~~

## 各个模块功能介绍
### Edit
编辑图片, 最基础的有 裁剪, 旋转, 对称; 后续有时间可以做: 马赛克, 涂鸦, 标注; 还有和图像处理相关的锐化,模糊,描边

实现思路: 裁剪暂时不知道, 旋转对称滤镜等用矩阵操作便可

### Draw 
创建新的画布, 可以画画以及擦除
已完成(擦除功能有bug)

### Convert To PDF
将多张图片转化为 PDF
已完成

### Settings
1. 设置PDF文件保存路径
2. 一些常用的设置: 隐私, 语言, 画质, 关于, 服务条款, 反馈, 帮助, 通知
