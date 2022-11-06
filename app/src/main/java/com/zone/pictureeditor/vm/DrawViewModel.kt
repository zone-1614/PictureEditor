package com.zone.pictureeditor.vm

import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.Coil
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import com.zone.pictureeditor.pages.draw.DrawMode
import com.zone.pictureeditor.pages.draw.model.PathProperties
import com.zone.pictureeditor.util.AppSharedPreferences
import com.zone.pictureeditor.util.PEApplication
import com.zone.pictureeditor.util.toast
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*

class DrawViewModel: ViewModel() {
    // --------------------------------- Top Bar 相关 ---------------------------------
    // 点击撤回后, 撤回刚刚画的东西
    fun cancel() {
        val remove = paths.removeLast()
        pathsUndone.add(remove)
    }
    // 反撤回
    fun counterCancel() {
        val remove = pathsUndone.removeLast()
        paths.add(remove)
    }
    // 点击保存
    fun save(bitmap: ImageBitmap) {
        val calendar = Calendar.getInstance()
        val filename = "/PE_${calendar.get(Calendar.YEAR)}_${calendar.get(Calendar.MONTH) + 1}" +
                "_${calendar.get(Calendar.DAY_OF_MONTH)}_${calendar.get(Calendar.HOUR_OF_DAY)}" +
                "_${calendar.get(Calendar.MINUTE)}_${calendar.get(Calendar.SECOND)}.png"
        val abitmap = bitmap.asAndroidBitmap()
        MediaStore.Images.Media.insertImage(PEApplication.context.contentResolver, abitmap, filename, "pe")
        // 保存完毕, 发送通知  TODO: 改成用 状态栏通知
        "保存成功".toast()
    }

    // --------------------------------- Canvas 相关 ---------------------------------
    // 画笔的状态, true 表示选择画画, false 表示橡皮擦
    var isPaint = mutableStateOf(true)
    // 画布上的线都保存在这里
     var paths = mutableStateListOf<Pair<Path, PathProperties>>()
    // 撤回的线保存在这里, 用于反撤回
     var pathsUndone = mutableStateListOf<Pair<Path, PathProperties>>()
    // 绘画模式
    var drawMode = mutableStateOf(DrawMode.Draw)
    // 画笔的属性
    var pathProperties = mutableStateOf(PathProperties())

    // 是否打开 pen dialog
    var openPenDialog = mutableStateOf(false)

    // --------------------------------- Bottom Bar 相关 ---------------------------------
    // 点击选择画笔
    fun choosePaint() {
        if (pathProperties.value.eraseMode) {
            pathProperties.value.eraseMode = false
            isPaint.value = true
        } else {
            openPenDialog.value = true
        }
    }
    // 点击选择橡皮擦
    fun chooseEraser() {
        if (!pathProperties.value.eraseMode) {
            pathProperties.value.eraseMode = true
            isPaint.value = false
        } else {
            openPenDialog.value = true
        }
    }
}