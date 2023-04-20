package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private lateinit var logout: Button
    private lateinit var firebaseAuth: FirebaseAuth

   /* override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        logout = view.findViewById(R.id.logoutButton)
        logout.setOnClickListener { logOut() }

        val toolbar = view.findViewById<Toolbar>(R.id.HomeToolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()

        setupMenu() // enable options menu

        return view
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout = view.findViewById(R.id.logoutButton)
        logout.setOnClickListener { logOut() }

        val toolbar = view.findViewById<Toolbar>(R.id.HomeToolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()

        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_menu, menu)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
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

        }, viewLifecycleOwner)
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
