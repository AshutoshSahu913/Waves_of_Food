package com.example.wavesoffood.Adapter

import android.content.Context
import android.content.Intent
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.DetailsActivity
import com.example.wavesoffood.databinding.MenuItemBinding

class MenuAdapter(private var menuList: ArrayList<FoodModel>, var requiredContext: Context) :
    RecyclerView.Adapter<MenuAdapter.viewHolder>() {

    private val itemClickListener: OnClickListener? = null

    inner class viewHolder(var binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(position)
                }
                val intent = Intent(requiredContext, DetailsActivity::class.java)
                intent.putExtra("menuFoodName", menuList[position].foodName)
                intent.putExtra("menuFoodImg", menuList[position].foodImg)
                requiredContext.startActivity(intent)
            }
        }

        fun bind(position: Int) {
            val model = menuList[position]
            binding.menuFoodImg.setImageResource(model.foodImg)
            binding.menuFoodName.text = model.foodName
            binding.menuFoodPrice.text = model.foodPrice
            binding.menuAddToCartBtn.setOnClickListener {
                Toast.makeText(itemView.context, "Add Menu Items", Toast.LENGTH_SHORT).show();
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.viewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.viewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {

        return menuList.size
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}

