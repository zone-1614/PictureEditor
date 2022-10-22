package com.zone.pictureeditor.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object AppSharedPreferences {
    var data: SharedPreferences = PEApplication.context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    val default_path = PEApplication.context.getExternalFilesDir(null)!!.absolutePath + "/PictureEditor"

    fun savePDFPath(path: String) {
        data.edit { putString("pdfpath", path) }
    }

    fun getPDFPath(): String {
        val str = data.getString("pdfpath", "")
        return if (str.isNullOrBlank()) {
            savePDFPath(default_path) // 默认存储路径
            default_path
        } else {
            str
        }
    }
}