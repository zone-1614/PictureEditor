package com.zone.pictureeditor.vm

import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.Coil
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import com.zone.pictureeditor.util.AppSharedPreferences
import com.zone.pictureeditor.util.PEApplication
import com.zone.pictureeditor.util.toast
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*

class PDFViewModel : ViewModel() {
    var imageUriList = mutableStateListOf<Uri>()

    fun convertToPDF() {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(1080, 2480, imageUriList.size).create()
        val savePath = AppSharedPreferences.getPDFPath()
        val calendar = Calendar.getInstance()
        val filename = "/PE_${calendar.get(Calendar.YEAR)}_${calendar.get(Calendar.MONTH) + 1}" +
                "_${calendar.get(Calendar.DAY_OF_MONTH)}_${calendar.get(Calendar.HOUR_OF_DAY)}" +
                "_${calendar.get(Calendar.MINUTE)}_${calendar.get(Calendar.SECOND)}.pdf"
        viewModelScope.launch {
            for (uri in imageUriList) {
                val page = document.startPage(pageInfo)
                val imageLoader = Coil.imageLoader(PEApplication.context)
                val request = ImageRequest.Builder(PEApplication.context)
                    .data(uri)
                    .allowHardware(false)
                    .scale(Scale.FIT)
                    .build()
                val result = (imageLoader.execute(request) as SuccessResult).drawable
                page.canvas.drawBitmap((result as BitmapDrawable).bitmap, 0.0f, 0.0f, null)
                document.finishPage(page)
            }
        }.invokeOnCompletion {
            val path = File(savePath)
            if (!path.exists()) {
                path.mkdirs()
            }
            document.writeTo(FileOutputStream(savePath + filename))
            document.close()
            imageUriList.clear()
            "转化成功".toast() // TODO: 可以改为用service通知, 同时实现点击跳转
        }
    }
}