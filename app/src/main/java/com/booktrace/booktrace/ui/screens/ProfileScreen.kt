package com.booktrace.booktrace.ui.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.booktrace.booktrace.R
import com.booktrace.booktrace.ui.navigation.Screen
import com.booktrace.booktrace.viewModel
import com.google.firebase.auth.FirebaseUser

@Composable
fun ProfileScreen(navController: NavController,
                viewModel: viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val configuration = LocalConfiguration.current
    var loginError by remember { mutableStateOf(false) }
    var displayName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchDisplayName { name ->
            displayName = name
        }
    }

    ProfileLayout(
        navController = navController,
        orientation = configuration.orientation,
        loginError = loginError,
        displayName = displayName
    )
}

@Composable
fun ProfileLayout(
    navController: NavController,
    viewModel: viewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    orientation: Int,
    loginError: Boolean,
    displayName: String?
){
    val context = LocalContext.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


    Scaffold(
        topBar = {
            ProfileTopBar(
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onBooksClick = { navController.navigate(Screen.Recommendations.route) },
                onHelpClick = { Toast.makeText(context, R.string.help_message_profile, Toast.LENGTH_LONG).show() },
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ProfileInfoColumn(modifier = Modifier.weight(1f), displayName = displayName)
                        }
                    }
                    else -> {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .wrapContentSize()
                        ) {
                            ProfileInfoColumn(modifier = Modifier.weight(1f), displayName = displayName)

                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    onProfileClick: () -> Unit,
    onBooksClick: () -> Unit,
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

    val userName = displayName ?: "displayName"

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

            IconButton(onClick = onBooksClick) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.books)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInfoColumn(modifier: Modifier, displayName: String?){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        CenterAlignedTopAppBar(title = {
            Text(
                text = displayName.toString(),
                modifier = Modifier.padding(bottom = 4.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        })
        Text(
            stringResource(R.string.profile_welcome),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}