package com.booktrace.booktrace.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.booktrace.booktrace.ui.navigation.Screen
import com.booktrace.booktrace.viewModel

@Composable
fun ProfileScreen(navController: NavController,
                viewModel: viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val configuration = LocalConfiguration.current
    var loginError by remember { mutableStateOf(false) }

    ProfileLayout(
        orientation = configuration.orientation,
        loginError = loginError,
        onDone = { email, password ->
            viewModel.signInWithEmailAndPassword(email,
                password,
                onLoginSuccess = { navController.navigate(Screen.Recommendations.route) },
                onLoginError = { loginError = true })
        }
    )
}

fun ProfileLayout(orientation: Int,
                  loginError: Boolean,
                  onDone: (String, String) -> Unit = { email, password -> }
){

}