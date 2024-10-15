package com.booktrace.booktrace.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.booktrace.booktrace.ui.screens.HomeScreen
import com.booktrace.booktrace.ui.screens.LoginScreen
import com.booktrace.booktrace.ui.screens.RecommendationsScreen
import com.booktrace.booktrace.ui.screens.SignUpScreen
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Recommendations.route) {
            RecommendationsScreen(navController = navController)
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}