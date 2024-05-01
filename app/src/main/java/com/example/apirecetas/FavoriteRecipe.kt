package com.example.apirecetas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavoriteRecipe(
    @PrimaryKey val id: Int,
    val title: String,
    val image: String
)
