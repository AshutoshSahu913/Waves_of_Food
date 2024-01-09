package com.example.wavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.wavesoffood.Fragments.MenuBottomSheetFragment
import com.example.wavesoffood.Fragments.Notification_Bottom_Fragment
import com.example.wavesoffood.databinding.ActivityHomePageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {
    val binding: ActivityHomePageBinding by lazy {
        ActivityHomePageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNav, navController)

        binding.notificationBtn.setOnClickListener {
            val bottomSheet = Notification_Bottom_Fragment()
            bottomSheet.show(supportFragmentManager, "Text")
        }


    }
}