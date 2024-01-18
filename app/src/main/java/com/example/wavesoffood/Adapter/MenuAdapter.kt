package com.example.wavesoffood.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.DetailsActivity
import com.example.wavesoffood.databinding.MenuItemBinding

class MenuAdapter(private var menuList: MutableList<FoodModel>, var requiredContext: Context) :
    RecyclerView.Adapter<MenuAdapter.viewHolder>() {
    inner class viewHolder(var binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem = menuList[position]
            // a intent to open details Activity and pass data
            val intent = Intent(requiredContext, DetailsActivity::class.java).apply {
                putExtra("menuItemName", menuItem.foodName)
                putExtra("menuItemPrice", menuItem.foodPrice)
                putExtra("menuItemDes", menuItem.foodDescription)
                putExtra("menuItemIngredients", menuItem.foodIngredients)
                putExtra("menuItemImg", menuItem.foodImg)
            }
            //start the details activity
            requiredContext.startActivity(intent)
        }

        //set data into recyclerView name, price, image
        fun bind(position: Int) {
            val model = menuList[position]
            binding.menuFoodName.text = model.foodName
            binding.menuFoodPrice.text = model.foodPrice

            val uri = Uri.parse(model.foodImg)
            Glide.with(requiredContext).load(uri).into(binding.menuFoodImg)

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
}

