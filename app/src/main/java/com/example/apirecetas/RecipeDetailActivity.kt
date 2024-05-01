package com.example.apirecetas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.apirecetas.databinding.RecipeDetailBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: RecipeDetailBinding
    private val apiKey = "2e28a33af7fc472fb3f6f07c1e811136"
    private val dao by lazy { AppDatabase.getDatabase(this).favoriteRecipeDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeId = intent.getIntExtra("recipeId", 0)
        Log.d("RecipeDetailActivity", "Received Recipe ID: $recipeId")
        lifecycleScope.launch {
            fetchRecipeDetails(recipeId) { recipe ->
                recipe?.let {
                    bindRecipeDetails(it)
                }
            }
            val favoriteRecipe = dao.getRecipeById(recipeId)
            if (favoriteRecipe != null) {
                // La receta ya está en favoritos
                binding.btnHeart.setImageResource(R.drawable.ic_heart_fill)
            } else {
                // La receta no está en favoritos
                binding.btnHeart.setImageResource(R.drawable.ic_heart_empty)
            }
        }
        binding.btnBack.setOnClickListener{finish()}
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun fetchRecipeDetails(id: Int, onResult: (RecipeDetail?) -> Unit) {
        Log.d("RecipeDetailActivity", "Fetching recipe details for ID: $id")
        val call: Call<RecipeDetail> = getRetrofit()
            .create(APIService::class.java)
            .getRecipeDetails(id, apiKey)

        call.enqueue(object : Callback<RecipeDetail> {
            override fun onResponse(call: Call<RecipeDetail>, response: Response<RecipeDetail>) {
                val recipe = if (response.isSuccessful) response.body() else null
                lifecycleScope.launch {
                    onResult(recipe)
                }
            }
            override fun onFailure(call: Call<RecipeDetail>, t: Throwable) {
                lifecycleScope.launch {
                    onResult(null)
                }
            }
        })
    }

    private fun bindRecipeDetails(recipe: RecipeDetail) {
        Log.d("RecipeDetailActivity", "Binding recipe details: $recipe")

        binding.tvTitle.text = recipe.title
        Picasso.get().load(recipe.image).transform(RoundedCornersTransformation(30, 0)).into(binding.ivRecipe)

        // Crear una cadena de texto con todos los ingredientes
        val ingredientsText = recipe.ingredients.joinToString("\n") { it.name }
        binding.tvIngredients.text = ingredientsText

        binding.btnLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe.sourceUrl))
            startActivity(intent)
        }

        binding.tvSummary.text = recipe.instructions

        binding.btnHeart.setOnClickListener {
            lifecycleScope.launch {
                val favoriteRecipe = dao.getRecipeById(recipe.id)
                if (favoriteRecipe != null) {
                    // Si la receta ya está en favoritos, la eliminamos
                    dao.delete(favoriteRecipe)
                    binding.btnHeart.setImageResource(R.drawable.ic_heart_empty)
                } else {
                    // Si la receta no está en favoritos, la agregamos
                    val newFavoriteRecipe = FavoriteRecipe(recipe.id, recipe.title, recipe.image)
                    dao.insert(newFavoriteRecipe)
                    binding.btnHeart.setImageResource(R.drawable.ic_heart_fill)
                }
            }
        }
    }
}
