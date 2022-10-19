package com.zone.pictureeditor.pages

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.zone.pictureeditor.ui.theme.AppColor
import com.zone.pictureeditor.util.PEApplication
import com.zone.pictureeditor.util.Router
import com.zone.pictureeditor.util.toast
import com.zone.pictureeditor.vm.PDFViewModel

@Composable
fun PDFPage(
    navController: NavHostController,
    vm: PDFViewModel = viewModel()
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()) { uriList ->
        // 获得图片后添加到 viewModel 中
        uriList.forEach { vm.imageUriList.add(it) }
        Log.d("PE", uriList.toString())
        "选择成功".toast()
    }
    Scaffold(
        topBar = { PDFTopBar(navController = navController) },
        backgroundColor = AppColor.Background ,
        floatingActionButton = { PDFFloatingActionButton(onClick = {
            galleryLauncher.launch("image/*")
        }) }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(vm.imageUriList.size) { idx ->
                val source = ImageDecoder.createSource(PEApplication.context.contentResolver, vm.imageUriList[idx])
                val bitmap = remember { mutableStateOf<Bitmap?>(null) }
                bitmap.value = ImageDecoder.decodeBitmap(source)
                bitmap.value?.let { btm ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, top = 20.dp)
                        .height(200.dp)
                    ) {
                        Image(bitmap = btm.asImageBitmap(), contentDescription = "bitmap")
                    }
                }
            }
            if (vm.imageUriList.isNotEmpty()) {
                item {
                    Button(
                        onClick = {
                            // TODO: Convert To PDF
                            "convert".toast()
                        },
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                    ) {
                        Text(text = "Convert")
                    }
                }
            }
        }
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
    backgroundColor = AppColor.Background
)

@Composable
fun PDFFloatingActionButton(onClick: () -> Unit) = FloatingActionButton(
    onClick = {
        onClick()
    },
    backgroundColor = AppColor.MainColor
) {
    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
}