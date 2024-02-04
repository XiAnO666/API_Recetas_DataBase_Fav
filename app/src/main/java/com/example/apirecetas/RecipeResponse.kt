package com.example.apirecetas

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("results")
    val results: List<Recipe>
)