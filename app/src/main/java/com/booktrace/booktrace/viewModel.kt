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
        email:String,
        password:String,
        onLoginSuccess: () -> Unit,
        onLoginError: () -> Unit)
            = viewModelScope.launch {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Booktrace", "singInWithEmailAndPassword: logueado")
                    onLoginSuccess()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Booktrace", "signInWithEmailAndPassword: ${exception.message}")
                onLoginError() // Llama a la función para manejar el error
            }
    }

    fun createUserWithEmailAndPassword(
        displayName: String?,
        email: String,
        password: String,
        onLoginSuccess: () -> Unit
    ){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        createUser(displayName)
                        onLoginSuccess()
                    }else{
                        Log.d("Booktrace", "createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
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