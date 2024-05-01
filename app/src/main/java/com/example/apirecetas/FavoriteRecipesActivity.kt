package com.example.apirecetas

import android.content.Intent
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
    private val dao = FavoriteRecipeDAO(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FavRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        // Configura el OnClickListener para el botón Back to Menu
        binding.btnBackToMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(){
        adapter = FavoriteRecipeAdapter(favoriteRecipeList)
        binding.rvFavRecipes.layoutManager = LinearLayoutManager(this)
        binding.rvFavRecipes.adapter = adapter
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
