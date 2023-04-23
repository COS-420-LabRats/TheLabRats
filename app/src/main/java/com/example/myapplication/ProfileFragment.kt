package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
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

        val accSpinner = view.findViewById<Spinner>(R.id.acc_txt)
        val genderSpinner = view.findViewById<Spinner>(R.id.gender_spinner)

        val accOptions = arrayOf("Account Type", "Student", "Regular")
        val genderOptions = arrayOf("Gender", "Female", "Male", "Prefer not to respond")

        val accAdapter = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item_disabled,
            accOptions
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0 // disable the first position
            }
        }

        val genderAdapter = object : ArrayAdapter<String>(
            requireContext(),
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


        val saveButton = view.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            saveProfile()
            Toast.makeText(context, "Profile Information Saved.", Toast.LENGTH_LONG).show()
        }

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("profile").document(currentUser.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val profile = documentSnapshot.data
                        val fullNameTextView = view.findViewById<TextView>(R.id.fullName)
                        val firstName = profile?.get("firstName").toString()
                        val lastName = profile?.get("lastName").toString()
                        fullNameTextView.text = "$firstName $lastName"

                        val firstNameTextView = view.findViewById<TextView>(R.id.first_name)
                        firstNameTextView.text = profile?.get("firstName").toString()

                        val lastNameTextView = view.findViewById<TextView>(R.id.last_name)
                        lastNameTextView.text = profile?.get("lastName").toString()

                        val accountTypeSpinner = view.findViewById<Spinner>(R.id.acc_txt)
                        val accountAdapter = accountTypeSpinner.adapter as ArrayAdapter<String>
                        val accountPosition = accountAdapter.getPosition(profile?.get("accountType").toString())
                        accountTypeSpinner.setSelection(accountPosition)

                        val birthdayTextView = view.findViewById<TextView>(R.id.bday_txt)
                        birthdayTextView.text = profile?.get("birthday").toString()

                        val emailTextView = view.findViewById<TextView>(R.id.email_txt)
                        if (profile?.get("email") == null) {
                            emailTextView.text = currentUser.email.toString()
                        } else {
                            emailTextView.text = profile["email"].toString()
                        }

                        val phoneTextView = view.findViewById<TextView>(R.id.phone_txt)
                        phoneTextView.text = profile?.get("phone").toString()

                        val genderTypeSpinner = view.findViewById<Spinner>(R.id.gender_spinner)
                        val genderAdapter = genderTypeSpinner.adapter as ArrayAdapter<String>
                        val genderPosition = genderAdapter.getPosition(profile?.get("gender").toString())
                        genderTypeSpinner.setSelection(genderPosition)

                        val bioTextView = view.findViewById<TextView>(R.id.bio_txt)
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
            db.collection("profile").document(id)
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