package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class OffCampusUserProfileActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offcampuslayout)
        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()
        loadData()
        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            saveProfile()
            Toast.makeText(this, "Account Information Saved.", Toast.LENGTH_LONG).show()
            val home = Intent(this, HomeActivity::class.java)
            startActivity(home)
        }
    }

    private fun loadData() {
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
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error fetching document", e)
                }
        } else {
            Log.w(TAG, "Error: current user is null")
        }


        if (currentUser != null) {
            db.collection("AccountInfo").document(currentUser.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val accountInfo = documentSnapshot.data

                        val addressSpinner = findViewById<Spinner>(R.id.address)
                        val addressAdapter = addressSpinner.adapter as ArrayAdapter<String>
                        val addressPosition =
                            addressAdapter.getPosition(accountInfo?.get("hasAddress").toString())
                        addressSpinner.setSelection(addressPosition)

                        val jobTextView = findViewById<TextView>(R.id.job)
                        jobTextView.text = accountInfo?.get("job").toString()

                        val rentView = findViewById<TextView>(R.id.rent)
                        rentView.text = accountInfo?.get("rent").toString()

                        val crimeSpinner = findViewById<Spinner>(R.id.hasCriminalHistory)
                        val crimeAdapter = crimeSpinner.adapter as ArrayAdapter<String>
                        val crimePosition = crimeAdapter.getPosition(
                            accountInfo?.get("hasCriminalHistory").toString()
                        )
                        crimeSpinner.setSelection(crimePosition)

                        val crimeTextView = findViewById<TextView>(R.id.criminalHistory)
                        crimeTextView.text = accountInfo?.get("criminalHistory").toString()

                        val petsSpinner = findViewById<Spinner>(R.id.pets)
                        val petsAdapter = petsSpinner.adapter as ArrayAdapter<String>
                        val petsPosition =
                            petsAdapter.getPosition(accountInfo?.get("pets").toString())
                        petsSpinner.setSelection(petsPosition)

                        val kidsSpinner = findViewById<Spinner>(R.id.kids)
                        val kidsAdapter = kidsSpinner.adapter as ArrayAdapter<String>
                        val kidsPosition =
                            kidsAdapter.getPosition(accountInfo?.get("kids").toString())
                        kidsSpinner.setSelection(kidsPosition)

                        val smokerSpinner = findViewById<Spinner>(R.id.smoker)
                        val smokerAdapter = smokerSpinner.adapter as ArrayAdapter<String>
                        val smokerPosition =
                            smokerAdapter.getPosition(accountInfo?.get("smoker").toString())
                        smokerSpinner.setSelection(smokerPosition)
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
        val addressTypeSpinner = findViewById<Spinner>(R.id.address)
        val address = addressTypeSpinner.selectedItem.toString()
        val jobField = findViewById<AppCompatEditText>(R.id.job)
        val job = jobField.text.toString()
        val rentField = findViewById<AppCompatEditText>(R.id.rent)
        val rent = rentField.text.toString()
        val crimeSpinner = findViewById<Spinner>(R.id.hasCriminalHistory)
        val hasCriminalHistory = crimeSpinner.selectedItem.toString()
        val crimeField = findViewById<AppCompatEditText>(R.id.criminalHistory)
        val criminalHistory = crimeField.text.toString()
        val petsTypeSpinner = findViewById<Spinner>(R.id.pets)
        val pets = petsTypeSpinner.selectedItem.toString()
        val kidsTypeSpinner = findViewById<Spinner>(R.id.kids)
        val kids = kidsTypeSpinner.selectedItem.toString()
        val smokerTypeSpinner = findViewById<Spinner>(R.id.smoker)
        val smoker = smokerTypeSpinner.selectedItem.toString()


        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        var accountType: String? = null // define accountType outside of the lambda function
        if (currentUser != null) {
            db.collection("Profile").document(currentUser.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val profile = documentSnapshot.data
                        accountType = profile?.get("accountType").toString() // set the value of accountType inside the lambda function
                        if (accountType != null) {
                            val accountInfo = hashMapOf(
                                "accountType" to accountType,
                                "hasAddress" to address,
                                "job" to job,
                                "rent" to rent,
                                "hasCriminalHistory" to hasCriminalHistory,
                                "criminalHistory" to criminalHistory,
                                "pets" to pets,
                                "kids" to kids,
                                "smoker" to smoker
                            )

                            if (currentUser != null) {
                                db.collection("AccountInfo").document(currentUser.uid)
                                    .set(accountInfo)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "Account Info saved")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                    }
                            }
                        } else {
                            Log.w(TAG, "Error: accountType is null")
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error fetching document", e)
                }
        } else {
            Log.w(TAG, "Error: current user is null")
        }
    }


}