package com.booktrace.booktrace.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class) // Opt in to use experimental features if required
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home Screen") })
        }
    ) { paddingValues ->
        // Content goes here, respecting the padding values
        Surface(modifier = Modifier.padding(paddingValues)) {
            Button(
                onClick = {
                }
            ) {
                Text("Go to Details")
            }
        }
    }
}