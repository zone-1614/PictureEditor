package com.zone.pictureeditor.vm

import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import com.zone.pictureeditor.util.PEApplication
import com.zone.pictureeditor.util.toast
import java.util.*

class EditViewModel : ViewModel() {

    var imageUri: MutableState<Uri?> = mutableStateOf(null)

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
}