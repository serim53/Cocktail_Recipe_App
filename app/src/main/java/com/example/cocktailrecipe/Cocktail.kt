package com.example.cocktailrecipe

import com.google.gson.annotations.SerializedName

data class Cocktail(
    @SerializedName("drinks")
    val drinks: List<Drink>?
)

data class Drink(
    @SerializedName("strDrink")
    val name: String,
    @SerializedName("strInstructions")
    val recipe: String,
    @SerializedName("strDrinkThumb")
    var image: String
)
