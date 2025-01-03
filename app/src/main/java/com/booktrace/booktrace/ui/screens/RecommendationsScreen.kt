package com.booktrace.booktrace.ui.screens

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.booktrace.booktrace.R
import com.booktrace.booktrace.ui.navigation.Screen
import com.booktrace.booktrace.viewModel
import com.booktrace.booktrace.RecommendationViewModel

@Composable
fun RecommendationsScreen(
    navController: NavHostController,
    viewModel: viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val userId = viewModel.getCurrentUser()?.uid

    val id = userId ?: "1"
    val idInt = id.filter { it.isDigit() }.toInt()
    Log.d("Booktrace","id: $idInt")

    val recommendationViewModel: RecommendationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    LaunchedEffect(Unit) {
        recommendationViewModel.fetchRecommendations(idInt)
    }

    Scaffold(
        topBar = {
            RecommendationsTopBar(
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onRefreshClick = { navController.navigate(Screen.Recommendations.route) },
                onHelpClick = { Toast.makeText(context, R.string.help_message, Toast.LENGTH_LONG).show() },
                onLogOutClick = {
                    viewModel.signOut()
                    Toast.makeText(context, R.string.logout_message, Toast.LENGTH_SHORT).show()
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                    navController.navigate(Screen.Home.route)
                },
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RecommendationsContent(viewModel = recommendationViewModel, columns = 4)
                    }
                }
                else -> {
                    RecommendationsContent(viewModel = recommendationViewModel, columns = 2)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsTopBar(
    onProfileClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onHelpClick: () -> Unit,
    onLogOutClick: () -> Unit,
    viewModel: viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var displayName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchDisplayName { name ->
            displayName = name
        }
    }

    val userName = displayName ?: ""

    TopAppBar(
        title = {
            Column {
                Text(
                    text = stringResource(R.string.enjoy),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.profile),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = onRefreshClick) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.refresh)
                )
            }

            IconButton(onClick = onHelpClick) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(R.string.help)
                )
            }

            IconButton(onClick = onLogOutClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = stringResource(R.string.logOut)
                )
            }
        }
    )
}

@Composable
fun RecommendationsContent(viewModel: RecommendationViewModel,
                           columns: Int
) {
    val books = viewModel.recommendations.value

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
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

                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = book.authors,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}