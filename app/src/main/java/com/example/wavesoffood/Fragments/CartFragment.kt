package com.example.wavesoffood.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.CartAdapter
import com.example.wavesoffood.DataClass.CartItems
import com.example.wavesoffood.PayOutActivity
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentCartBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
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

    //    private lateinit var cartLists: MutableList<CartItems>
    private lateinit var cartFoodNames: MutableList<String>
    private lateinit var cartFoodPrices: MutableList<String>
    private lateinit var cartFoodDescription: MutableList<String>
    private lateinit var cartFoodImages: MutableList<String>
    private lateinit var cartFoodQuantities: MutableList<Int>
    private lateinit var cartFoodIngredients: MutableList<String>
    private lateinit var cartAdapter:CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader()
        binding.loader.visibility = View.VISIBLE
        retrieveCartItems()
    }

    private fun loader() {
        val loader = binding.loader as ProgressBar
        val circle: Sprite = Circle()
        loader.indeterminateDrawable = circle
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        //database reference to the firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""

        binding.proceedBtn.isEnabled = false
        binding.proceedBtn.setOnClickListener {
            //get order items details before proceeding to checkout
            getOrderItemsDetails()
            binding.proceedBtn.setBackgroundResource(R.drawable.un_shape)
            Toast.makeText(context, "Proceed to pay", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun getOrderItemsDetails() {
        val orderIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")
//        cartLists = mutableListOf<CartItems>()
        val foodNames = mutableListOf<String>()
        val foodPrices = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodImages = mutableListOf<String>()
        val foodIngredients = mutableListOf<String>()

        //get items Quantities
        val foodQuantities = cartAdapter.getUpdatedItemQuantities()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    //get the cartItems to respective list
                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    //add items details in to list
//                    orderItems?.let { cartLists.add(it) }
                    orderItems?.cartFoodName?.let { foodNames.add(it) }
                    orderItems?.cartFoodPrice?.let { foodPrices.add(it) }
                    orderItems?.cartFoodDes?.let { foodDescription.add(it) }
                    orderItems?.cartFoodImg?.let { foodImages.add(it) }
                    orderItems?.cartFoodQuantity?.let { foodQuantities.add(it) }
                    orderItems?.cartFoodIngredient?.let { foodIngredients.add(it) }
                }
                orderNow(
                    foodNames,
                    foodPrices,
                    foodDescription,
                    foodImages,
                    foodIngredients,
                    foodQuantities
                )
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

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDes: MutableList<String>,
        foodImg: MutableList<String>,
        foodIngredient: MutableList<String>,
        foodQty: MutableList<Int>,
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("fName", foodName as ArrayList<String>)
            intent.putExtra("fPrice", foodPrice as ArrayList<String>)
            intent.putExtra("fDes", foodDes as ArrayList<String>)
            intent.putExtra("fImg", foodImg as ArrayList<String>)
            intent.putExtra("fIngredients", foodIngredient as ArrayList<String>)
            intent.putExtra("fQty", foodQty as ArrayList<Int>)
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {
        val foodReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        //list to store cart Items
        cartFoodNames = mutableListOf()
        cartFoodPrices = mutableListOf()
        cartFoodDescription = mutableListOf()
        cartFoodImages = mutableListOf()
        cartFoodQuantities = mutableListOf()
        cartFoodIngredients = mutableListOf()

        //fetch data from the database
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    //get the cartItems object from the child node
                    val cartItems = foodSnapshot.getValue(CartItems::class.java)
                    //add cartItem data to the list
//                    cartItems?.let { cartLists.add(it) }
                    cartItems?.cartFoodName?.let { cartFoodNames.add(it) }
                    cartItems?.cartFoodPrice?.let { cartFoodPrices.add(it) }
                    cartItems?.cartFoodDes?.let { cartFoodDescription.add(it) }
                    cartItems?.cartFoodImg?.let { cartFoodImages.add(it) }
                    cartItems?.cartFoodQuantity?.let { cartFoodQuantities.add(it) }
                    cartItems?.cartFoodIngredient?.let { cartFoodIngredients.add(it) }

                }
                //set the list items to adapter

                //check the cartItems is empty or not
                if (cartFoodNames.isEmpty()) {
                    binding.rvCart.visibility = View.GONE
                    binding.emptyCart.visibility = View.VISIBLE
                    binding.loader.visibility = View.GONE
                } else {
                    binding.emptyCart.visibility = View.GONE
                    binding.proceedBtn.isEnabled = true
                    setAdapter(
                        cartFoodNames,
                        cartFoodPrices,
                        cartFoodDescription,
                        cartFoodImages,
                        cartFoodQuantities,
                        cartFoodIngredients
                    )
                }

            }

            fun setAdapter(
                foodName: MutableList<String>,
                foodPrice: MutableList<String>,
                foodDes: MutableList<String>,
                foodImg: MutableList<String>,
                foodQty: MutableList<Int>,
                foodIngredients: MutableList<String>
            ) {
                if (isAdded && context != null) {
                    binding.loader.visibility = View.GONE
                    cartAdapter = CartAdapter(
                        requireContext(),
                        foodName,
                        foodPrice,
                        foodDes,
                        foodImg,
                        foodQty,
                        foodIngredients
                    )

                    binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvCart.adapter = cartAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not Empty here", Toast.LENGTH_SHORT).show()
            }
        })
    }
}