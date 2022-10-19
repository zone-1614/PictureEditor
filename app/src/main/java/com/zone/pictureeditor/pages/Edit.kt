package com.zone.pictureeditor.pages

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.zone.pictureeditor.util.Router

@Composable
fun Edit(navController: NavHostController) {
    Scaffold(
        topBar = { EditTopBar(navController = navController) }
    ) {
        Text(text = "This is Edit Page")
    }
}

@Composable
fun EditTopBar(navController: NavHostController) = TopAppBar(
    title = { Text(text = "Edit") },
    navigationIcon = {
        IconButton(onClick = {
            navController.navigate(Router.MainPage)
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack")
        }
    },
    backgroundColor = Color.White
)