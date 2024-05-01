package com.example.apirecetas

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoriteRecipeAdapter(private val favoriteRecipeList: List<FavoriteRecipe>) : RecyclerView.Adapter<FavoriteRecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoriteRecipeViewHolder(layoutInflater.inflate(R.layout.fav_cardview, parent, false))
    }

    override fun getItemCount(): Int {
        return favoriteRecipeList.size
    }

    override fun onBindViewHolder(holder: FavoriteRecipeViewHolder, position: Int) {
        val item = favoriteRecipeList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java)
            intent.putExtra("recipeId", item.id)
            holder.itemView.context.startActivity(intent)
        }
    }
}
