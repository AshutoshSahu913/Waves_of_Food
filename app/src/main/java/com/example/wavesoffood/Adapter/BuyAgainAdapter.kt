package com.example.wavesoffood.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.databinding.BuyAgainItemBinding

class BuyAgainAdapter(
    private var againFoodName: MutableList<String>,
    private var againFoodPrice: MutableList<String>,
    private var againFoodImg: MutableList<String>,
    var context: Context

) :
    RecyclerView.Adapter<BuyAgainAdapter.viewHolder>() {

    inner class viewHolder(val binding: BuyAgainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, foodImg: String) {

            binding.apply {
                buyAgainFoodName.text = foodName
                buyAgainFoodPrice.text = foodPrice
                //load image using Glide
                val uriString = foodImg
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(binding.buyAgainFoodImg)


                binding.buyAgainFoodBtn.setOnClickListener {
                    Toast.makeText(
                        context,
                        "Buy Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainAdapter.viewHolder {
        val binding =
            BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuyAgainAdapter.viewHolder, position: Int) {

        holder.bind(
            againFoodName[position],
            againFoodPrice[position],
            againFoodImg[position]
        )
//        holder.binding.buyAgainFoodImg.setImageResource(foodModel.foodImgUrl)
    }

    override fun getItemCount(): Int {
        return againFoodName.size
    }

}



