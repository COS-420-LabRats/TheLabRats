package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {
    private lateinit var logout: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        logout = viewBinding.logoutButton
        logout.setOnClickListener { logOut() }
        firebaseAuth = FirebaseAuth.getInstance()

        val toolbar: Toolbar = viewBinding.HomeToolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        setHasOptionsMenu(true)

        return viewBinding.root
    }

    @Deprecated("Deprecated in Java", ReplaceWith("inflater.inflate(R.menu.top_menu, menu)"))
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Settings -> {
                Toast.makeText(context, "You clicked Settings", Toast.LENGTH_LONG).show()
                true
            }
            R.id.UpdateProfile -> {
                Toast.makeText(context, "You clicked Update Profile", Toast.LENGTH_LONG).show()
                true
            }
            R.id.UpdatePreferences -> {
                Toast.makeText(context, "You clicked Update Preferences", Toast.LENGTH_LONG).show()
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
