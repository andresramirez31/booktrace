package com.booktrace.booktrace.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.booktrace.booktrace.R
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
        orientation = configuration.orientation,
        loginError = loginError,
        displayName = displayName

    )
}

@Composable
fun ProfileLayout(
    orientation: Int,
    loginError: Boolean,
    displayName: String?
){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }




    Scaffold { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        // Disposición para landscape
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
                        // Disposición para portrait
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

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    enabled = email.isNotEmpty() && password.isNotEmpty()
                ) {
                    Text(stringResource(R.string.login_button))
                }
            }
        }
    }
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
            "bienvenido/a",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}