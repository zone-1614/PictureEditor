package com.zone.pictureeditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.zone.pictureeditor.ui.theme.PictureEditorTheme
import com.zone.pictureeditor.util.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewCompat.getWindowInsetsController(LocalView.current)?.isAppearanceLightStatusBars = true
            PictureEditorTheme {
                // A surface container using the 'background' color from the theme
                Navigation()
            }
        }
    }
}
