package com.booktrace.booktrace.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.booktrace.booktrace.R
import com.booktrace.booktrace.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {

    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeLoginLayout(navController)
    } else {
        PortraitLoginLayout(navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortraitLoginLayout(navController: NavHostController){
    // State para email y contrasena
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State para mensajes de error
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf(false) }

    Scaffold(

    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.Center) // Center the entire Column
                        .wrapContentSize(),
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
                        modifier = Modifier.padding(bottom = 12.dp),
                    )
                    // Campo de Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        },
                        label = {
                            Text(
                                stringResource(R.string.login_email_hint),
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        isError = emailError,
                        textStyle = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth(),
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de contrasena
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = it.length < 6 // Password must be at least 6 characters
                        },
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
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    if (passwordError) {
                        Text(
                            text = stringResource(R.string.login_error_invalid_password),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Login Error Message
                    if (loginError) {
                        Text(
                            text = stringResource(R.string.login_error_credentials),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Logica del boton

                }
                Button(
                    onClick = {
                        if (!emailError && !passwordError) {
                            // Validacion de login
                            val isValidLogin = validateLogin(email, password)
                            if (isValidLogin) {
                                navController.navigate(Screen.Recommendations.route) // Navigate to home after successful login
                            } else {
                                loginError = true // Show login error message
                            }

                        }
                    },
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    enabled = email.isNotEmpty() && password.isNotEmpty() // Habilitado cuando los campos no estan vacios
                ) {
                    Text(stringResource(R.string.login_button))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandscapeLoginLayout(navController: NavHostController){
    // State para email y contrasena
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State para mensajes de error
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf(false) }

    Scaffold(

    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(1f) // Center the entire Column
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
                            modifier = Modifier.padding(bottom = 12.dp),
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Campo de Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                                emailError =
                                    !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                            },
                            label = {
                                Text(
                                    stringResource(R.string.login_email_hint),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            isError = emailError,
                            textStyle = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.fillMaxWidth(),
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

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo de contrasena
                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                passwordError =
                                    it.length < 6 // Password must be at least 6 characters
                            },
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
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        if (passwordError) {
                            Text(
                                text = stringResource(R.string.login_error_invalid_password),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Login Error Message
                        if (loginError) {
                            Text(
                                text = stringResource(R.string.login_error_credentials),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Logica del boton

                    }
                }

                Button(
                    onClick = {
                        if (!emailError && !passwordError) {
                            // Validacion de login
                            val isValidLogin = validateLogin(email, password)
                            if (isValidLogin) {
                                navController.navigate(Screen.Recommendations.route) // Navigate to home after successful login
                            } else {
                                loginError = true // Show login error message
                            }

                        }
                    },
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    enabled = email.isNotEmpty() && password.isNotEmpty() // Habilitado cuando los campos no estan vacios
                ) {
                        Text(stringResource(R.string.login_button))
                    }

            }
        }
    }
}

fun validateLogin(email: String, password: String): Boolean {
    // This is a mock function; replace with actual login logic
    return email == "test@example.com" && password == "password123"
}