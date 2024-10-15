package com.booktrace.booktrace

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class viewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithEmailAndPassword(email:String, password:String, home: ()-> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Booktrace", "singInWithEmailAndPassword: logueado")
                        home()
                    }else{
                        Log.d("Booktrace", "signInWithEmailAndPassword: ${task.result.toString()}")
                    }
                }
        }
        catch (ex:Exception){
            Log.d("Booktrace", "signInWithEmailAndPassword: ${ex.message}")
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit
    ){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        home()
                    }else{
                        Log.d("Booktrace", "createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
        }
    }

}