package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class StudentFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveButton = view.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            saveProfile()
            Toast.makeText(context, "Account Information Saved.", Toast.LENGTH_LONG).show()
        }

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("Profile").document(currentUser.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val profile = documentSnapshot.data
                        val fullNameTextView = view.findViewById<TextView>(R.id.fullName)
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

                        val dormSpinner = view.findViewById<Spinner>(R.id.hasDorm)
                        val dormAdapter = dormSpinner.adapter as ArrayAdapter<String>
                        val dormPosition =
                            dormAdapter.getPosition(accountInfo?.get("hasDorm").toString())
                        dormSpinner.setSelection(dormPosition)

                        val universityTextView = view.findViewById<TextView>(R.id.university)
                        universityTextView.text = accountInfo?.get("university").toString()

                        val gradeSpinner = view.findViewById<Spinner>(R.id.grade)
                        val gradeAdapter = gradeSpinner.adapter as ArrayAdapter<String>
                        val crimePosition = gradeAdapter.getPosition(
                            accountInfo?.get("grade").toString())
                        gradeSpinner.setSelection(crimePosition)

                        val majorTextView = view.findViewById<TextView>(R.id.major)
                        majorTextView.text = accountInfo?.get("major").toString()

                        val religionSpinner = view.findViewById<Spinner>(R.id.religion)
                        val religionAdapter = religionSpinner.adapter as ArrayAdapter<String>
                        val religionPosition = religionAdapter.getPosition(
                            accountInfo?.get("religion").toString())
                        religionSpinner.setSelection(religionPosition)

                        val hobbiesTextView = view.findViewById<TextView>(R.id.hobbies)
                        hobbiesTextView.text = accountInfo?.get("hobbies").toString()
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
        val view = requireView()

        val dormTypeSpinner = view.findViewById<Spinner>(R.id.hasDorm)
        val dorm = dormTypeSpinner.selectedItem.toString()
        val universityField = view.findViewById<AppCompatEditText>(R.id.university)
        val university = universityField.text.toString()
        val gradeSpinner = view.findViewById<Spinner>(R.id.grade)
        val grade = gradeSpinner.selectedItem.toString()
        val majorField = view.findViewById<AppCompatEditText>(R.id.major)
        val major = majorField.text.toString()
        val religionTypeSpinner = view.findViewById<Spinner>(R.id.religion)
        val religion = religionTypeSpinner.selectedItem.toString()
        val hobbiesField = view.findViewById<AppCompatEditText>(R.id.hobbies)
        val hobbies = hobbiesField.text.toString()


        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        var accountType: String? = null // define accountType outside of the lambda function
        if (currentUser != null) {
            db.collection("Profile").document(currentUser.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val profile = documentSnapshot.data
                        accountType = profile?.get("accountType").toString()

                        // use the accountType variable inside the lambda function
                        val accountInfo = hashMapOf(
                            "accountType" to accountType,
                            "dorm" to dorm,
                            "university" to university,
                            "grade" to grade,
                            "major" to major,
                            "religion" to religion,
                            "hobbies" to hobbies,
                        )

                        db.collection("AccountInfo").document(currentUser.uid)
                            .set(accountInfo)
                            .addOnSuccessListener {
                                Log.d(TAG, "Account information saved")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error saving account information", e)
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