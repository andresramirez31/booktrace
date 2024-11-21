package com.booktrace.booktrace.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.booktrace.booktrace.ui.screens.HomeScreen
import com.booktrace.booktrace.ui.screens.LoginScreen
import com.booktrace.booktrace.ui.screens.ProfileScreen
import com.booktrace.booktrace.ui.screens.RecommendationsScreen
import com.booktrace.booktrace.ui.screens.SignUpScreen
import com.booktrace.booktrace.ui.theme.BooktraceTheme
import com.booktrace.booktrace.ui.theme.GradientBackground
import com.booktrace.booktrace.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SetupNavGraph(navController: NavHostController,
                  viewModel:viewModel = viewModel()
) {
    val currentUser = Firebase.auth.currentUser

    NavHost(navController = navController,
        startDestination = if (currentUser != null) Screen.Recommendations.route else Screen.Home.route
    ) {
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
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }

    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.popBackStack(Screen.Home.route, inclusive = false)
            navController.navigate(Screen.Home.route)
        }
    }
}