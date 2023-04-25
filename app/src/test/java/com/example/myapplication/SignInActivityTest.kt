package com.example.myapplication

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import com.google.firebase.auth.FirebaseAuth

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySignInBinding



class SignInActivityTest(){
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding:ActivitySignInBinding


@Test
    fun `email matches returns true` (){
        val result = firebaseAuth.signInWithEmailAndPassword(
            "",
            "secret Word"

        )
        assertThat(result).isFalse()
    }


}