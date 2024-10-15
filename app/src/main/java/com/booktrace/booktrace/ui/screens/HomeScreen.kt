package com.booktrace.booktrace.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.booktrace.booktrace.R

@OptIn(ExperimentalMaterial3Api::class) // Opt in to use experimental features if required
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.app_name)) })
        }
    ) { paddingValues ->
        // Content goes here, respecting the padding values
        Surface(modifier = Modifier.padding(paddingValues)) {
            Button(
                onClick = {
                    navController.navigate("login")
                }
            ) {
                Text(stringResource(R.string.login_button))
            }

            Button(
                onClick = {
                    navController.navigate("signup")
                }
            ) {
                Text(stringResource(R.string.signup_button))
            }
        }
    }
}