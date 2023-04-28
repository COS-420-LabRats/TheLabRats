package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivitySignInBinding
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.google.android.gms.common.internal.FallbackServiceBroker
import com.google.firebase.auth.FirebaseAuth


class Match() {

    fun Swipe(User1: User, User2: User, direction: String): Boolean {
        if (direction == "up") {
            return true
        } else { //eg if direction == "down"
            return false
        }


    }

}