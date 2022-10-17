package com.zone.pictureeditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
            Card3()
            SettingsCard()
        }
    }
}

@Composable
fun EditCard() {
    Card(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(10.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
//                Icon(imageVector = Icons.Default.Build, contentDescription = "Settings")
        Text(text = "Edit",
            modifier = Modifier.background(Color(194, 204, 208)).wrapContentSize())
    }
}

@Composable
fun ConvertToPDFCard() {
    Card(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
//                Icon(imageVector = Icons.Default.Info, contentDescription = "Convert To PDF")
        Text(text = "Convert To PDF",
            modifier = Modifier.background(Color(194, 204, 208)).wrapContentSize())
    }
}

@Composable
fun Card3() {
    Card(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(10.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
//                Icon(imageVector = Icons.Default.Check, contentDescription = "card3")
        Text(text = "card3(暂时不知道要叫什么)",
            modifier = Modifier.background(Color(194, 204, 208)).wrapContentSize())
    }
}

@Composable
fun SettingsCard() {
    Card(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
//                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
        Text(
            text = "Settings",
            modifier = Modifier.background(Color(194, 204, 208)).wrapContentSize())
    }
}