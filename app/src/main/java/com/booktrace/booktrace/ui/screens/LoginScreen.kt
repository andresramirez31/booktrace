package com.booktrace.booktrace.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.booktrace.booktrace.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    // State para email y contrasena
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State para mensajes de error
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.login_title)) })
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {

                // Campo de Email
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    },
                    label = { Text(stringResource(R.string.login_email_hint)) },
                    isError = emailError,
                    modifier = Modifier.fillMaxWidth(),
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
                    label = { Text(stringResource(R.string.login_password_hint)) },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError,
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
                Button(
                    onClick = {
                        if (!emailError && !passwordError) {
                            // Validacion de login
                            val isValidLogin = validateLogin(email, password)
                            if (isValidLogin) {
                                navController.navigate("recommendations") // Navigate to home after successful login
                            } else {
                                loginError = true // Show login error message
                            }

                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
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