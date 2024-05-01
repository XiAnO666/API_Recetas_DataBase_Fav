package com.example.apirecetas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteRecipeDAO {
    @Query("SELECT * FROM favorite_recipes")
    suspend fun getAll(): List<FavoriteRecipe>

    @Query("SELECT * FROM favorite_recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): FavoriteRecipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: FavoriteRecipe)

    @Delete
    suspend fun delete(recipe: FavoriteRecipe)
}
