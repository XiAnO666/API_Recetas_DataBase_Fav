package com.example.apirecetas


import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.apirecetas.databinding.ItemRecipeBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class RecipeViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemRecipeBinding.bind(view)

    fun bind(recipe: Recipe){
        binding.tvTitle.text = recipe.title

        Picasso.get().load(recipe.image).transform(RoundedCornersTransformation(30, 0)).into(binding.ivPoster)
        /*itemView.setOnClickListener {
            // Aquí puedes hacer una llamada a la API para obtener más información sobre la receta
            // Puedes usar la ID de la receta (recipe.id) para hacer esta llamada
            val context = itemView.context
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.id)
            context.startActivity(intent)
        }*/
    }
}


