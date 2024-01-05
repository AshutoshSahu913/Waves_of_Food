package com.example.wavesoffood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.databinding.BuyAgainItemBinding

class BuyAgainAdapter(private var list: ArrayList<FoodModel>) :
    RecyclerView.Adapter<BuyAgainAdapter.viewHolder>() {

    inner class viewHolder(val binding: BuyAgainItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainAdapter.viewHolder {
        val binding =
            BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuyAgainAdapter.viewHolder, position: Int) {


        val foodModel = list[position]
        holder.binding.buyAgainFoodImg.setImageResource(foodModel.foodImg)
        holder.binding.buyAgainFoodName.text = foodModel.foodName
        holder.binding.buyAgainFoodPrice.text = foodModel.foodPrice

        holder.binding.buyAgainFoodBtn.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Buy Again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
        override fun getItemCount(): Int {
            return list.size
        }

    }



