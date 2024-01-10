package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wavesoffood.databinding.FragmentCongratsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CongratsFragment : BottomSheetDialogFragment() {
    val binding: FragmentCongratsBinding by lazy {
        FragmentCongratsBinding.inflate(layoutInflater)
    }

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
        val binding = FragmentCongratsBinding.inflate(inflater, container, false)

        binding.goToHome.setOnClickListener {
            val intent=Intent(requireContext(), HomePage::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}