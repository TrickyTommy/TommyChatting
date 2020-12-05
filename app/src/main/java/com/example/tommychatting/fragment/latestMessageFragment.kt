package com.example.tommychatting.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tommychatting.IOnBackPressed
import com.example.tommychatting.R
import com.example.tommychatting.databinding.FragmentLatestMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class latestMessageFragment : Fragment() {

    private lateinit var binding: FragmentLatestMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLatestMessageBinding.inflate(inflater, container, false).apply {

            verifyUserLoggin()


        }
        return binding.root
    }

    private fun verifyUserLoggin() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val frag =
                findNavController().navigate(R.id.action_latestMessageFragment_to_registerFragment)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.menu_new_message -> {
                val frag =
                    findNavController().navigate(R.id.action_latestMessageFragment_to_newMessageFragment)


            }
            R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(R.id.action_latestMessageFragment_to_registerFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}