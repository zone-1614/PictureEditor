package com.zone.pictureeditor.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppSharedPreferences {
    var data: SharedPreferences = PEApplication.context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    val default_PDF_path = ""

    fun savePDFPath(path: String) {
        data.edit { putString("pdfpath", path) }
    }

    fun getPDFPath(): String {
        val str = data.getString("pdfpath", "")
        return if (str.isNullOrBlank()) {
            default_PDF_path
        } else {
            str
        }
    }
}