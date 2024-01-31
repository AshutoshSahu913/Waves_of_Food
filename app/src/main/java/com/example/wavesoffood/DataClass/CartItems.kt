package com.example.wavesoffood.DataClass

data class CartItems(
    var cartFoodName: String? = null,
    var cartFoodPrice: String? = null,
    var cartFoodDes: String? = null,
    var cartFoodImg: String? = null,
    var cartFoodQuantity: Int? = null,
    var cartFoodIngredients: String? = null
)