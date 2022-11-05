package com.zone.pictureeditor.vm

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import com.zone.pictureeditor.pages.draw.DrawMode
import com.zone.pictureeditor.pages.draw.model.PathProperties

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
    fun save() {
        // TODO: 保存图片
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
    // 是否打开 eraser dialog
    var openEraserDialog = mutableStateOf(false)

    // --------------------------------- Bottom Bar 相关 ---------------------------------
    // 点击选择画笔
    fun choosePaint() {
        if (pathProperties.value.eraseMode) {
            pathProperties.value.eraseMode = false
            isPaint.value = true
        } else {
            // TODO: 打开选择颜色的dialog
            openPenDialog.value = true
        }
    }
    // 点击选择橡皮擦
    fun chooseEraser() {
        if (!pathProperties.value.eraseMode) {
            pathProperties.value.eraseMode = true
            isPaint.value = false
        } else {
            // TODO: 打开清除的dialog
            openEraserDialog.value = true
        }
    }
}