package com.zone.pictureeditor.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zone.pictureeditor.R
import com.zone.pictureeditor.ui.theme.AppColor
import com.zone.pictureeditor.util.Router

// 主页
@Composable
fun MainPage(navController: NavHostController) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .background(Color(233, 241, 246))
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTitle()
            AllFunctions(navController)
        }
    }
}

// APP的标题
@Composable
fun AppTitle() {
    Text(
        text = "Picture",
        fontSize = 60.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.W800,
        fontFamily = FontFamily.Serif,
        color = AppColor.MainColor
    )
    Text(
        text = "Editor",
        fontSize = 40.sp,
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Serif,
        color = AppColor.MainColor
    )
}

// 全部功能
@Composable
fun AllFunctions(navController: NavHostController) {
    Row(
        Modifier
            .padding(top = 100.dp, bottom = 50.dp, start = 35.dp, end = 35.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.fillMaxWidth(0.5f)) {
            EditCard(navController)
            ConvertToPDFCard(navController)
        }
        Column(Modifier.fillMaxWidth()) {
            Drawing(navController)
            SettingsCard(navController)
        }
    }
}


// 编辑图片
@SuppressLint("ResourceType")
@Composable
fun EditCard(navController: NavHostController) {
    Card(
        elevation = 6.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current) {
                    // Edit 点击事件
                    navController.navigate(Router.EditPage)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_picture),
                contentDescription = "Edit",
                tint = AppColor.MainColor,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .width(60.dp)
                    .height(60.dp)
            )
            Text(
                text = "Edit",
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 10.dp)
            )
        }
    }
}

// 转化为PDF
@SuppressLint("ResourceType")
@Composable
fun ConvertToPDFCard(navController: NavHostController) {
    Card(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current) {
                    // click pdf
                    navController.navigate(Router.PDFPage)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pdf2),
                contentDescription = "Convert To PDF",
                tint = AppColor.MainColor,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .width(60.dp)
                    .height(60.dp)
            )
            Text(
                text = "Convert To\n PDF",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 10.dp)
            )
        }
    }
}

// 画图
@Composable
fun Drawing(navController: NavHostController) {
    Card(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(10.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current) {
                    // TODO: click draw
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_drawing),
                contentDescription = "Draw",
                tint = AppColor.MainColor,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .width(60.dp)
                    .height(60.dp)
            )
            Text(
                text = "Draw",
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 10.dp)
            )
        }
    }
}

// 设置
@Composable
fun SettingsCard(navController: NavHostController) {
    Card(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current) {
                    // click settings
                    navController.navigate(Router.SettingsPage)
                }
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                tint = AppColor.MainColor,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .width(60.dp)
                    .height(60.dp)
            )
            Text(
                text = "Settings",
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 10.dp)
            )
        }
    }
}