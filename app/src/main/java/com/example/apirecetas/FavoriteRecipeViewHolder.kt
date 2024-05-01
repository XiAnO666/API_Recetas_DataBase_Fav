package com.example.apirecetas

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.apirecetas.databinding.FavCardviewBinding
import com.squareup.picasso.Picasso

class FavoriteRecipeViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = FavCardviewBinding.bind(view)

    fun bind(favoriteRecipe: FavoriteRecipe){
        binding.tvTitle.text = favoriteRecipe.title
        Picasso.get().load(favoriteRecipe.image).into(binding.ivRecipe)
    }
}
