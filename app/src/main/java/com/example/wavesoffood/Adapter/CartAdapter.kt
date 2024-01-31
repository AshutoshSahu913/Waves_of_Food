package com.example.wavesoffood.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.DataClass.CartItems
import com.example.wavesoffood.databinding.CartItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(var cartList: MutableList<CartItems>, var context: Context) :
    RecyclerView.Adapter<CartAdapter.viewHolder>() {

    //Instance Firebase Auth
    private val auth = FirebaseAuth.getInstance()

    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid ?: ""
        val cartItemNumber = cartList.size

        itemQuantities = IntArray(cartItemNumber) { 1 }
        cartItemReference = database.reference.child("user").child(userId).child("CartItems")
    }

    companion object {
        private var itemQuantities: IntArray = intArrayOf()
        private lateinit var cartItemReference: DatabaseReference
    }

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

            val positionRetrieve = position
            getUniqueKeyAtPosition(positionRetrieve) { uniqueKey ->
                if (uniqueKey != null) {
                    removeItem(position, uniqueKey)
                }
            }
        }

    }

    private fun removeItem(position: Int, uniqueKey: String) {
        if (uniqueKey != null) {
            cartItemReference.child(uniqueKey).removeValue().addOnSuccessListener {
                cartList.removeAt(position)
                Toast.makeText(context, "Item Removed Successfully", Toast.LENGTH_SHORT).show()
                //update itemQuantities
                itemQuantities =
                    itemQuantities.filterIndexed { index, i -> index != position }.toIntArray()
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, cartList.size)
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getUniqueKeyAtPosition(positionRetrieve: Int, onComplete: (String?) -> Unit) {
        cartItemReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var uniqueKey: String? = null
                //loop for snapshot children
                snapshot.children.forEachIndexed { index, dataSnapshot ->
                    if (index == positionRetrieve) {
                        uniqueKey = dataSnapshot.key
                        return@forEachIndexed
                    }
                    onComplete(uniqueKey)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.viewHolder {
        val binding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.viewHolder, position: Int) {
        holder.bind(position)
        var model = cartList[position]
        holder.binding.cartFoodName.text = model.cartFoodName
        holder.binding.cartFoodPrice.text = model.cartFoodPrice

        //load image using Glide
        val uriString = model.cartFoodImg
        val uri = Uri.parse(uriString)
        Glide.with(context).load(uri).into(holder.binding.cartFoodImg)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }



    fun getUpdatedItemQuantities(position: Int): MutableList<Int> {
        val itemQuantity = mutableListOf<Int>()

        //there is problem how to Fetch cartFoodQuantity in cartList and how to add int the
        val cartQuantity=cartList[position]
        itemQuantity.add(cartQuantity.cartFoodQuantity!!)
        return itemQuantity
    }
}