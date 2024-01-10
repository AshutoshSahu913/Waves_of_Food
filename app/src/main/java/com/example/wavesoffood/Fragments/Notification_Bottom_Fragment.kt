package com.example.wavesoffood.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.NotificationAdapter
import com.example.wavesoffood.DataClass.NotificationModel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Notification_Bottom_Fragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentNotificationBottomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBottomBinding.inflate(inflater, container, false)

        // add notification adapter
        val notiList = ArrayList<NotificationModel>()
        notiList.add(
            NotificationModel(
                R.drawable.sademoji,
                "Your order has been Canceled Successfully"
            )
        )
        notiList.add(NotificationModel(R.drawable.truck, "Order has been taken by the driver"))
        notiList.add(NotificationModel(R.drawable.congrat, "Congrats Your Order Placed"))

        val adapter2 = NotificationAdapter(notiList)
        binding.rvNotification.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotification.adapter = adapter2


        return binding.root
    }

}