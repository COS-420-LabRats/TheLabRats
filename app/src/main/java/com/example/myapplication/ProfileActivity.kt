package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()

        val accSpinner = findViewById<Spinner>(R.id.accountType)
        val genderSpinner = findViewById<Spinner>(R.id.gender)

        val accOptions = arrayOf("Account Type", "On Campus", "Off Campus")
        val genderOptions = arrayOf("Gender", "Female", "Male", "Prefer not to respond")

        val accAdapter = object : ArrayAdapter<String>(
            this,
            R.layout.spinner_item_disabled,
            accOptions
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0 // disable the first position
            }
        }

        val genderAdapter = object : ArrayAdapter<String>(
            this,
            R.layout.spinner_item_disabled,
            genderOptions
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0 // disable the first position
            }
        }

        accAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        accSpinner.adapter = accAdapter
        accSpinner.setSelection(0, false)

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = genderAdapter
        genderSpinner.setSelection(0, false)


        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            saveProfile()
            Toast.makeText(this, "Profile Information Saved.", Toast.LENGTH_LONG).show()
            // Create an instance of the Fragment you want to navigate to
            val home = Intent(this, HomeActivity::class.java)
            startActivity(home)

        }

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("Profile").document(currentUser.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val profile = documentSnapshot.data
                        val fullNameTextView = findViewById<TextView>(R.id.fullName)
                        val firstName = profile?.get("firstName").toString()
                        val lastName = profile?.get("lastName").toString()
                        fullNameTextView.text = "$firstName $lastName"

                        val firstNameTextView = findViewById<TextView>(R.id.first_name)
                        firstNameTextView.text = profile?.get("firstName").toString()

                        val lastNameTextView = findViewById<TextView>(R.id.last_name)
                        lastNameTextView.text = profile?.get("lastName").toString()

                        val accountTypeSpinner = findViewById<Spinner>(R.id.accountType)
                        val accountAdapter = accountTypeSpinner.adapter as ArrayAdapter<String>
                        val accountPosition = accountAdapter.getPosition(profile?.get("accountType").toString())
                        accountTypeSpinner.setSelection(accountPosition)

                        val birthdayTextView = findViewById<TextView>(R.id.birthday)
                        birthdayTextView.text = profile?.get("birthday").toString()

                        val emailTextView = findViewById<TextView>(R.id.email)
                        if (profile?.get("email") == null) {
                            emailTextView.text = currentUser.email.toString()
                        } else {
                            emailTextView.text = profile["email"].toString()
                        }

                        val phoneTextView = findViewById<TextView>(R.id.phone)
                        phoneTextView.text = profile?.get("phone").toString()


                        val genderSpinner = findViewById<Spinner>(R.id.gender)
                        val genderAdapter = genderSpinner.adapter as ArrayAdapter<String>
                        val genderPosition = genderAdapter.getPosition(profile?.get("gender").toString())
                        genderSpinner.setSelection(genderPosition)

                        val bioTextView = findViewById<TextView>(R.id.biography)
                        bioTextView.text = profile?.get("biography").toString()
                        bioTextView.movementMethod = ScrollingMovementMethod()

                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error fetching document", e)
                }
        } else {
            Log.w(TAG, "Error: current user is null")
        }

    }

    private fun saveProfile() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        val firstNameField = findViewById<AppCompatEditText>(R.id.first_name)
        val firstName = firstNameField.text.toString()
        val lastNameField = findViewById<AppCompatEditText>(R.id.last_name)
        val lastName = lastNameField.text.toString()
        val accountTypeSpinner = findViewById<Spinner>(R.id.accountType)
        val accountType = accountTypeSpinner.selectedItem.toString()
        val birthdayField = findViewById<AppCompatEditText>(R.id.birthday)
        val birthday = birthdayField.text.toString()
        val email = currentUser?.email
        val emailField = findViewById<AppCompatEditText>(R.id.email).apply { setText(email) }
        val phoneField = findViewById<AppCompatEditText>(R.id.phone)
        val phone = phoneField.text.toString()
        val genderSpinner = findViewById<Spinner>(R.id.gender)
        val gender = genderSpinner.selectedItem.toString()
        val bioField = findViewById<AppCompatEditText>(R.id.biography)
        val biography = bioField.text.toString()

        if (firstName.isEmpty()) {
            // Show an error message for the first name field
            firstNameField.error = "First name is required"
            return
        }

        if (lastName.isEmpty()) {
            // Show an error message for the last name field
            lastNameField.error = "Last name is required"
            return
        }

        if (accountType == "Select Account Type") {
            // Show an error message for the account type spinner
            (accountTypeSpinner.selectedView as? AppCompatTextView)?.error =
                "Account type is required"
            return
        }

        if (email == null) {
            // Show an error message for the email field
            emailField.error = "Email is required"
            return
        }

        if (phone.isEmpty()) {
            // Show an error message for the phone field
            phoneField.error = "Phone is required"
            return
        }

        if (gender == "Gender") {
            // Show an error message for the account type spinner
            (genderSpinner.selectedView as? AppCompatTextView)?.error = "Gender is required"
            return
        }

        val profile = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "accountType" to accountType,
            "birthday" to birthday,
            "email" to email,
            "phone" to phone,
            "gender" to gender,
            "biography" to biography
        )

        val id = currentUser.uid
        if (id != null) {
            db.collection("Profile").document(id)
                .set(profile)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot written with ID: $id")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        } else {
            Log.w(TAG, "Error: current user is null")
        }
    }
}