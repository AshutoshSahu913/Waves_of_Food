package com.example.wavesoffood.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.MenuAdapter
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.databinding.FragmentSearchBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
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
        loader2()
        binding.loader2.visibility = View.VISIBLE
        retrieveMenuItem()

        showAllMenu()
        // show search items
        setUpSearchView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAllMenu()
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
                // Move setAdapter here after data is processed
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun showAllMenu() {
        val filteredMenuItem = ArrayList(originalMenuList)
        binding.loader2.visibility = View.GONE
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

    private fun filterMenuItems(query: String?) {
        // Clear the filteredList
        val filteredMenuItems = originalMenuList.filter {
            it.foodName?.contains(query!!, ignoreCase = true) == true
        }
        //Set Adapter
        setAdapter(filteredMenuItems)
    }

    fun loader2() {
        // code for loader
        val progressBar = binding.loader2 as ProgressBar
        val circle: Sprite = Circle()
        progressBar.indeterminateDrawable = circle
    }
}