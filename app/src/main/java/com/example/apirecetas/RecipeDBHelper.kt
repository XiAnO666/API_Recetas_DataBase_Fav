package com.example.apirecetas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecipeDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "recipe.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_FAVORITE_RECIPE_TABLE = "CREATE TABLE ${FavoriteRecipe.TABLE_NAME}(" +
                "${FavoriteRecipe.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${FavoriteRecipe.COLUMN_TITLE} TEXT," +
                "${FavoriteRecipe.COLUMN_IMAGE} TEXT)"
        db.execSQL(CREATE_FAVORITE_RECIPE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${FavoriteRecipe.TABLE_NAME}")
        onCreate(db)
    }
}
