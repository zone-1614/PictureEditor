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
import androidx.compose.ui.text.style.TextAlign
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
        elevation = 6.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = "Edit",
                tint = Color(22, 133, 169),
                modifier = Modifier.width(60.dp).height(60.dp)
            )
            Text(
                text = "Edit",
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.wrapContentSize().padding(top = 10.dp)
            )
        }
    }
}

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
            modifier = Modifier.padding(top = 30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Convert To PDF",
                tint = Color(22, 133, 169),
                modifier = Modifier.width(60.dp).height(60.dp)
            )
            Text(
                text = "Convert To\n PDF",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.wrapContentSize().padding(top = 10.dp)
            )
        }
    }
}

@Composable
fun Card3() {
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
            modifier = Modifier.padding(top = 30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Draw",
                tint = Color(22, 133, 169),
                modifier = Modifier.width(60.dp).height(60.dp)
            )
            Text(
                text = "Draw",
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.wrapContentSize().padding(top = 10.dp)
            )
        }
    }
}

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
            modifier = Modifier.padding(top = 30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color(22, 133, 169),
                modifier = Modifier.width(60.dp).height(60.dp)
            )
            Text(
                text = "Settings",
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.wrapContentSize().padding(top = 10.dp)
            )
        }
    }
}