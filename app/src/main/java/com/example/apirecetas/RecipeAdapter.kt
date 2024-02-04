package com.example.apirecetas


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecipeAdapter(private val recipeList: List<Recipe>) : RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecipeViewHolder(layoutInflater.inflate(R.layout.item_recipe, parent, false))
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = recipeList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            Log.d("RecipeAdapter", "Recipe Clicked: ${item.id}")
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java)
            intent.putExtra("recipeId", item.id)
            holder.itemView.context.startActivity(intent)
        }

    }
}
