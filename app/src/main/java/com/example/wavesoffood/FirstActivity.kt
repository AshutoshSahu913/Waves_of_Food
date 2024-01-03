package com.example.wavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.wavesoffood.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    private val binding: ActivityFirstBinding by lazy {
        ActivityFirstBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nextBtn.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }
    }
}