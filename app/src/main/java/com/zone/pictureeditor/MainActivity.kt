package com.zone.pictureeditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zone.pictureeditor.ui.theme.PictureEditorTheme
import com.zone.pictureeditor.util.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PictureEditorTheme {
                // A surface container using the 'background' color from the theme
                Navigation()
            }
        }
    }
}
