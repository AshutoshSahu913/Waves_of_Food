package com.example.wavesoffood.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wavesoffood.DataClass.UserModel
import com.example.wavesoffood.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {


    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserData()

        binding.saveInfoBtn.setOnClickListener {
            val name = binding.profileEdName.text.toString()
            val email = binding.profileEdEmail.text.toString()
            val address = binding.profileEdAddress.text.toString()
            val phone = binding.profileEdPhone.text.toString()

            updateUserData(name, email, address, phone)
        }
    }

    private fun updateUserData(name: String, email: String, address: String, phone: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("user").child(userId)
            val userData = hashMapOf(
                "name" to name,
                "email" to email,
                "address" to address,
                "phone" to phone
            )

            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(),"Profile Update Successfully !",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Profile Update Failed !",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("user").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null) {
                            binding.profileEdName.setText(userProfile.name)
                            binding.profileEdEmail.setText(userProfile.email)
                            binding.profileEdAddress.setText(userProfile.address)
                            binding.profileEdPhone.setText(userProfile.phone)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }
}