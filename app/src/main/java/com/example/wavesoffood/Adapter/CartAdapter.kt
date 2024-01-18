package com.example.wavesoffood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.databinding.CartItemBinding

class CartAdapter(private var cartList: ArrayList<FoodModel>) :
    RecyclerView.Adapter<CartAdapter.viewHolder>() {
    private val itemQuantities = IntArray(cartList.size) { 1 }

    inner class viewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            val quantity = itemQuantities[position]
            binding.itemQuantity.text = quantity.toString()
            binding.minusBtn.setOnClickListener {
                deceaseQuantity(position)
            }
            binding.plusBtn.setOnClickListener {
                increaseQuantity(position)

            }
            binding.deleteBtn.setOnClickListener {
                val itemPosition = adapterPosition
                if (itemPosition != RecyclerView.NO_POSITION) {
                    deleteItems(itemPosition)
                }
            }
        }

        private fun deceaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.itemQuantity.text = itemQuantities[position].toString()

            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.itemQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun deleteItems(position: Int) {
            cartList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartList.size)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.viewHolder {
        val binding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.viewHolder, position: Int) {
        holder.bind(position)
        var model = cartList.get(position)
//        holder.binding.cartFoodImg.setImageResource(model.foodImg)
        holder.binding.cartFoodName.text = model.foodName
        holder.binding.cartFoodPrice.text = model.foodPrice

    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}