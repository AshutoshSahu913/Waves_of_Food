package com.example.wavesoffood.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.MenuAdapter
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.databinding.FragmentSearchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private lateinit var adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase

    private var originalMenuList = mutableListOf<FoodModel>()

    //    private var filteredList = ArrayList<FoodModel>()
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
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        //retrieve menu item from database
        retrieveMenuItem()

        // show search items
        setUpSearchView()

        return binding.root
    }

    private fun retrieveMenuItem() {
        //get database Reference
        database = FirebaseDatabase.getInstance()
        //reference to the menu Node
        val foodReference: DatabaseReference = database.reference.child("Menu")
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(FoodModel::class.java)
                    menuItem?.let {
                        originalMenuList.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    private fun showAllMenu() {
        val filteredMenuItem = ArrayList(originalMenuList)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: List<FoodModel>) {
        adapter = MenuAdapter(filteredMenuItem, requireContext())
        binding.rvSearchMenu.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchMenu.adapter = adapter

    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterMenuItems(query: String?) {
        // Clear the filteredList
        val filteredMenuItems = originalMenuList.filter {
            it.foodName?.contains(query!!, ignoreCase = true) == true
        }
        setAdapter(filteredMenuItems)
        // Update the adapter with the filteredList
        adapter.notifyDataSetChanged()
    }
}