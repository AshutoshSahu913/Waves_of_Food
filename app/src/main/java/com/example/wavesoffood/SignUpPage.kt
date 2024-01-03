package com.example.wavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wavesoffood.databinding.ActivitySignUpPageBinding

class SignUpPage : AppCompatActivity() {
    private val binding: ActivitySignUpPageBinding by lazy {
        ActivitySignUpPageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.alreadyHaveBtn.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }

    }
}