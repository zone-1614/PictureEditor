package com.zone.pictureeditor.pages.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zone.pictureeditor.util.Router

@Composable
fun HelpPage(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(
            title = { Text("Help") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(Router.SettingsPage)
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            backgroundColor = Color.White
        ) },
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState())) {
            Text("这款App名为: Picture Editor, 也就是图片编辑器.\n" +
                    "我们提供了四个基本的功能: Edit(图片编辑), Draw(画图), Convert To PDF(把图片转化为PDF), Settings(设置)\n" +
                    "1. 图片编辑 \n" +
                    "在图片编辑这一模块中, 首先你需要从系统相册中选择你要编辑的图片, 选择完毕后, 可以对其进行裁剪, 旋转, 滤镜, 增强等功能, 还可以吧处理完毕的图片进行保存.\n" +
                    "2. 画图\n" +
                    "在画图这一模块中, 你可以自己创作图片, 也可以把它当成一个草稿纸, 在画错之后, 你还可以从右上角的按钮选择撤回或者反撤回. \n" +
                    "界面的底部可以选择画笔或者橡皮擦的功能, 再次点击选中的功能之后, 可以触发菜单. 在菜单中, 可以调整画笔的颜色, 粗细, 还可以一键清空画布. \n" +
                    "最后还可以用右上角的Save按钮将图片保存到系统相册中. " +
                    "3. 转化为PDF\n" +
                    "手机自带的转化为PDF功能大多数只能将一张图片转为PDF, 而在这里你可以选择任意多的图片. 转化成功后, 将会储存在默认目录下(内部存储/Android/data/com.zone.pictureeditor/files/PictureEditor). \n" +
                    "4. 设置\n" +
                    "在这一模块中, 你可以修改一些默认设置, 比如PDF存储路径, 通知设置, 查看有用的信息(比如隐私, 服务条款等等).还可以通过'反馈'所提供的联系方式联系到我们. ")
        }
    }

}