package com.zone.pictureeditor.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zone.pictureeditor.pages.*
import com.zone.pictureeditor.pages.draw.Draw
import com.zone.pictureeditor.pages.edit.Edit
import com.zone.pictureeditor.pages.pdf.PDFPage
import com.zone.pictureeditor.pages.settings.SettingsPage

object Router {
    const val MainPage = "MainPage"
//    const val EditPage = "EditPage"
    const val PDFPage = "PDFPage"
    const val SettingsPage = "SettingsPage"
    const val DrawPage = "DrawPage"
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Router.MainPage
    ) {
        composable(Router.MainPage) {
            MainPage(navController)
        }
//        composable(Router.EditPage) {
//            Edit(navController)
//        }
        composable(Router.PDFPage) {
            PDFPage(navController)
        }
        composable(Router.SettingsPage) {
            SettingsPage(navController)
        }
        composable(Router.DrawPage) {
            Draw(navController)
        }
    }
}
