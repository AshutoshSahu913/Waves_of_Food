package com.example.wavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.RecentBuyAdapter
import com.example.wavesoffood.DataClass.OrderDetails
import com.example.wavesoffood.databinding.ActivityRecentOrderItemBinding

class RecentOrderItem : AppCompatActivity() {
    val binding: ActivityRecentOrderItemBinding by lazy {
        ActivityRecentOrderItemBinding.inflate(layoutInflater)
    }

    private lateinit var allFoodNames: ArrayList<String>
    private lateinit var allFoodImgs: ArrayList<String>
    private lateinit var allFoodPrices: ArrayList<String>
    private lateinit var allFoodQty: ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recentOrderItems =
            intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<OrderDetails>
        recentOrderItems?.let { orderDetails ->
            if (orderDetails.isNotEmpty()) {
                val recentOrderItem = orderDetails[0]
                allFoodNames = recentOrderItem.foodNames as ArrayList<String>
                allFoodPrices = recentOrderItem.foodPrices as ArrayList<String>
                allFoodImgs = recentOrderItem.foodImages as ArrayList<String>
                allFoodQty = recentOrderItem.foodQty as ArrayList<Int>

            }
        }
        setAdapter()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun setAdapter() {
        val recentBuyAdapter =
            RecentBuyAdapter(this, allFoodNames, allFoodPrices, allFoodImgs, allFoodQty)
        binding.rvRecentBuy.layoutManager = LinearLayoutManager(this)
        binding.rvRecentBuy.adapter = recentBuyAdapter
    }
}