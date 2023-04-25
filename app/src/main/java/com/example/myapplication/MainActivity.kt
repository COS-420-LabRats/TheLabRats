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

        val home = Intent(this, HomeActivity::class.java)
        startActivity(home)
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