package com.example.wavesoffood.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.CartAdapter
import com.example.wavesoffood.DataClass.CartItems
import com.example.wavesoffood.PayOutActivity
import com.example.wavesoffood.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private lateinit var database: FirebaseDatabase
    private lateinit var cartLists: MutableList<CartItems>
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrieveCartItems()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.proceedBtn.setOnClickListener {
            //get order items details before proceeding to checkout
            getOrderItemsDetails()
            val intent = Intent(requireContext(), PayOutActivity::class.java)

            startActivity(intent)

        }
        return binding.root
    }

    private fun getOrderItemsDetails() {
        val orderIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")
        val cartItems = mutableListOf<CartItems>()

        //get items Quantities
        val foodQuantities = cartAdapter.getUpdatedItemQuantities()


        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (foodSnapshot in snapshot.children) {
                    //get the cartItems to respective list
                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    //add items details in to list
                    orderItems?.let { cartLists.add(it) }
                }
                orderNow(cartLists)
            }

            private fun orderNow(cartLists: MutableList<CartItems>) {
                if (isAdded && context!=null){
                    val intent=Intent(requireContext(),PayOutActivity::class.java)
                    intent.putExtra("foodItems",cartLists as ArrayList<CartItems>)
                    startActivity(intent)
                }




            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(
                    requireContext(),
                    "Order making Failed Please Try Again Later!",
                    Toast.LENGTH_SHORT
                ).show()

            }

        })


    }

    private fun retrieveCartItems() {
        //database reference to the firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        //list to store cart Items
        cartLists = mutableListOf()

        //fetch data from the database
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    //get the cartItems object from the child node
                    val cartItems = foodSnapshot.getValue(CartItems::class.java)
                    //add cartItem data to the list
                    cartItems?.let { cartLists.add(it) }
                }
                //set the list items to adapter
                setAdapter(cartLists)
            }

            fun setAdapter(cartList: MutableList<CartItems>) {
                cartAdapter = CartAdapter(cartList, requireContext())
                binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
                binding.rvCart.adapter = cartAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not Empty here", Toast.LENGTH_SHORT).show()
            }
        })
    }
}