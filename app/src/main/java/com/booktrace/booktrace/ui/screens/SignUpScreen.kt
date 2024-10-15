package com.booktrace.booktrace.ui.screens

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
import androidx.compose.ui.text.input.PasswordVisualTransformation


@Composable
fun SignUpScreen(navController: NavHostController) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var confirmarContraseña by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Registro de Usuario",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = confirmarContraseña,
            onValueChange = { confirmarContraseña = it },
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                if (nombre.isNotEmpty() && correo.isNotEmpty() && contraseña.isNotEmpty() && confirmarContraseña.isNotEmpty()) {
                    if (contraseña == confirmarContraseña) {
                        if (esCorreoValido(correo)) {
                            // Simulación del guardado del registro
                            val registroExitoso = guardarUsuarioSimulado(nombre, correo, contraseña)
                            if (registroExitoso) {
                                // Mostrar mensaje de éxito
                                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                navController.navigate("recommendations")
                            } else {
                                // Mostrar error de guardado
                                Toast.makeText(context, "Error al guardar el registro", Toast.LENGTH_SHORT).show()
                            }
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
            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Registrarse")
        }
    }
}

fun guardarUsuarioSimulado(nombre: String, correo: String, contraseña: String): Boolean {
    // Simulación del guardado del registro
    println("Guardando usuario: $nombre, $correo, $contraseña")
    
    return true
}


fun esCorreoValido(correo: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(correo).matches()
}

