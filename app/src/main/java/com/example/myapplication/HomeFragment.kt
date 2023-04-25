package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {
    private lateinit var logout: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        logout = viewBinding.logoutButton
        logout.setOnClickListener { logOut() }
        firebaseAuth = FirebaseAuth.getInstance()
        FirebaseApp.initializeApp(requireContext())
        db = FirebaseFirestore.getInstance()

        val toolbar: Toolbar = viewBinding.HomeToolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        setHasOptionsMenu(true)

        return viewBinding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileCard = childFragmentManager.beginTransaction()
        profileCard.replace(R.id.ProfileCard, ProfileCardFragment())
        profileCard.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // ...
            R.id.ProfileCard -> {
                parentFragmentManager.beginTransaction().replace(R.id.container, ProfileFragment()).commit()
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
                                    "Student" -> {
                                        Log.d("HomeFragment", "currentUser: ${currentUser?.uid}")
                                        Log.d("HomeFragment", "documentSnapshot: $documentSnapshot")
                                        Log.d("HomeFragment", "accountType: $accountType")
                                        parentFragmentManager.beginTransaction()
                                            .replace(R.id.container, StudentFragment())
                                            .commit()
                                        true
                                    }
                                    "Regular" -> {
                                        Log.d("HomeFragment", "currentUser: ${currentUser?.uid}")
                                        Log.d("HomeFragment", "documentSnapshot: $documentSnapshot")
                                        Log.d("HomeFragment", "accountType: $accountType")
                                        parentFragmentManager.beginTransaction()
                                            .replace(R.id.container, RegularFragment())
                                            .commit()
                                        true
                                    }
                                    else -> {
                                        Toast.makeText(
                                            context,
                                            "User Does not have account type (Error 97 - HomeFragment).",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Log.d("HomeFragment", "currentUser: ${currentUser?.uid}")
                                        Log.d("HomeFragment", "documentSnapshot: $documentSnapshot")
                                        Log.d("HomeFragment", "accountType: $accountType")
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
                Toast.makeText(context, "Update Preferences feature coming soon.", Toast.LENGTH_LONG).show()
                true
            }
            R.id.Notifications -> {
                Toast.makeText(context, "Notifications feature coming soon.", Toast.LENGTH_LONG).show()
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
        if (isAdded) {
            // Log the user out of Firebase Authentication
            firebaseAuth.signOut()
            // Launch the sign-in activity
            startActivity(Intent(requireContext(), SignInActivity::class.java))
            activity?.finish() // Close the current activity
        }
    }
}