package com.booktrace.booktrace.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.booktrace.booktrace.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.recommendations_title)) })
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {

            LazyColumn {
                items(listOf("Book 1", "Book 2", "Book 3")) { book ->
                    Text(text = book)
                }
            }

            Button(onClick = {
                navController.navigate("home")
            }) { Text(stringResource(R.string.home_return)) }

        }
    }
}