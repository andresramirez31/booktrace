package com.booktrace.booktrace.ui.screens

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.booktrace.booktrace.ui.navigation.Screen
import com.booktrace.booktrace.R
import com.booktrace.booktrace.viewModel

@Composable
fun SignUpScreen(navController: NavHostController,
                 viewModel: viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val configuration = LocalConfiguration.current

    SignUpLayout(
        orientation = configuration.orientation,
        onDone = {displayName, email, password ->
            viewModel.createUserWithEmailAndPassword(displayName, email, password) {
                navController.navigate(Screen.Recommendations.route)
            }
        })
}

@Composable
fun SignUpLayout(orientation: Int,
                 onDone: (String, String, String) -> Unit = { displayName, email, password -> }
) {
    var displayName by remember { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmarContraseña by remember { mutableStateOf("") }

    val context = LocalContext.current

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SignUpWelcomeText()
                    SignUpForm(
                        displayName, email, password, confirmarContraseña,
                        onDisplayNameChange = { displayName = it },
                        onCorreoChange = { email = it },
                        onContraseñaChange = { password = it },
                        onConfirmarContraseñaChange = { confirmarContraseña = it },
                        onRegistrarseClick = {
                            checkSingUp(displayName, email, password, confirmarContraseña, context, onDone)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        else -> { // Portrait
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SignUpWelcomeText()
                SignUpForm(
                    displayName, email, password, confirmarContraseña,
                    onDisplayNameChange = { displayName = it },
                    onCorreoChange = { email = it },
                    onContraseñaChange = { password = it },
                    onConfirmarContraseñaChange = { confirmarContraseña = it },
                    onRegistrarseClick = {
                        checkSingUp(displayName, email, password, confirmarContraseña, context, onDone)
                    }
                )
            }
        }
    }
}

@Composable
fun SignUpWelcomeText() {
    Text(
        text = stringResource(R.string.signup_welcome),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(bottom = 12.dp)
    )

    Text(
        text = stringResource(R.string.signup_welcome_2),
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(bottom = 32.dp)
    )
}

@Composable
fun SignUpForm(
    displayName: String,
    email: String,
    password: String,
    confirmarContraseña: String,
    onDisplayNameChange: (String) -> Unit,
    onCorreoChange: (String) -> Unit,
    onContraseñaChange: (String) -> Unit,
    onConfirmarContraseñaChange: (String) -> Unit,
    onRegistrarseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        OutlinedTextField(
            value = displayName,
            onValueChange = onDisplayNameChange,
            label = { Text(stringResource(R.string.signup_username), style= MaterialTheme.typography.labelSmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = MaterialTheme.shapes.medium
        )

        OutlinedTextField(
            value = email,
            onValueChange = onCorreoChange,
            label = { Text(stringResource(R.string.signup_email), style= MaterialTheme.typography.labelSmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = MaterialTheme.shapes.medium
        )

        OutlinedTextField(
            value = password,
            onValueChange = onContraseñaChange,
            label = { Text(stringResource(R.string.signup_password), style= MaterialTheme.typography.labelSmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = MaterialTheme.shapes.medium
        )

        OutlinedTextField(
            value = confirmarContraseña,
            onValueChange = onConfirmarContraseñaChange,
            label = { Text(stringResource(R.string.signup_password_2), style= MaterialTheme.typography.labelSmall) },
            textStyle = MaterialTheme.typography.bodySmall,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
            shape = MaterialTheme.shapes.medium
        )

        Button(
            onClick = onRegistrarseClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}

fun esCorreoValido(correo: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(correo).matches()
}

fun checkSingUp(displayName: String, email: String, password: String, confirmarContraseña: String, context: Context, onDone: (String, String, String) -> Unit){
    if (displayName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmarContraseña.isNotEmpty()) {
        if (password == confirmarContraseña) {
            if (esCorreoValido(email)) {
                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                onDone(displayName, email, password)
            } else {
                // Mostrar error de correo inválido
                Toast.makeText(context, "Correo electrónico inválido", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Mostrar error de contraseñas no coincidentes
            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }
    } else {
        // Mostrar error de campos vacíos
        Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
    }
}