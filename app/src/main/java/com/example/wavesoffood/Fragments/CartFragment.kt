package com.example.wavesoffood.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.CartAdapter
import com.example.wavesoffood.Adapter.PopularAdapter
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)

        // add recycler view food items
        val list = ArrayList<FoodModel>()
        list.add(FoodModel(R.drawable.menu1, "Donuts", "₹ 99"))
        list.add(FoodModel(R.drawable.menu2, "Salad", "₹ 69"))
        list.add(FoodModel(R.drawable.menu3, "Ice Cream", "₹ 49"))
        list.add(FoodModel(R.drawable.menu4, "Soop", "₹ 69"))
        list.add(FoodModel(R.drawable.menu5, "Pasta", "₹ 99"))
        list.add(FoodModel(R.drawable.menu6, "Rools", "₹ 59"))
        list.add(FoodModel(R.drawable.menu7, "Fruits", "₹ 99"))

        var adapter = CartAdapter(list)
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = adapter


        return binding.root
    }

}