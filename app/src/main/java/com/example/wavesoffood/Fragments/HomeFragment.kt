package com.example.wavesoffood.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.wavesoffood.Adapter.MenuAdapter
import com.example.wavesoffood.DataClass.FoodModel
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentHomeBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuList: MutableList<FoodModel>

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

        //retrieve and display popular menu items
        binding.loader1.visibility = View.VISIBLE
        loader1()
        retrieveAndDisplayPopularItems()

        return binding.root
    }

    private fun retrieveAndDisplayPopularItems() {
        //get reference to the database
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("Menu")
        menuList = mutableListOf()

        //retrieve menu items form the database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(FoodModel::class.java)
                    menuItem?.let { menuList.add(it) }
                }
                //display a random popular items
                randomPopularItems()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun randomPopularItems() {
        //CREATE AS SHUFFLED LIST OF MENU ITEMS
        val index = menuList.indices.toList().shuffled()
        val numItemToShow = 6
        val subsetMenuItem = index.take(numItemToShow).map { menuList[it] }
        setPopularItemAdapter(subsetMenuItem)
    }

    private fun setPopularItemAdapter(subsetMenuItems: List<FoodModel>) {
        val adapter = MenuAdapter(subsetMenuItems, requireContext())
        binding.loader1.visibility = View.GONE
        binding.rvPopularItem.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPopularItem.adapter = adapter
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
    }
    fun loader1() {
        // code for loader
        val progressBar = binding.loader1 as ProgressBar
        val circle: Sprite = Circle()
        progressBar.indeterminateDrawable = circle
    }
}