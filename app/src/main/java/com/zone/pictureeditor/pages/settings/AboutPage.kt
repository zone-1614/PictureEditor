package com.zone.pictureeditor.pages.settings

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.zone.pictureeditor.R
import com.zone.pictureeditor.util.Router

@Composable
fun AboutPage(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(
            title = { Text("About") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(Router.SettingsPage)
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            backgroundColor = Color.White
        ) },
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val icon = ContextCompat.getDrawable(LocalContext.current, R.mipmap.gimp)!!
            val bitmap = Bitmap.createBitmap(icon.intrinsicWidth, icon.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            icon.setBounds(0, 0, canvas.width, canvas.height)
            icon.draw(canvas)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.scale(2.0f)
            )
            Text(
                text = "Picture Editor",
                fontSize = 30.sp,
                modifier = Modifier.padding(top = 50.dp)
            )
            Text(
                text = "Version 1.0.0",
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp)
            )
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
                Text(
                    text = "SCUT Android课程作业某小组 版权所有",
                    fontSize = 15.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
        }
    }
}