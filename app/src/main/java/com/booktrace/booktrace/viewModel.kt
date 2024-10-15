package com.booktrace.booktrace
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginviewModel: ViewModel() {
    var correo = mutableStateOf("")
    var contraseña = mutableStateOf("")
    var confirmarContraseña = mutableStateOf("")
    var cargando = mutableStateOf(false)
    var errorLogin = mutableStateOf<String?>(null)

    fun login(onSuccess: () -> Unit) {
        if (validateCredentials()) {
            if(validateContraseña()) {
                cargando.value = true
                errorLogin.value = null

                // Simulate network call with delay
                viewModelScope.launch {
                    delay(2000)  // Simulating a delay for network call
                    cargando.value = false
                    onSuccess()
                }
            } else {
                errorLogin.value = "Las contraseñas no coinciden"
            }
        } else {
            errorLogin.value = "Correo o contraseña invalidos"
        }
    }

    // Validation logic
    private fun validateCredentials(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo.value).matches() &&
                contraseña.value.length >= 6
    }

    private fun validateContraseña(): Boolean {
        return contraseña.value == confirmarContraseña.value
    }



}