package com.zone.pictureeditor.pages

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.zone.pictureeditor.util.Router

@Composable
fun Draw(navController: NavHostController) {
    Scaffold(
        topBar = { DrawTopBar(navController = navController) }
    ) {
        Text(text = "This is Draw Page")
    }
}

@Composable
fun DrawTopBar(navController: NavHostController) = TopAppBar(
    title = { Text(text = "Draw") },
    navigationIcon = {
        IconButton(onClick = { 
            navController.navigate(Router.MainPage)
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack")
        }
    },
    backgroundColor = Color.White
)