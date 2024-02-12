package com.example.wavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.wavesoffood.databinding.ActivityLocationBinding
import com.example.wavesoffood.databinding.ActivityLoginPageBinding

class LocationActivity : AppCompatActivity() {
    private val binding: ActivityLocationBinding by lazy {
        ActivityLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locationList = arrayOf("jaipur", "Bhopal", "Lucknow", "Delhi", "Hyderabad")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()

            startActivity(Intent(this@LocationActivity, HomePage::class.java))
            // Do something with the selected item, such as displaying it in a toast or performing a task
            Toast.makeText(this, "Selected Location: $selectedItem", Toast.LENGTH_SHORT).show()
        }

    }
}