package com.example.wavesoffood

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val foodName = intent.getStringExtra("menuItemName")
        val foodPrice = intent.getStringExtra("menuItemPrice")
        val foodDes = intent.getStringExtra("menuItemDes")
        val foodIngredients = intent.getStringExtra( "menuItemIngredients")
        val foodImg = intent.getStringExtra("menuItemImg")


        with(binding) {
            detailsFoodName.text = foodName
            detailsFoodDescription.text = foodDes
            detailsFoodIngredients.text = foodIngredients
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImg)).into(detailsFoodImg)
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

    }
}