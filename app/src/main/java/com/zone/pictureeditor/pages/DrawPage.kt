package com.zone.pictureeditor.pages

import android.Manifest
import android.view.MotionEvent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.zone.pictureeditor.R
import com.zone.pictureeditor.ui.theme.AppColor
import com.zone.pictureeditor.util.PermissionUtils
import com.zone.pictureeditor.util.Router
import com.zone.pictureeditor.util.toast
import com.zone.pictureeditor.vm.DrawViewModel

@Composable
fun Draw(navController: NavHostController, vm: DrawViewModel = viewModel()) {
    Scaffold(
        topBar = { DrawTopBar(navController, vm) },
        bottomBar = { PE_Draw_BottomBar(vm) }
    ) {
        PE_Canvas(vm)
    }
}

@Composable
fun DrawTopBar(
    navController: NavHostController,
    vm: DrawViewModel
) = TopAppBar(
    title = { Text(text = "Draw") },
    navigationIcon = {
        IconButton(onClick = { 
            navController.navigate(Router.MainPage)
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack")
        }
    },
    backgroundColor = Color.White,
    actions = {
        // 撤回按钮
        IconButton(onClick = {
            vm.cancel()
        }, enabled = vm.linesOnCanvas.isNotEmpty()) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
        // 反向撤回按钮
        IconButton(onClick = {
            vm.counterCancel()
        }, enabled = vm.cancelLines.isNotEmpty()) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
        }
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap ->
            val allGranted = permissionsMap.values.reduce { acc, next -> acc && next }
            if (!allGranted) {
                "拒绝权限请求, 保存为图片失败".toast()
            }
        }
        Button(
            modifier = Modifier.padding(start = 10.dp, end = 20.dp),
            onClick = {
            if (PermissionUtils.haveStoragePermission()) {
                vm.save()
            } else {
                launcher.launch(arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ))
            }
        }) {
            Text(text = "Save")
        }
    }
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PE_Canvas(vm: DrawViewModel) {
    val action: MutableState<MotionEvent?> = remember { mutableStateOf(null) }
    val path = Path()
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        action.value = it
                        path.moveTo(it.x, it.y)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        action.value = it
                        path.lineTo(it.x, it.y)
                    }
                    MotionEvent.ACTION_UP -> {
                        vm.linesOnCanvas.add(path)
                    }
                }
                true
            }
    ) {
        action.value?.let {
            drawPath(path = path, color = vm.penColor.value, alpha = 1f, style = Stroke(10f))
        }
//        vm.linesOnCanvas.forEach {
//            drawPath(path = it, color = vm.penColor.value)
//        }
    }
}

@Composable
fun PE_Draw_BottomBar(vm: DrawViewModel) = BottomAppBar(
    backgroundColor = Color.White
) {
    IconButton(
        modifier = Modifier.padding(start = 100.dp),
        onClick = {
            vm.choosePaint()
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_pen),
            contentDescription = "Pen",
            tint = if (vm.isPaint.value) { AppColor.MainColor } else { Color.Gray },
            modifier = Modifier.size(45.dp, 45.dp)
        )
    }
    Spacer(modifier = Modifier.weight(1f, true))
    IconButton(
        modifier = Modifier.padding(end = 100.dp),
        onClick = {
            vm.chooseEraser()
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_eraser),
            contentDescription = "Eraser",
            tint = if (!vm.isPaint.value) { AppColor.MainColor } else { Color.Gray },
            modifier = Modifier.size(30.dp, 30.dp)
        )
    }
}