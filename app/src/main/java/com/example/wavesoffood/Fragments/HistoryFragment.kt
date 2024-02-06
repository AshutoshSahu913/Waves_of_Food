package com.example.wavesoffood.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wavesoffood.Adapter.BuyAgainAdapter
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.DataClass.OrderDetails
import com.example.wavesoffood.RecentOrderItem
import com.example.wavesoffood.databinding.FragmentHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()
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
        binding = FragmentHistoryBinding.inflate(inflater, container, false)


        //initialize firebase auth
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        //Retrieve and display the user order history
        retrieveBuyHistory()

        binding.recentBuyItemCart.setOnClickListener {
            seeItemRecentBuy()
        }


        return binding.root
    }


    //function to see items recent buy
    private fun seeItemRecentBuy() {
        listOfOrderItem.firstOrNull()?.let {
            var intent = Intent(requireContext(), RecentOrderItem::class.java)
            intent.putExtra("RecentBuyOrderItem", listOfOrderItem)
            startActivity(intent)
        }
    }

    private fun retrieveBuyHistory() {
        binding.recentBuyItemLayout.visibility = View.GONE
        userId = auth.currentUser?.uid ?: ""

        val buyItemReference: DatabaseReference =
            database.reference.child("user").child(userId).child("BuyHistory")

        val shortingQuery = buyItemReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {
                    setDataInRecentBuyItem()
                    setPreviousBuyItemsRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun setDataInRecentBuyItem() {
        binding.recentBuyItemLayout.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                rFoodName.text = it.foodNames?.firstOrNull() ?: ""
                rFoodPrice.text = it.foodPrices?.firstOrNull() ?: ""
                val img = it.foodImages?.firstOrNull() ?: ""
                val uri = Uri.parse(img)
                Glide.with(requireContext()).load(uri).into(rFoodImg)
                listOfOrderItem.reverse()
//                if (listOfOrderItem.isNotEmpty()) {
//                }
            }
        }
    }

    private fun setPreviousBuyItemsRecyclerView() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrice = mutableListOf<String>()
        val buyAgainFoodImg = mutableListOf<String>()

        for (i in 1 until listOfOrderItem.size) {
            listOfOrderItem[i].foodNames?.firstOrNull()?.let { buyAgainFoodName.add(it) }
            listOfOrderItem[i].foodPrices?.firstOrNull()?.let { buyAgainFoodPrice.add(it) }
            listOfOrderItem[i].foodImages?.firstOrNull()?.let { buyAgainFoodImg.add(it) }
        }
        val rv = binding.rvHistory
        rv.layoutManager = LinearLayoutManager(requireContext())
        buyAgainAdapter =
            BuyAgainAdapter(buyAgainFoodName, buyAgainFoodPrice, buyAgainFoodImg, requireContext())
        rv.adapter = buyAgainAdapter
    }

}