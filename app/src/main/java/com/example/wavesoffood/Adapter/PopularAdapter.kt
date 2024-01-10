package com.example.wavesoffood.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.DetailsActivity
import com.example.wavesoffood.databinding.PopularItemBinding

class PopularAdapter(private val list: ArrayList<FoodModel>, var requiredContext: Context) :
    RecyclerView.Adapter<PopularAdapter.viewHolder>() {
    inner class viewHolder(var binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.viewHolder {
        var binding = PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularAdapter.viewHolder, position: Int) {

        var model = list[position]
        holder.binding.foodImg.setImageResource(model.foodImg)
        holder.binding.foodName.text = model.foodName
        holder.binding.foodPrice.text = model.foodPrice
        holder.binding.addToCartBtn.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Added to Cart", Toast.LENGTH_SHORT).show();
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(requiredContext, DetailsActivity::class.java)
            intent.putExtra("menuFoodName", model.foodName)
            intent.putExtra("menuFoodImg", model.foodImg)
            requiredContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}