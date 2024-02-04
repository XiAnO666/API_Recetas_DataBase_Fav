package com.example.apirecetas

import com.google.gson.annotations.SerializedName

data class RecipeDetail(
    val id: Int,
    val title: String,
    val image: String,
    val sourceUrl: String,
    @SerializedName("extendedIngredients")
    val ingredients: List<Ingredient>,
    val instructions: String
)

data class Ingredient(
    val name: String,
)

