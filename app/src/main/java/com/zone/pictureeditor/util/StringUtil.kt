package com.zone.pictureeditor.util

import android.widget.Toast

fun String.toast() {
    Toast.makeText(PEApplication.context, this, Toast.LENGTH_SHORT).show()
}