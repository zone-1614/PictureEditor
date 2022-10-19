package com.zone.pictureeditor.pages

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.zone.pictureeditor.ui.theme.AppColor
import com.zone.pictureeditor.util.Router
import com.zone.pictureeditor.util.toast

@Composable
fun PDFPage(navController: NavHostController) {
    Scaffold(
        topBar = { PDFTopBar(navController = navController) },
        floatingActionButton = { PDFFloatingActionButton() }
    ) {
        Text(text = "This is Convert To PDF Page")
    }
}

@Composable
fun PDFTopBar(navController: NavHostController) = TopAppBar(
    title = { Text(text = "Convert To PDF") },
    navigationIcon = {
        IconButton(onClick = {
            navController.navigate(Router.MainPage)
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack")
        }
    },
    backgroundColor = AppColor.MainColor
)

@Composable
fun PDFFloatingActionButton() = FloatingActionButton(
    onClick = {
        "click".toast()
    },
    backgroundColor = AppColor.MainColor
) {
    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
}