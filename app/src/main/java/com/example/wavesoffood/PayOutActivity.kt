package com.example.wavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wavesoffood.DataClass.OrderDetails
import com.example.wavesoffood.databinding.ActivityPayOutActitvityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayOutActivity : AppCompatActivity() {
    val binding: ActivityPayOutActitvityBinding by lazy {
        ActivityPayOutActitvityBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var totalAmount: String
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String
    private lateinit var fName: ArrayList<String>
    private lateinit var fPrice: ArrayList<String>
    private lateinit var fImg: ArrayList<String>
    private lateinit var fDes: ArrayList<String>
    private lateinit var fIngredients: ArrayList<String>
    private lateinit var fQty: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //initialize firebase and user details
        auth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().reference
        //set user data
        setUserData()

        //get user Details form Firebase
        val intent = intent
        fName = intent.getStringArrayListExtra("fName") as ArrayList<String>
        fPrice = intent.getStringArrayListExtra("fPrice") as ArrayList<String>
        fDes = intent.getStringArrayListExtra("fDes") as ArrayList<String>
        fImg = intent.getStringArrayListExtra("fImg") as ArrayList<String>
        fIngredients = intent.getStringArrayListExtra("fIngredients") as ArrayList<String>
        fQty = intent.getIntegerArrayListExtra("fQty") as ArrayList<Int>


        totalAmount = calculateTotalAmount().toString() + "₹"
        binding.payOutTotalPrice.text = totalAmount


        binding.placeOrderBtn.setOnClickListener {
            //get data from text view
            name = binding.payOutEdName.text.toString().trim()
            address = binding.payOutEdAddress.text.toString().trim()
            phone = binding.payOutEdPhone.text.toString().trim()

            if (name.isBlank() && address.isBlank() && phone.isBlank()) {
                Toast.makeText(this, "Please Enter All the Details ", Toast.LENGTH_SHORT).show()
            } else {
                placeHolder()
            }
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun placeHolder() {
        userId = auth.currentUser?.uid ?: ""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetails").push().key
        val orderDetails = OrderDetails(
            userId,
            name,
            fName,
            fPrice,
            fImg,
            fQty,
            address,
            totalAmount,
            phone,
            time,
            itemPushKey,
            b=false,
            b1 = false
        )
        val orderReference = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratsFragment()
            bottomSheetDialog.show(supportFragmentManager, "Test")
            removeItemFromCart()
            addOrderToHistory(orderDetails)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed To Order 🤡", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
        databaseReference.child("user").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }
    }

    private fun removeItemFromCart() {
        val cartItemReference = databaseReference.child("user").child(userId).child("CartItems")
        cartItemReference.removeValue()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until fPrice.size) {

            val price = fPrice[i]
            val lastChar = price.last()
            val priceIntValue = if (lastChar == '₹') {
                price.dropLast(1).toInt()
            } else {
                price.toInt()
            }

            val quantity = fQty[i]
            totalAmount += priceIntValue * quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            userId = user.uid
            val userReference = databaseReference.child("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val names = snapshot.child("name").getValue(String::class.java) ?: ""
                        val addresses = snapshot.child("address").getValue(String::class.java) ?: ""
                        val phones = snapshot.child("phone").getValue(String::class.java) ?: ""

                        binding.apply {
                            payOutEdName.setText(names)
                            payOutEdAddress.setText(addresses)
                            payOutEdPhone.setText(phones)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
//        val cartItems = intent.getStringExtra("cartItems")
    }
}