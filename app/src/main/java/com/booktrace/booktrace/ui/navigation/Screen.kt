package com.booktrace.booktrace.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Recommendations : Screen("recommendations")
    object SignUp : Screen("signup")
    object Profile : Screen("profile")
}