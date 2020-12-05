package com.example.tommychatting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tommychatting.ConstantUtil
import com.example.tommychatting.LocalSession
import com.example.tommychatting.R
import com.example.tommychatting.databinding.FragmentLoginBinding
import com.example.tommychatting.model.UserModel
import com.example.tommychatting.utils.showToast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    private val auth by lazy { Firebase.auth }
    private val db by lazy { Firebase.firestore }
    private val localSession by lazy { LocalSession(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            btnRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
            btnLogin.setOnClickListener {
                if (tieEmail.text.toString().isNotEmpty() &&
                    tiePassword.text.toString().isNotEmpty()
                ) {
                    showLoading(true)

                    auth.signInWithEmailAndPassword(
                        tieEmail.text.toString(),
                        tiePassword.text.toString()
                    ).addOnSuccessListener {
                        if (it.user?.isEmailVerified == true) {

                            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                        } else {
                            showToast("Belum Ferivikasi Email")
                        }
                        it.user?.uid.let { uid ->
                            if (uid != null) {
                                localSession.uid = uid
                            }
                        }
                        it.user?.uid?.let { uid ->
                            localSession.uid = uid

                            db.collection(ConstantUtil.COLLECTION).document(uid)
                                .set(UserModel(uid, tieEmail.text.toString()))
                                .addOnSuccessListener {
                                    showLoading(false)

                                    showToast( "ada suatu kesalahan")

                                }.addOnFailureListener { exc ->
                                    exc.printStackTrace()

                                    showLoading(false)
                                }
                        }




                        showLoading(false)
                    }.addOnFailureListener {
                        it.printStackTrace()
                        showToast(it.message ?: "oop sesuatu bermasalah")
                        showLoading(false)
                    }
                } else {
                    showToast("Tidak boleh kosong")
                }
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (localSession.uid.isNotEmpty()) findNavController().navigate(R.id.action_loginFragment_to_latestMessageFragment2)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.btnRegister.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
        binding.btnLogin.visibility = if (!isLoading) View.VISIBLE else View.GONE
    }


}