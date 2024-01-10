package com.example.wavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wavesoffood.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val foodName = intent.getStringExtra("menuFoodName")
        val foodImg = intent.getIntExtra("menuFoodImg", 0)

        binding.detailsFoodName.text = foodName
        binding.detailsFoodImg.setImageResource(foodImg)

        binding.backBtn.setOnClickListener {
            finish()
        }

    }
}