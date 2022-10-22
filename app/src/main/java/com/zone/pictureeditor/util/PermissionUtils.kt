package com.zone.pictureeditor.util

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionUtils {
    // 是否有操作手机存储的权限
    fun haveStoragePermission(): Boolean =
        havePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        && havePermission(Manifest.permission.READ_EXTERNAL_STORAGE)

    private fun havePermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(PEApplication.context, permission) == PackageManager.PERMISSION_GRANTED

}