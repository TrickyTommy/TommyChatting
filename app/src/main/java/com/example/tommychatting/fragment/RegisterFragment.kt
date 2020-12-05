package com.example.tommychatting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tommychatting.databinding.FragmentRegisterBinding
import com.example.tommychatting.utils.showToast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {


    private lateinit var binding: FragmentRegisterBinding
    private val auth by lazy { Firebase.auth }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false).apply {
            tvSignin.setOnClickListener { requireActivity().onBackPressed() }
            btnRegister.setOnClickListener {
                if (tieName.text.toString().isNotEmpty() &&
                    tieEmail.text.toString().isNotEmpty() &&
                    tiePassword.text.toString().isNotEmpty()
                ) {
                    showLoading(true)

                    auth.createUserWithEmailAndPassword(
                        tieEmail.text.toString(),
                        tiePassword.text.toString()
                    ).addOnSuccessListener {
                        showLoading(false)

                        it.user?.sendEmailVerification()
                        requireActivity().onBackPressed()
                    }.addOnFailureListener {
                        it.printStackTrace()

                        showToast(it.message ?: "ada suatu kesalahan")
                        showLoading(false)
                    }
                } else {
                    showToast("field tidak boleh kosong")
                }
            }
        }
        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.btnRegister.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
    }


}
