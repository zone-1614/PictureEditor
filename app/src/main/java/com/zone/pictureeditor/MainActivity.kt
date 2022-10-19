package com.zone.pictureeditor

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zone.pictureeditor.ui.theme.PictureEditorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PictureEditorTheme {
                // A surface container using the 'background' color from the theme
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
                        AllFunctions()
                    }
                }
            }
        }
    }
}

// APP的标题
@Composable
fun AppTitle() {
    Text(
        text = "Picture",
        fontSize = 60.sp,
        fontStyle = Italic,
        fontWeight = FontWeight.W800,
        fontFamily = FontFamily.Serif,
        color = Color(22, 133, 169)
    )
    Text(
        text = "Editor",
        fontSize = 40.sp,
        fontStyle = Italic,
        fontFamily = FontFamily.Serif,
        color = Color(22, 133, 169)
    )
}

// 全部功能
@Composable
fun AllFunctions() {
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
            ConvertToPDFCard()
        }
        Column(Modifier.fillMaxWidth()) {
            Drawing()
            SettingsCard()
        }
    }
}


// 编辑图片
@SuppressLint("ResourceType")
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
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current){}
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_picture),
                contentDescription = "Edit",
                tint = Color(22, 133, 169),
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
fun ConvertToPDFCard() {
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
                    indication = LocalIndication.current){}
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pdf2),
                contentDescription = "Convert To PDF",
                tint = Color(22, 133, 169),
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
@SuppressLint("ResourceType")
@Composable
fun Drawing() {
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
                    indication = LocalIndication.current){}
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_drawing),
                contentDescription = "Draw",
                tint = Color(22, 133, 169),
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
fun SettingsCard() {
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
                    indication = LocalIndication.current){}
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                tint = Color(22, 133, 169),
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