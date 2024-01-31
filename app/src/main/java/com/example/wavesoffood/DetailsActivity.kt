package com.example.wavesoffood

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.wavesoffood.DataClass.CartItems
import com.example.wavesoffood.databinding.ActivityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDes: String
    private lateinit var foodIngredients: String
    private lateinit var foodImg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize auth datbase
        auth = FirebaseAuth.getInstance()

        foodName = intent.getStringExtra("menuItemName").toString()
        foodPrice = intent.getStringExtra("menuItemPrice").toString()
        foodDes = intent.getStringExtra("menuItemDes").toString()
        foodIngredients = intent.getStringExtra("menuItemIngredients").toString()
        foodImg = intent.getStringExtra("menuItemImg").toString()


        with(binding) {
            detailsFoodName.text = foodName
            detailsFoodDescription.text = "$foodName\nPrice: ₹$foodPrice\n$foodDes"
            detailsFoodIngredients.text = foodIngredients
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImg)).into(detailsFoodImg)
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.addToCartBtn.setOnClickListener {
            binding.addToCartBtn.text = "Added to Cart"
            binding.addToCartBtn.setBackgroundResource(R.drawable.greenbuttongradient2)
            addItemToCart()
        }


    }

    private fun addItemToCart() {

        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""

        //create a cartItems Objects
        val cartItems = CartItems(foodName, foodPrice, foodDes, foodImg, 1, foodIngredients)
        database.child("user").child(userId).child("CartItems").push().setValue(cartItems)
            .addOnSuccessListener {
                Toast.makeText(this, "Item added into cart Successfully 👻", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }.addOnFailureListener {
            Toast.makeText(this, "Item Not Added 😰", Toast.LENGTH_SHORT).show()
        }
    }
}