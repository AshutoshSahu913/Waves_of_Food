package com.example.wavesoffood.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wavesoffood.Adapter.BuyAgainAdapter
import com.example.wavesoffood.DataClass.CartItems
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.DataClass.OrderDetails
import com.example.wavesoffood.R
import com.example.wavesoffood.RecentOrderItem
import com.example.wavesoffood.databinding.FragmentHistoryBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
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

        binding.recentBuyItemCart.setOnClickListener {
            seeItemRecentBuy()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Retrieve and display the user order history
        loader2()
        binding.loader2.visibility = View.VISIBLE
        retrieveBuyHistory()
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
                    binding.emptyTxt.visibility = View.GONE

                } else {
                    binding.emptyTxt.visibility = View.VISIBLE
                    binding.recentBuyItemLayout.visibility = View.GONE

                }
//                if (listOfOrderItem.isNotEmpty()) {
                setDataInRecentBuyItem()
                setPreviousBuyItemsRecyclerView()

//                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    @SuppressLint("ResourceAsColor")
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
//                listOfOrderItem.reverse()

                val isOrderAccepted = listOfOrderItem[0].orderAccepted
                if (isOrderAccepted) {
                    orderStatus.setCardBackgroundColor(R.color.appColor)
                    receivedBtn.visibility = View.VISIBLE
                } else {
                    orderStatus.setCardBackgroundColor(R.color.defaultColor)
                    receivedBtn.visibility = View.GONE
                }
                receivedBtn.setOnClickListener {
                    receivedBtn.setBackgroundResource(R.drawable.un_shape)
                    updateOrderStatus()
                }

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
        binding.loader2.visibility = View.GONE
        buyAgainAdapter =
            BuyAgainAdapter(buyAgainFoodName, buyAgainFoodPrice, buyAgainFoodImg, requireContext())
        rv.adapter = buyAgainAdapter
    }

    private fun updateOrderStatus() {
        val itemPushKey = listOfOrderItem[0].itemPushKey
        val completeOrderReference = database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentReceived").setValue(true)
    }

    //function to see items recent buy
    private fun seeItemRecentBuy() {
        listOfOrderItem.firstOrNull()?.let {
            val intent = Intent(requireContext(), RecentOrderItem::class.java)
            intent.putExtra("RecentBuyOrderItem", listOfOrderItem)
            startActivity(intent)
        }
    }

    fun loader2() {
        // code for loader
        val progressBar = binding.loader2 as ProgressBar
        val circle: Sprite = Circle()
        progressBar.indeterminateDrawable = circle
    }
}