package com.example.tommychatting.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.tommychatting.User
import com.example.tommychatting.databinding.FragmentRegisterBinding
import com.example.tommychatting.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*


class RegisterFragment : Fragment() {

    private var imageUri: Uri? = null
    private lateinit var binding: FragmentRegisterBinding
    private val auth by lazy { Firebase.auth }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false).apply {
            //upload foto
            buttonPhoto.setOnClickListener {

                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)
            }

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
                        uploadImageToFirebaseStorage()
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

    //upload foto
    private fun uploadImageToFirebaseStorage() {
        if (imageUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(imageUri!!)
            .addOnSuccessListener {
                Log.d("Register", "succes${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    saveUserToFirebaseDatabase(it.toString())
                }.addOnFailureListener {

                }
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(uid, binding.tieName.text.toString(), profileImageUrl)
        ref.setValue(user)
            .addOnSuccessListener {

            }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.btnRegister.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
    }

    //upload foto
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, imageUri)

            val img = binding.selectFotoImage
            img.setImageBitmap(bitmap)



//            val bitmapDrawable = BitmapDrawable(bitmap)
//            binding.buttonPhoto.setImageDrawable(bitmapDrawable)
        }


    }


}
