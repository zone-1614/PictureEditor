package com.zone.pictureeditor.pages.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.zone.pictureeditor.ui.theme.Background
import com.zone.pictureeditor.util.Router
import com.zone.pictureeditor.util.toast
import com.zone.pictureeditor.vm.SettingsViewModel

@Composable
fun SettingsPage(
    navController: NavHostController,
    vm: SettingsViewModel = viewModel()
) {
    Scaffold(
        topBar = { SettingsTopBar(navController = navController) },
        backgroundColor = Background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                elevation = 6.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, top = 40.dp, bottom = 20.dp)
            ) {
                Column {
                    SettingItem(text = "This is Settings Page")
                    Divider()
                    SettingItem(text = "改变PDF存储路径")
                    Divider()
                    SettingItem(text = "通知")
                }
            }
            Card(
                elevation = 6.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, top = 20.dp, bottom = 20.dp)
            ) {
                Column {
                    SettingItem(text = "隐私")
                    Divider()
                    SettingItem(text = "服务条款")
                    Divider()
                    SettingItem(text = "反馈")
                    Divider()
                    SettingItem(text = "帮助")
                }
            }
        }
    }
}

@Composable
fun SettingsTopBar(navController: NavHostController) = TopAppBar(
    title = { Text(text = "Settings") },
    navigationIcon = {
        IconButton(onClick = {
            navController.navigate(Router.MainPage)
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack")
        }
    },
    backgroundColor = Background
)

@Composable
fun SettingItem(
    text: String,
    onClick: () -> Unit = {"click".toast()}
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
        onClick()
    }) {
        Text(
            text = text,
            modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 15.dp, bottom = 15.dp)
        )
    }
}