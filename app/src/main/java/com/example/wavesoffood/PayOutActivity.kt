package com.example.wavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wavesoffood.databinding.ActivityPayOutActitvityBinding

class PayOutActivity : AppCompatActivity() {
    val binding: ActivityPayOutActitvityBinding by lazy {
        ActivityPayOutActitvityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.placeOrderBtn.setOnClickListener {
            val bottomSheetDialog = CongratsFragment()
            bottomSheetDialog.show(supportFragmentManager, "Test")
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}