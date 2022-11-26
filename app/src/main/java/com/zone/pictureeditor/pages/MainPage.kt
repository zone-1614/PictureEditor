package com.zone.pictureeditor.pages

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zone.pictureeditor.R
import com.zone.pictureeditor.pages.edit.EditActivity
import com.zone.pictureeditor.ui.theme.Background
import com.zone.pictureeditor.ui.theme.MainColor
import com.zone.pictureeditor.util.PermissionUtils
import com.zone.pictureeditor.util.Router
import com.zone.pictureeditor.util.toast

// 主页
@Composable
fun MainPage(navController: NavHostController) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .background(Background)
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
        color = MainColor
    )
    Text(
        text = "Editor",
        fontSize = 40.sp,
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Serif,
        color = MainColor
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
            EditCard()
            ConvertToPDFCard(navController)
        }
        Column(Modifier.fillMaxWidth()) {
            Drawing(navController)
            SettingsCard(navController)
        }
    }
}


// 编辑图片
@Composable
fun EditCard() {
    Card(
        elevation = 6.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(10.dp)
    ) {
        val currentContext = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap ->
            val allGranted = permissionsMap.values.reduce { acc, next -> acc && next }
            if (!allGranted) {
                "拒绝权限请求, 无法转化为PDF".toast()
            }
        }
        val galleryLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                // 因为 compose 无法实现图片编辑的功能, 使用 activity
                val intent = Intent(currentContext, EditActivity::class.java)
                intent.putExtra("curPic", "$it")
                currentContext.startActivity(intent)
            }
        }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current) {
                    // 跳转之前先到系统相册选择图片, 所以要获取权限
                    if (PermissionUtils.haveStoragePermission()) {
                        // 有权限, 进入系统相册选择图片, 获取其uri, 并跳转到 activity
                        galleryLauncher.launch("image/*")
                    } else { // 没有权限, 尝试获取权限
                        launcher.launch(arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ))
                    }

                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_picture),
                contentDescription = "Edit",
                tint = MainColor,
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
                tint = MainColor,
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
                    navController.navigate(Router.DrawPage)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_drawing),
                contentDescription = "Draw",
                tint = MainColor,
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
                tint = MainColor,
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