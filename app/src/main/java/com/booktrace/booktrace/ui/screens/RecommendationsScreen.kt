package com.booktrace.booktrace.ui.screens

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.booktrace.booktrace.R
import com.booktrace.booktrace.ui.navigation.Screen
import com.booktrace.booktrace.viewModel

@Composable
fun RecommendationsScreen(navController: NavHostController,
                          viewModel: viewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    RecommendationsLayout(
        orientation = configuration.orientation,
        onNavigateBack = { viewModel.signOut()
            Toast.makeText(context, "Cierre de sesión exitoso", Toast.LENGTH_SHORT).show()
            navController.popBackStack(Screen.Home.route, inclusive = false)
            navController.navigate(Screen.Home.route)}
    )
}

@Composable
fun RecommendationsLayout(orientation: Int, onNavigateBack: () -> Unit) {
    Scaffold { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RecommendationsContent(onNavigateBack)
                    }
                }
                else -> {
                    RecommendationsContent(onNavigateBack)
                }
            }
        }
    }
}

@Composable
fun RecommendationsContent(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
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

        Button(onClick = onNavigateBack)
        {
            Text(
                stringResource(R.string.logOut)
            )
        }
    }
}