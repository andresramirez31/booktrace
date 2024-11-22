package com.booktrace.booktrace

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booktrace.booktrace.ui.model.Book
import com.booktrace.booktrace.ui.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class viewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    private val _bookList = MutableStateFlow<List<Book>>(emptyList())
    var bookList = _bookList.asStateFlow()

    init {
        getBookList()
    }

    private fun getBookList() {
        val db = Firebase.firestore

        db.collection("books")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (value != null) {
                    _bookList.value = value.toObjects(Book::class.java)
                    Log.d("ViewModel", "Books: ${_bookList.value}")
                }
            }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onEmailNotVerified: () -> Unit,
        onLoginError: (String) -> Unit
    ) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user?.isEmailVerified == true) {
                            Log.d("Booktrace", "signInWithEmailAndPassword: logueado")
                            onLoginSuccess()
                        } else {
                            Log.d("Booktrace", "signInWithEmailAndPassword: email no verificado")
                            auth.signOut()
                            onEmailNotVerified()
                        }
                    } else {
                        Log.d("Booktrace", "signInWithEmailAndPassword: ${task.exception?.message}")
                        onLoginError(task.exception?.message ?: "Error desconocido.")
                    }
                }
        }
    }

    fun createUserWithEmailAndPassword(
        displayName: String?,
        email: String,
        password: String,
        onVerificationRequired: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.let {
                            it.sendEmailVerification()
                                .addOnCompleteListener { emailTask ->
                                    if (emailTask.isSuccessful) {
                                        Log.d("Booktrace", "Verification email sent to ${user.email}")
                                        createUser(displayName)
                                        onVerificationRequired()
                                    } else {
                                        Log.d("Booktrace", "Error sending email verification: ${emailTask.exception}")
                                        onError("Error enviando el correo de verificación.")
                                    }
                                }
                        }
                    } else {
                        Log.d("Booktrace", "createUserWithEmailAndPassword: ${task.exception?.message}")
                        onError(task.exception?.message ?: "Error desconocido.")
                    }
                    _loading.value = false
                }
        }
    }

    fun fetchDisplayName(
        onResult: (String?) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("user_id", userId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val displayName = querySnapshot.documents[0].getString("display_name")
                        Log.d("Booktrace", "User's displayName: $displayName")
                        onResult(displayName)
                    } else {
                        Log.d("Booktrace", "No se encontro el usuario.")
                        onResult(null)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Booktrace", "Error al buscar el usuario: $exception")
                    onResult(null)
                }
        } else {
            Log.d("Booktrace", "El usuario no esta logueado.")
            onResult(null)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid

        val user = User(
            id = null,
            userId = userId.toString(),
            displayName = displayName.toString()
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("Booktrace", "createUser: user_id ${it.id}")
            }
            .addOnFailureListener {
                Log.d("Booktrace", "createUser: ocurrió un error ${it}")
            }
    }
}