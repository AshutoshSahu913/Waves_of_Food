package com.example.wavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wavesoffood.databinding.ActivityFirstBinding
import com.example.wavesoffood.databinding.ActivityLoginPageBinding

class LoginPage : AppCompatActivity() {
    private val binding: ActivityLoginPageBinding by lazy {
        ActivityLoginPageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.goSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpPage::class.java))
        }
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }

    }
}