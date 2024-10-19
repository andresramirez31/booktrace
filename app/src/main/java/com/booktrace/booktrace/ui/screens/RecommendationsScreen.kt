package com.booktrace.booktrace.ui.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.booktrace.booktrace.R
import com.booktrace.booktrace.ui.navigation.Screen
import com.booktrace.booktrace.viewModel

@Composable
fun RecommendationsScreen(navController: NavHostController,
                          viewModel: viewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
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
fun RecommendationsContent(onNavigateBack: () -> Unit,
                           viewModel: viewModel = viewModel()
) {
    val books by viewModel.bookList.collectAsStateWithLifecycle()

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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 columns
            modifier = Modifier.padding(16.dp)
        ) {
            items(books) { book ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = book.coverImageUrl,
                            contentDescription = book.title,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = book.title,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Text(
                            text = book.author,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
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