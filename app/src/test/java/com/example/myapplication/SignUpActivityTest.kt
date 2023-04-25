package com.example.myapplication

import org.junit.Assert.*
import org.junit.Test

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivityTest(){
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    @Test
    fun `email is not empty returns true`(){

        val email = ""
        val pass = "apple"
        val confirmPass = "apple"

        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            assertFalse(it.isSuccessful)
        }


    }
}

