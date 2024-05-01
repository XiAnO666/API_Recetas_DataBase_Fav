package com.example.apirecetas

data class FavoriteRecipe(val id: Int, val title: String, val image: String) {
    companion object {
        const val TABLE_NAME = "favorite_recipes"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_IMAGE = "image"
    }
}
