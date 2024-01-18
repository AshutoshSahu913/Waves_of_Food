package com.example.wavesoffood.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.BuyAgainAdapter
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryBinding

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

        setUpRecyclerView()


        return binding.root
    }

    private fun setUpRecyclerView() {
        val buyAgainList = ArrayList<FoodModel>()
//        buyAgainList.add(FoodModel(R.drawable.menu1, "Donuts", "₹ 99"))
//        buyAgainList.add(FoodModel(R.drawable.menu2, "Salad", "₹ 69"))
//        buyAgainList.add(FoodModel(R.drawable.menu3, "Ice Cream", "₹ 49"))

        val adapterHistory = BuyAgainAdapter(buyAgainList)
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapterHistory
    }
}