package com.booktrace.booktrace.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Details : Screen("details")
}