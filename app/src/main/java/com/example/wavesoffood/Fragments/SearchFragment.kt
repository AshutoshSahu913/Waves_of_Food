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

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private lateinit var adapter: MenuAdapter
    private var originalList = ArrayList<FoodModel>()
    private var filteredList = ArrayList<FoodModel>()
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
//        originalList.add(FoodModel(R.drawable.menu1, "Donuts", "₹ 99"))
//        originalList.add(FoodModel(R.drawable.menu2, "Salad", "₹ 69"))
//        originalList.add(FoodModel(R.drawable.menu3, "Ice Cream", "₹ 49"))
//        originalList.add(FoodModel(R.drawable.menu4, "Soop", "₹ 69"))
//        originalList.add(FoodModel(R.drawable.menu5, "Pasta", "₹ 99"))
//        originalList.add(FoodModel(R.drawable.menu6, "Rools", "₹ 59"))
//        originalList.add(FoodModel(R.drawable.menu7, "Fruits", "₹ 99"))
//        originalList.add(FoodModel(R.drawable.menu1, "Donuts", "₹ 99"))

        filteredList.addAll(originalList)

        adapter = MenuAdapter(filteredList,requireContext())
        binding.rvSearchMenu.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchMenu.adapter = adapter

        // show search items
        setUpSearchView()

        // show all menu items
        showAllMenu()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showAllMenu() {
        // Clear the filteredList and add all items from the originalList
        filteredList.clear()
        filteredList.addAll(originalList)
        adapter.notifyDataSetChanged()
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
        filteredList.clear()

        //Add matching items to the filteredList
//        originalList.forEach { foodModel ->
//            if (foodModel.foodName.contains(query.toString().orEmpty(), ignoreCase = true)) {
//                filteredList.add(foodModel)
//            }
//        }
        // Update the adapter with the filteredList
        adapter.notifyDataSetChanged()
    }
}