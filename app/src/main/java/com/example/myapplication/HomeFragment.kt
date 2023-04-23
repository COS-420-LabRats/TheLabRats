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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileCard = childFragmentManager.beginTransaction()
        profileCard.replace(R.id.ProfileCard, ProfileCardFragment())
        profileCard.commit()
    }


    @Deprecated("Deprecated in Java", ReplaceWith("inflater.inflate(R.menu.top_menu, menu)"))
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Settings -> {
                Toast.makeText(context, "Settings feature coming soon.", Toast.LENGTH_LONG).show()
                true
            }
            R.id.UpdateProfile -> {
                parentFragmentManager.beginTransaction().replace(R.id.container, ProfileFragment()).commit()
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