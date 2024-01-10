package com.example.wavesoffood.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.wavesoffood.Adapter.NotificationAdapter
import com.example.wavesoffood.Adapter.PopularAdapter
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.DataClass.NotificationModel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        //for AllViewItems
        binding.viewAllMenuTxt.setOnClickListener {
            val bottomSheetFragment = MenuBottomSheetFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //add imageSlider is here
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {

            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }

        })
        // add recycler view food items
        val list = ArrayList<FoodModel>()
        list.add(FoodModel(R.drawable.menu1, "Donuts", "₹ 99"))
        list.add(FoodModel(R.drawable.menu2, "Salad", "₹ 69"))
        list.add(FoodModel(R.drawable.menu3, "Ice Cream", "₹ 49"))
        list.add(FoodModel(R.drawable.menu4, "Soop", "₹ 69"))
        list.add(FoodModel(R.drawable.menu5, "Pasta", "₹ 99"))
        list.add(FoodModel(R.drawable.menu6, "Rools", "₹ 59"))
        list.add(FoodModel(R.drawable.menu7, "Fruits", "₹ 99"))

        var adapter = PopularAdapter(list, requireContext())
        binding.rvPopularItem.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPopularItem.adapter = adapter


    }
}