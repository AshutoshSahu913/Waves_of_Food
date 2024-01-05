package com.example.wavesoffood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.databinding.MenuItemBinding

class MenuAdapter(var menuList: ArrayList<FoodModel>) :
    RecyclerView.Adapter<MenuAdapter.viewHolder>() {

    inner class viewHolder(var binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.viewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.viewHolder, position: Int) {
        var model = menuList[position]
        holder.binding.menuFoodImg.setImageResource(model.foodImg)
        holder.binding.menuFoodName.text = model.foodName
        holder.binding.menuFoodPrice.text = model.foodPrice
        holder.binding.menuAddToCartBtn.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Add Menu Items", Toast.LENGTH_SHORT).show();
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}