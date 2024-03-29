package com.example.wavesoffood.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.DetailsActivity
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.MenuItemBinding

class MenuAdapter(private var menuList: List<FoodModel>, var context: Context) :
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
            val intent = Intent(context,
                DetailsActivity::class.java).apply {
                putExtra("menuItemName", menuItem.foodName)
                putExtra("menuItemPrice", menuItem.foodPrice)
                putExtra("menuItemDes", menuItem.foodDes)
                putExtra("menuItemIngredients", menuItem.foodIngredients)
                putExtra("menuItemImg", menuItem.foodImg)
            }
            //start the details activity
            context.startActivity(intent)
        }

        //set data into recyclerView name, price, image
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val model = menuList[position]
            binding.menuFoodName.text = model.foodName
            binding.menuFoodPrice.text = "₹ ${model.foodPrice}"

            val uri = Uri.parse(model.foodImg)
            Glide.with(context).load(uri).into(binding.menuFoodImg)

            binding.menuAddToCartBtn.setOnClickListener {
                binding.menuAddToCartBtn.setBackgroundResource(R.drawable.un_shape)
                Toast.makeText(itemView.context, "Add Item To Cart", Toast.LENGTH_SHORT).show();
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

