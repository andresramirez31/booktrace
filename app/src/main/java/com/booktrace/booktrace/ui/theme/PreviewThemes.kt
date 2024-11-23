package com.booktrace.booktrace.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.booktrace.booktrace.Inicio
import com.booktrace.booktrace.PreviewViewModel
import com.booktrace.booktrace.ui.screens.LoginScreen
import com.booktrace.booktrace.ui.screens.RecommendationsScreen
import com.booktrace.booktrace.ui.screens.SignUpScreen

@Preview(showBackground = true, name = "Light Theme")
@Composable
fun LightThemeHomePreviewPortrait(){
    BooktraceTheme(darkTheme = false) {
        Inicio()
    }
}

@Preview(showBackground = true, name = "Light Theme", widthDp = 640, heightDp = 360)
@Composable
fun LightThemeHomePreviewLandscape(){
    BooktraceTheme(darkTheme = false) {
        Inicio()
    }
}

@Preview(showBackground = true, name = "Light Theme")
@Composable
fun LightThemeLoginPreviewPortrait(){
    val navController = rememberNavController()
    BooktraceTheme(darkTheme = false) {
        LoginScreen(navController = navController)
    }
}

@Preview(showBackground = true, name = "Light Theme")
@Composable
fun LightThemeRecommendationsPreviewPortrait(){
    val navController = rememberNavController()
    BooktraceTheme(darkTheme = false) {
        RecommendationsScreen(navController = navController)
    }
}

@Preview(showBackground = true, name = "Light Theme", widthDp = 640, heightDp = 360)
@Composable
fun LightThemeRecommendationsPreviewLandscape(){
    val navController = rememberNavController()
    BooktraceTheme(darkTheme = false) {
        RecommendationsScreen(navController = navController)
    }
}

@Preview(showBackground = true, name = "Light Theme")
@Composable
fun LightThemeSignUpPreviewPortrait(){
    val navController = rememberNavController()
    BooktraceTheme(darkTheme = false) {
        SignUpScreen(navController = navController)
    }
}

@Preview(showBackground = true, name = "Light Theme", widthDp = 640, heightDp = 360)
@Composable
fun LightThemeSignUpPreviewLandscape(){
    val navController = rememberNavController()
    BooktraceTheme(darkTheme = false) {
        SignUpScreen(navController = navController)
    }
}