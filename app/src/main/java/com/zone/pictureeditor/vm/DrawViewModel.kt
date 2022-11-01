package com.zone.pictureeditor.vm

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel

class DrawViewModel: ViewModel() {
    // --------------------------------- Top Bar 相关 ---------------------------------
    // 点击撤回后, 撤回刚刚画的东西
    fun cancel() {
        cancelLines.add(linesOnCanvas.last())
        linesOnCanvas.removeAt(linesOnCanvas.lastIndex)
    }
    // 反撤回
    fun counterCancel() {
        linesOnCanvas.add(cancelLines.last())
        cancelLines.removeAt(cancelLines.lastIndex)
    }
    // 点击保存
    fun save() {
        linesOnCanvas.forEach {
            Log.d("PictureEditor", it.getBounds().toString())
        }
    }

    // --------------------------------- Canvas 相关 ---------------------------------
    // 画笔的状态, true 表示选择画画, false 表示橡皮擦
    var isPaint = mutableStateOf(true)
    // 画笔的颜色
    var penColor = mutableStateOf(Color.Black)
    // TODO: 画完的线都保存在这里
     var linesOnCanvas = mutableStateListOf<Path>()
    // TODO: 撤回的线保存在这里, 用于反撤回
     var cancelLines = mutableStateListOf<Path>()

    // --------------------------------- Bottom Bar 相关 ---------------------------------
    // 点击选择画笔
    fun choosePaint() {
        if (isPaint.value) {
            // TODO: 打开选择颜色的dialog
        } else {
            isPaint.value = true
        }
    }
    // 点击选择橡皮擦
    fun chooseEraser() {
        if (isPaint.value) {
            isPaint.value = false
        } else {
            // TODO: 打开清除的dialog
        }
    }
}