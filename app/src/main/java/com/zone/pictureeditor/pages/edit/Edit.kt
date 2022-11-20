package com.zone.pictureeditor.pages.edit

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.zone.pictureeditor.ui.theme.Background
import com.zone.pictureeditor.util.Router
import com.zone.pictureeditor.vm.EditViewModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Edit(
    navController: NavHostController,
    vm: EditViewModel = viewModel()
) {
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        vm.imageUri.value = it
    }
    Scaffold(
        topBar = { EditTopBar(navController = navController, vm = vm, clickOpenPicture = {
            galleryLauncher.launch("image/*")
        }, clickSavePicture = {
//            vm.imageUri.value?.let {
//                vm.save()
//            }
        }) },
        bottomBar = { EditBottomBar() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Background).padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (vm.imageUri.value != null) {
                Image(
                    painter = rememberImagePainter(data = vm.imageUri.value),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun EditTopBar(
    navController: NavHostController,
    vm: EditViewModel,
    clickOpenPicture: () -> Unit,
    clickSavePicture: () -> Unit
) = TopAppBar(
    title = { Text(text = "Edit") },
    navigationIcon = {
        IconButton(onClick = {
            navController.navigate(Router.MainPage)
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack")
        }
    },
    backgroundColor = Color.White,
    actions = {
        Button(
            onClick = { clickSavePicture() },
            modifier = Modifier.padding(end = 20.dp),
            enabled = vm.imageUri.value != null
        ) {
            Text(text = "Save")
        }
        Button(
            onClick = { clickOpenPicture() },
            modifier = Modifier.padding(end = 20.dp)
        ) {
            Text(text = "Open")
        }
    }
)

@Composable
fun EditBottomBar() = BottomAppBar(
    backgroundColor = Color.White,
    modifier = Modifier.wrapContentHeight()
) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
        EditButton()
    }
}

@Composable
fun EditButton() = IconButton(
    onClick = { },
    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
) {
    Column {
        Icon(imageVector = Icons.Default.TextFields, contentDescription = "")
        Text("呵呵", fontSize = 10.sp)
    }
}