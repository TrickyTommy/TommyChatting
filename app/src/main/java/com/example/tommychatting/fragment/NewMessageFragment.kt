package com.example.tommychatting.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tommychatting.R
import com.example.tommychatting.databinding.FragmentNewMessageBinding

class NewMessageFragment : Fragment(){
    private lateinit var binding : FragmentNewMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentNewMessageBinding.inflate(inflater, container, false).apply{
            tvSignin2.setOnClickListener {
                findNavController().navigate(R.id.action_latestMessageFragment_to_newMessageFragment)
            }


        }
        return binding.root
        get

    }



}