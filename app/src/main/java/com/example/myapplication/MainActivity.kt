package com.example.myapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {
    private lateinit var logout: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout = findViewById(R.id.logoutButton)
        firebaseAuth = FirebaseAuth.getInstance()
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

    fun logOut(view: View) {
        // Log the user out of Firebase Authentication
        firebaseAuth.signOut()
        // Launch the sign-in activity
        startActivity(Intent(this, SignInActivity::class.java))
        finish() // Close the current activity
    }

}


