package com.booktrace.booktrace.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.booktrace.booktrace.R
import com.booktrace.booktrace.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsScreen(navController: NavHostController) {

    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeRecommendationsLayout(navController)
    } else {
        PortraitRecommendationsLayout(navController)
    }

}

@Composable
fun PortraitRecommendationsLayout(navController: NavHostController){
    Scaffold(

    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(stringResource(R.string.recommendations_title), modifier = Modifier.padding(bottom = 16.dp), style = MaterialTheme.typography.headlineSmall)

                LazyColumn {
                    items(listOf("Book 1", "Book 2", "Book 3")) { book ->
                        Text(text = book, modifier = Modifier.padding(bottom = 8.dp))
                    }
                }

                Button(onClick = {
                    navController.navigate(Screen.Home.route)
                }) {
                    Text(
                        stringResource(R.string.home_return),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LandscapeRecommendationsLayout(navController: NavHostController){
    Scaffold(

    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        stringResource(R.string.recommendations_title),
                        modifier = Modifier.padding(bottom = 16.dp),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    LazyColumn {
                        items(listOf("Book 1", "Book 2", "Book 3")) { book ->
                            Text(text = book, modifier = Modifier.padding(bottom = 8.dp))
                        }
                    }

                    Button(onClick = {
                        navController.navigate(Screen.Home.route)
                    }) {
                        Text(
                            stringResource(R.string.home_return),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }
}