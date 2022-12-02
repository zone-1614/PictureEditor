package com.zone.pictureeditor.pages.draw

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.zone.pictureeditor.R
import com.zone.pictureeditor.pages.draw.gesture.MotionEvent
import com.zone.pictureeditor.pages.draw.gesture.dragMotionEvent
import com.zone.pictureeditor.pages.draw.model.PathProperties
import com.zone.pictureeditor.ui.theme.MainColor
import com.zone.pictureeditor.util.PermissionUtils
import com.zone.pictureeditor.util.Router
import com.zone.pictureeditor.util.toast
import com.zone.pictureeditor.vm.DrawViewModel
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.CaptureController
import dev.shreyaspatil.capturable.controller.rememberCaptureController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Draw(navController: NavHostController, vm: DrawViewModel = viewModel()) {
    val captureController = rememberCaptureController()
    Scaffold(
        topBar = { DrawTopBar(navController, vm, captureController) },
        bottomBar = { PE_Draw_BottomBar(vm) }
    ) {
        PE_Canvas(vm, captureController)
    }
}

@Composable
fun DrawTopBar(
    navController: NavHostController,
    vm: DrawViewModel,
    captureController: CaptureController
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
        }, enabled = !vm.paths.isEmpty()) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
        // 反向撤回按钮
        IconButton(onClick = {
            vm.counterCancel()
        }, enabled = !vm.pathsUndone.isEmpty()) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
        }
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap ->
            var allGranted = true
            permissionsMap.values.forEach {
                allGranted = it && allGranted
            }
            if (!allGranted) {
                "拒绝权限请求, 保存为图片失败".toast()
            }
        }
        Button(
            modifier = Modifier.padding(start = 10.dp, end = 20.dp),
            onClick = {
            if (PermissionUtils.haveStoragePermission()) {
                captureController.capture()
            } else {
                launcher.launch(arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ))
            }
        }) {
            Text(text = "Save")
        }
    }
)

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PE_Canvas(
    vm: DrawViewModel,
    captureController: CaptureController
) {
    // 画布相关
    val paths = remember { vm.paths }
    val pathsUndone = remember { vm.pathsUndone }
    var motionEvent by remember { mutableStateOf(MotionEvent.Idle) }
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
    var drawMode by remember { vm.drawMode }
    var currentPath by remember { mutableStateOf(Path()) }
    var currentPathProperty by remember { vm.pathProperties }

    // dialog 相关
    var openPenDialog by remember { vm.openPenDialog }


    Capturable(
        controller = captureController,
        onCaptured = { bitmap, error ->
            // This is captured bitmap of a content inside Capturable Composable.
            if (bitmap != null) {
                // Bitmap is captured successfully. Do something with it!
                vm.save(bitmap)
            }

            if (error != null) {
                // Error occurred. Handle it!
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {

            val drawModifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
                .dragMotionEvent(
                    onDragStart = { pointerInputChange ->
                        motionEvent = MotionEvent.Down
                        currentPosition = pointerInputChange.position
                        pointerInputChange.consumeDownChange()

                    },
                    onDrag = { pointerInputChange ->
                        motionEvent = MotionEvent.Move
                        currentPosition = pointerInputChange.position

                        if (drawMode == DrawMode.Touch) {
                            val change = pointerInputChange.positionChange()
                            paths.forEach { entry ->
                                val path: Path = entry.first
                                path.translate(change)
                            }
                            currentPath.translate(change)
                        }
                        pointerInputChange.consumePositionChange()
                    },
                    onDragEnd = { pointerInputChange ->
                        motionEvent = MotionEvent.Up
                        pointerInputChange.consumeDownChange()
                    }
                )

            Canvas(modifier = drawModifier) {

                when (motionEvent) {

                    MotionEvent.Down -> {
                        if (drawMode != DrawMode.Touch) {
                            currentPath.moveTo(currentPosition.x, currentPosition.y)
                        }
                        // 按下手指, 记录当前位置
                        previousPosition = currentPosition

                    }
                    MotionEvent.Move -> {

                        if (drawMode != DrawMode.Touch) {
                            // 两点之间画 bezier 曲线
                            currentPath.quadraticBezierTo(
                                previousPosition.x,
                                previousPosition.y,
                                (previousPosition.x + currentPosition.x) / 2,
                                (previousPosition.y + currentPosition.y) / 2
                            )
                        }

                        previousPosition = currentPosition
                    }

                    MotionEvent.Up -> {
                        if (drawMode != DrawMode.Touch) {
                            currentPath.lineTo(currentPosition.x, currentPosition.y)

                            // 松开手指, 保存 path
                            paths.add(Pair(currentPath, currentPathProperty))

                            // 保存完毕, currentPath 重新赋值
                            currentPath = Path()

                            // property 也重新赋值
                            currentPathProperty = PathProperties(
                                strokeWidth = currentPathProperty.strokeWidth,
                                color = currentPathProperty.color,
                                strokeCap = currentPathProperty.strokeCap,
                                strokeJoin = currentPathProperty.strokeJoin,
                                eraseMode = currentPathProperty.eraseMode
                            )
                        }

                        // 新画一条线, 就把 undo 的都删了
                        pathsUndone.clear()

                        // 防止新的 path 从左上角开始
                        currentPosition = Offset.Unspecified
                        previousPosition = currentPosition
                        motionEvent = MotionEvent.Idle
                    }
                    else -> Unit
                }

                with(drawContext.canvas.nativeCanvas) {

                    val checkPoint = saveLayer(null, null)

                    paths.forEach {

                        val path = it.first
                        val property = it.second

                        if (!property.eraseMode) {
                            drawPath(
                                color = property.color,
                                path = path,
                                style = Stroke(
                                    width = property.strokeWidth,
                                    cap = property.strokeCap,
                                    join = property.strokeJoin
                                )
                            )
                        } else {
                            drawPath(
                                color = Color.Transparent,
                                path = path,
                                style = Stroke(
                                    width = property.strokeWidth,
                                    cap = property.strokeCap,
                                    join = property.strokeJoin
                                ),
                                blendMode = BlendMode.Clear
                            )
                        }
                    }

                    if (motionEvent != MotionEvent.Idle) {

                        if (!currentPathProperty.eraseMode) {
                            drawPath(
                                color = currentPathProperty.color,
                                path = currentPath,
                                style = Stroke(
                                    width = currentPathProperty.strokeWidth,
                                    cap = currentPathProperty.strokeCap,
                                    join = currentPathProperty.strokeJoin
                                )
                            )
                        } else {

                            drawPath(
                                color = Color.Transparent,
                                path = currentPath,
                                style = Stroke(
                                    width = currentPathProperty.strokeWidth,
                                    cap = currentPathProperty.strokeCap,
                                    join = currentPathProperty.strokeJoin
                                ),
                                blendMode = BlendMode.Clear
                            )
                        }
                    }
                    restoreToCount(checkPoint)
                }
            }

            if (openPenDialog) {
                PenDialog(vm)
            }
        }
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
            tint = if (vm.isPaint.value) { MainColor } else { Color.Gray },
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
            tint = if (!vm.isPaint.value) { MainColor } else { Color.Gray },
            modifier = Modifier.size(30.dp, 30.dp)
        )
    }
}
