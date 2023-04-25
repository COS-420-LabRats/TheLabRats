package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()

        bottomNavigationView = findViewById(R.id.BottomNav)

        //This will show how many messages a user has on read once we figure out that number:
        val badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.Chat)
        badgeDrawable.isVisible = true
        badgeDrawable.number = 10

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment()).commit()
                    true
                }
                R.id.Chat -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ChatFragment()).commit()
                    true
                }
                R.id.Swipe -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SwipeFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = firebaseAuth.currentUser // Get the current user
        if (currentUser == null) {
            // No one is logged in
            startActivity(Intent(this, SignInActivity::class.java)) // Start the sign-in activity
            finish() // Close the current activity
        }
        // Otherwise, the user is already logged in, and we can continue displaying the main activity
    }
}