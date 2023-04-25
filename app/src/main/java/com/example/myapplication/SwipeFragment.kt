package com.example.myapplication

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

@Suppress("DEPRECATION")
class SwipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }
//To Do Info button isn't working
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Info -> {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Hello World")
                builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                builder.create().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}



