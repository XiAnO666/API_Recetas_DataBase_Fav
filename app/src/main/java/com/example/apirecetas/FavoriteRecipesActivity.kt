package com.example.apirecetas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apirecetas.databinding.FavCardviewBinding
import com.example.apirecetas.databinding.FavRecyclerviewBinding
import kotlinx.coroutines.launch

class FavoriteRecipesActivity : AppCompatActivity() {

    private lateinit var binding: FavRecyclerviewBinding
    private lateinit var adapter: FavoriteRecipeAdapter
    private val favoriteRecipeList = mutableListOf<FavoriteRecipe>()
    private val dao by lazy { AppDatabase.getDatabase(this).favoriteRecipeDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FavRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView(){
        adapter = FavoriteRecipeAdapter(favoriteRecipeList)
        binding.rvRecipes.layoutManager = LinearLayoutManager(this)
        binding.rvRecipes.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        updateFavoriteRecipes()
    }

    private fun updateFavoriteRecipes() {
        lifecycleScope.launch {
            favoriteRecipeList.clear()
            favoriteRecipeList.addAll(dao.getAll())
            adapter.notifyDataSetChanged()
        }
    }
}
