package com.example.wavesoffood.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.RecentBuyItemBinding

class RecentBuyAdapter(
    private var context: Context,
    private var foodNameList: ArrayList<String>,
    private var foodPriceList: ArrayList<String>,
    private var foodImgList: ArrayList<String>,
    private var foodQtyList: ArrayList<Int>,

    ) : RecyclerView.Adapter<RecentBuyAdapter.viewHolder>() {


    inner class viewHolder(var binding: RecentBuyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodName.text = foodNameList[position]
                foodPrice.text = foodPriceList[position]
                foodQty.text = foodQtyList[position].toString()
                val uriString = foodImgList[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(foodImg)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = RecentBuyItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(position)

    }

    override fun getItemCount(): Int {
        return foodNameList.size
    }
}