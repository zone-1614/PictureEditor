package com.zone.pictureeditor.pages.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zone.pictureeditor.util.Router

@Composable
fun FeedbackPage(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(
            title = { Text("Feedback") },
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Text("你可以通过以下的邮箱联系我们:", fontSize = 20.sp)
            Text("xxxxxxxxxxxxx@gmail.com", fontSize = 20.sp)
        }
    }
}