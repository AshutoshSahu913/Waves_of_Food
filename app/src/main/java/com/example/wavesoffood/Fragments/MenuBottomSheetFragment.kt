package com.example.wavesoffood.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.BuyAgainAdapter
import com.example.wavesoffood.Adapter.MenuAdapter
import com.example.wavesoffood.Adapter.PopularAdapter
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBottomSheetBinding

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        binding.backBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun setUpRecyclerView() {

        // add recycler view food items in bottom sheet drawable
        val listMenu = ArrayList<FoodModel>()
        listMenu.add(FoodModel(R.drawable.menu1, "Donuts", "₹ 99"))
        listMenu.add(FoodModel(R.drawable.menu2, "Salad", "₹ 69"))
        listMenu.add(FoodModel(R.drawable.menu3, "Ice Cream", "₹ 49"))
        listMenu.add(FoodModel(R.drawable.menu4, "Soop", "₹ 69"))
        listMenu.add(FoodModel(R.drawable.menu5, "Pasta", "₹ 99"))
        listMenu.add(FoodModel(R.drawable.menu6, "Rools", "₹ 59"))
        listMenu.add(FoodModel(R.drawable.menu7, "Fruits", "₹ 99"))

        var adapter = MenuAdapter(listMenu, requireContext())
        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMenu.adapter = adapter
    }

}