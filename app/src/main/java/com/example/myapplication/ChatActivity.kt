package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class ChatActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        bottomNavigationView = findViewById(R.id.BottomNav)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Home -> {
                    val home = Intent(this, HomeActivity::class.java)
                    startActivity(home)
                    true
                }
                R.id.Chat -> {
                    val chat = Intent(this, ChatActivity::class.java)
                    startActivity(chat)
                    true
                }
                R.id.Swipe -> {
                    val swipe = Intent(this, SwipeActivity::class.java)
                    startActivity(swipe)
                    true
                }
                else -> false
            }
        }
    }

}

