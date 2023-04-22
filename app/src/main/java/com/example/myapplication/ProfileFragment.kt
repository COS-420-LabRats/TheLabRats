package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveButton = view.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        val view = requireView()
        val firstNameField = view.findViewById<AppCompatEditText>(R.id.first_name)
        val firstName = firstNameField.text.toString()
        val lastNameField = view.findViewById<AppCompatEditText>(R.id.last_name)
        val lastName = lastNameField.text.toString()
        val accountTypeSpinner = view.findViewById<Spinner>(R.id.acc_txt)
        val accountType = accountTypeSpinner.selectedItem.toString()
        val birthdayField = view.findViewById<AppCompatEditText>(R.id.bday_txt)
        val birthday = birthdayField.text.toString()
        val email = currentUser?.email
        val emailField = view.findViewById<AppCompatEditText>(R.id.email_txt).apply { setText(email) }
        val phoneField = view.findViewById<AppCompatEditText>(R.id.phone_txt)
        val phone = phoneField.text.toString()
        val genderSpinner = view.findViewById<Spinner>(R.id.gender_spinner)
        val gender = genderSpinner.selectedItem.toString()
        val bioField = view.findViewById<AppCompatEditText>(R.id.bio_txt)
        val bio = bioField.text.toString()

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

        if (email != null) {
            if (email.isEmpty()) {
                // Show an error message for the email field
                emailField.error = "Email is required"
                return
            }
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
                "bio" to bio
            )

            db.collection("profile")
                .add(profile)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }

    }