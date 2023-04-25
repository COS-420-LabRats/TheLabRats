package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {

    private lateinit var logout: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.HomeToolbar)
        setSupportActionBar(toolbar)

        logout = findViewById(R.id.logoutButton)
        logout.setOnClickListener { logOut() }
        firebaseAuth = FirebaseAuth.getInstance()
        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()

        supportFragmentManager.beginTransaction()
            .replace(R.id.ProfileCard, ProfileCardFragment())
            .commit()

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // ...
            R.id.ProfileCardUpdate -> {
                val profileSettings = Intent(this, ProfileActivity::class.java)
                startActivity(profileSettings)
                true
            }
            R.id.ProfileInfo -> {
                val auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser
                var accountType: String? = null
                if (currentUser != null) {
                    db.collection("Profile").document(currentUser.uid)
                        .get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                val profile = documentSnapshot.data
                                accountType = profile?.get("accountType")
                                    .toString()
                                when (accountType) {
                                    "On Campus" -> {
                                        val studentSettings = Intent(this, OnCampusUserProfileActivity::class.java)
                                        startActivity(studentSettings)
                                        true
                                    }
                                    "Off Campus" -> {
                                        val regularSettings = Intent(this, OffCampusUserProfileActivity::class.java)
                                        startActivity(regularSettings)
                                        true
                                    }
                                    else -> {
                                        Toast.makeText(
                                            this,
                                            "User Does not have account type (Error 97 - HomeActivity).",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        true
                                    }
                                }

                            }
                        }.addOnFailureListener { e ->
                            Log.w(ContentValues.TAG, "Error fetching document", e)
                        }
                } else {
                    Log.w(ContentValues.TAG, "Error: current user is null")
                }
                true
            }
            R.id.UpdatePreferences -> {
                Toast.makeText(this, "Update Preferences feature coming soon.", Toast.LENGTH_LONG).show()
                true
            }
            R.id.Notifications -> {
                Toast.makeText(this, "Notifications feature coming soon.", Toast.LENGTH_LONG).show()
                true
            }
            R.id.logoutButton -> {
                logOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logOut() {
        // Log the user out of Firebase Authentication
        firebaseAuth.signOut()
        // Launch the sign-in activity
        startActivity(Intent(this, SignInActivity::class.java))
        finish() // Close the current activity
    }
}
