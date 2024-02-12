package com.example.wavesoffood.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.MenuAdapter
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.databinding.FragmentMenuBottomSheetBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuLists: MutableList<FoodModel>
//    private lateinit var adapterMenu: MenuAdapter

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
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        binding.backBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader2()
        binding.loader2.visibility = View.VISIBLE
        setUpRecyclerView()
        setAdapter()
    }

    fun loader2() {
        // code for loader
        val progressBar = binding.loader2 as ProgressBar
        val circle: Sprite = Circle()
        progressBar.indeterminateDrawable = circle
    }

    private fun setUpRecyclerView() {
        // add recycler view food items in bottom sheet drawable
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("Menu")
        menuLists = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(FoodModel::class.java)
                    menuItem?.let { menuLists.add(it) }
                }
//                Log.d("ITEMS", "onDataChange : Data Received $menuList")
                // once data receive , set to adapter
                setAdapter()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun setAdapter() {
        if (menuLists.isNotEmpty()) {
            binding.loader2.visibility = View.GONE
            val adapter = MenuAdapter(menuLists, requireContext())
            binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
            binding.rvMenu.adapter = adapter
            Log.d("ITEMS", "setAdapter: data set")
        } else {
            Log.d("ITEMS", "setAdapter: data not set")
        }

    }
}