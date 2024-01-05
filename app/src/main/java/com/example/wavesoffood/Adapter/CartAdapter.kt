package com.example.wavesoffood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.databinding.CartItemBinding

class CartAdapter(private var cartList: MutableList<FoodModel>) :
    RecyclerView.Adapter<CartAdapter.viewHolder>() {
private val itemQuntities=IntArray(cartList.size){1}
    inner class viewHolder(var binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                val quantity=itemQuantity
                itemQuantity.text=quantity.toString()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.viewHolder {
        val binding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.viewHolder, position: Int) {
        var model = cartList.get(position)
        holder.binding.cartFoodImg.setImageResource(model.foodImg)
        holder.binding.cartFoodName.text = model.foodName
        holder.binding.cartFoodPrice.text = model.foodPrice



    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}