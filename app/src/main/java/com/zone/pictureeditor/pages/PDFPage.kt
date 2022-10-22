package com.zone.pictureeditor.pages

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.zone.pictureeditor.ui.theme.AppColor
import com.zone.pictureeditor.util.PermissionUtils
import com.zone.pictureeditor.util.Router
import com.zone.pictureeditor.util.toast
import com.zone.pictureeditor.vm.PDFViewModel

@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun PDFPage(
    navController: NavHostController,
    vm: PDFViewModel = viewModel()
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()) { uriList ->
        // 获得图片后添加到 viewModel 中
        uriList.forEach { vm.imageUriList.add(it) }
    }
    Scaffold(
        topBar = { PDFTopBar(navController, vm) },
        backgroundColor = AppColor.Background ,
        floatingActionButton = { PDFFloatingActionButton(onClick = {
            galleryLauncher.launch("image/*")
        }) }
    ) {
        LazyVerticalGrid(cells = GridCells.Fixed(1)) {
            items(vm.imageUriList.size) { idx ->
                val showDeleteDialog = remember { mutableStateOf(false) }
                Image(
                    painter = rememberImagePainter(data = vm.imageUriList[idx]),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                        .size(200.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    // 长按弹出删除的dialog
                                    showDeleteDialog.value = true
//                                    vm.imageUriList.removeAt(idx)
                                },
                                onTap = {
                                    // 点击放大
                                    "onTap".toast()
                                }
                            )
                        }
                )
                if (showDeleteDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog.value = false },
                        title = { Text(text = "是否删除图片") },
                        confirmButton = {
                            Button(onClick = {
                                showDeleteDialog.value = false
                                vm.imageUriList.removeAt(idx)
                            }) {
                                Text(text = "确定")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDeleteDialog.value = false }) {
                                Text(text = "取消")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PDFTopBar(navController: NavHostController, vm: PDFViewModel) = TopAppBar(
    title = { Text(text = "Convert To PDF") },
    navigationIcon = {
        IconButton(onClick = {
            navController.navigate(Router.MainPage)
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack")
        }
    },
    backgroundColor = AppColor.Background,
    actions = {
        if (vm.imageUriList.isNotEmpty()) {
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissionsMap ->
                val allGranted = permissionsMap.values.reduce { acc, next -> acc && next }
                if (allGranted) {
//                    vm.convertToPDF()
                } else {
                    "拒绝权限请求, 无法转化为PDF".toast()
                }
            }
            Button(onClick = {
                if (PermissionUtils.haveStoragePermission()) {
                    vm.convertToPDF()
                } else {
                    launcher.launch(arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ))
                }
            }) {
                Text(text = "Convert")
            }
        }
    }
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