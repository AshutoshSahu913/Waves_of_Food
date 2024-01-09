package com.example.wavesoffood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.DataClass.NotificationModel
import com.example.wavesoffood.databinding.FragmentNotificationBottomBinding
import com.example.wavesoffood.databinding.NotificationItemBinding

class NotificationAdapter(var notificationList: ArrayList<NotificationModel>) :
    RecyclerView.Adapter<NotificationAdapter.viewHolder>() {

    inner class viewHolder(var binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.viewHolder {
        val binding =
            NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.viewHolder, position: Int) {
        val model = notificationList[position]
        holder.binding.notificationImg.setImageResource(model.notificationImg)
        holder.binding.notificationTxt.text = model.notificationTxt
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
}