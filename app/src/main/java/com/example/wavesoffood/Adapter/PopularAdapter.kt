package com.example.wavesoffood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.databinding.PopularItemBinding

class PopularAdapter(private val list: ArrayList<FoodModel>) :
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
    }

    override fun getItemCount(): Int {
        return list.size
    }
}