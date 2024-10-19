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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.booktrace.booktrace.R
import com.booktrace.booktrace.ui.navigation.Screen
import com.booktrace.booktrace.viewModel

@Composable
fun LoginScreen(navController: NavController,
                viewModel: viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val configuration = LocalConfiguration.current
    var loginError by remember { mutableStateOf(false) }

    LoginLayout(
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

@Composable
fun LoginLayout(orientation: Int,
                loginError: Boolean,
                onDone: (String, String) -> Unit = { email, password -> }
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

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
                            LoginInfoColumn(modifier = Modifier.weight(1f))
                            LoginInputColumn(
                                email,
                                password,
                                emailError,
                                passwordError,
                                loginError,
                                onEmailChanged = { email = it; emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() },
                                onPasswordChanged = { password = it; passwordError = it.length < 6 },
                                modifier = Modifier.weight(1f)
                            )
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
                            LoginInfoColumn()
                            LoginInputColumn(
                                email,
                                password,
                                emailError,
                                passwordError,
                                loginError,
                                onEmailChanged = { email = it; emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() },
                                onPasswordChanged = { password = it; passwordError = it.length < 6 }
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        if (!emailError && !passwordError) {
                            onDone(email, password)
                        }
                    },
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
fun LoginInfoColumn(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        CenterAlignedTopAppBar(title = {
            Text(
                stringResource(R.string.login_title),
                modifier = Modifier.padding(bottom = 4.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        })
        Text(
            stringResource(R.string.login_welcome),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}


@Composable
fun LoginInputColumn(
    email: String,
    password: String,
    emailError: Boolean,
    passwordError: Boolean,
    loginError: Boolean,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de Email
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = {
                Text(
                    stringResource(R.string.login_email_hint),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            isError = emailError,
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            shape = MaterialTheme.shapes.medium,
            singleLine = true
        )
        if (emailError) {
            Text(
                text = stringResource(R.string.login_error_invalid_email),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Campo de contrasena
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            label = {
                Text(
                    stringResource(R.string.login_password_hint),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError,
            textStyle = MaterialTheme.typography.bodySmall,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            singleLine = true
        )
        if (passwordError) {
            Text(
                text = stringResource(R.string.login_error_invalid_password),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        // Login Error Message
        if (loginError) {
            Text(
                text = stringResource(R.string.login_error_credentials),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}