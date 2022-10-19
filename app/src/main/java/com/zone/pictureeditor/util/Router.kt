package com.zone.pictureeditor.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zone.pictureeditor.pages.PDFPage
import com.zone.pictureeditor.pages.SettingsPage
import com.zone.pictureeditor.pages.Edit
import com.zone.pictureeditor.pages.MainPage

object Router {
    const val MainPage = "MainPage"
    const val EditPage = "EditPage"
    const val PDFPage = "PDFPage"
    const val SettingsPage = "SettingsPage"
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Router.MainPage) {
        composable(Router.MainPage) {
            MainPage(navController)
        }
        composable(Router.EditPage) {
            Edit()
        }
        composable(Router.PDFPage) {
            PDFPage()
        }
        composable(Router.SettingsPage) {
            SettingsPage()
        }
    }
}