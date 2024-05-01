package com.example.apirecetas

import android.content.ContentValues
import android.content.Context
class FavoriteRecipeDAO(context: Context) {

    private val dbHelper = RecipeDBHelper(context)

    fun getAll(): List<FavoriteRecipe> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(FavoriteRecipe.TABLE_NAME, null, null, null, null, null, null)
        val recipes = mutableListOf<FavoriteRecipe>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(FavoriteRecipe.COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(FavoriteRecipe.COLUMN_TITLE))
                val image = getString(getColumnIndexOrThrow(FavoriteRecipe.COLUMN_IMAGE))
                recipes.add(FavoriteRecipe(id, title, image))
            }
        }
        return recipes
    }

    fun getRecipeById(id: Int): FavoriteRecipe? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(FavoriteRecipe.TABLE_NAME, null, "${FavoriteRecipe.COLUMN_ID} = ?", arrayOf(id.toString()), null, null, null)
        return with(cursor) {
            when {
                moveToFirst() -> FavoriteRecipe(getInt(getColumnIndexOrThrow(FavoriteRecipe.COLUMN_ID)), getString(getColumnIndexOrThrow(FavoriteRecipe.COLUMN_TITLE)), getString(getColumnIndexOrThrow(FavoriteRecipe.COLUMN_IMAGE)))
                else -> null
            }
        }
    }

    fun insert(recipe: FavoriteRecipe) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(FavoriteRecipe.COLUMN_ID, recipe.id)
            put(FavoriteRecipe.COLUMN_TITLE, recipe.title)
            put(FavoriteRecipe.COLUMN_IMAGE, recipe.image)
        }
        db.insert(FavoriteRecipe.TABLE_NAME, null, values)
    }

    fun delete(recipe: FavoriteRecipe) {
        val db = dbHelper.writableDatabase
        val selection = "${FavoriteRecipe.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(recipe.id.toString())
        db.delete(FavoriteRecipe.TABLE_NAME, selection, selectionArgs)
    }
}

