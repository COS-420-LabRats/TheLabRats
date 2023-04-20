package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private lateinit var logout: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout = view.findViewById(R.id.logoutButton)
        logout.setOnClickListener { logOut() }

        // Create a new instance of Toolbar
        val toolbar = Toolbar(requireContext())
        // Set the toolbar as the support action bar for the activity
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        // Set the title for the toolbar
        toolbar.title = "My Toolbar Title"

        firebaseAuth = FirebaseAuth.getInstance()

        // Set up the options menu for the toolbar
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Settings -> {
                    Toast.makeText(context, "You clicked Settings", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.UpdateProfile -> {
                    Toast.makeText(context, "You clicked Update Profile", Toast.LENGTH_LONG)
                        .show()
                    true
                }
                R.id.UpdatePreferences -> {
                    Toast.makeText(context, "You clicked Update Preferences", Toast.LENGTH_LONG)
                        .show()
                    true
                }
                else -> false
            }
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
