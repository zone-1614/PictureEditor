package com.zone.pictureeditor.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zone.pictureeditor.pages.*
import com.zone.pictureeditor.pages.draw.Draw
import com.zone.pictureeditor.pages.pdf.PDFPage
import com.zone.pictureeditor.pages.settings.*

object Router {
    const val MainPage = "MainPage"
    const val PDFPage = "PDFPage"
    const val SettingsPage = "SettingsPage"
    const val DrawPage = "DrawPage"
    const val PrivacyPage = "PrivacyPage"
    const val ServicePage = "ServicePage"
    const val FeedbackPage = "Feedback"
    const val HelpPage = "Help"
    const val AboutPage = "About"
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
        composable(Router.PDFPage) {
            PDFPage(navController)
        }
        composable(Router.SettingsPage) {
            SettingsPage(navController)
        }
        composable(Router.DrawPage) {
            Draw(navController)
        }
        composable(Router.PrivacyPage) {
            PrivacyPage(navController)
        }
        composable(Router.ServicePage) {
            ServicePage(navController)
        }
        composable(Router.FeedbackPage) {
            FeedbackPage(navController)
        }
        composable(Router.HelpPage) {
            HelpPage(navController)
        }
        composable(Router.AboutPage) {
            AboutPage(navController)
        }
    }
}
