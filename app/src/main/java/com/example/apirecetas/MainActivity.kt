package com.example.apirecetas

import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apirecetas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.Manifest


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecipeAdapter
    private val recipeList = mutableListOf<Recipe>()
    private val apiKey = "2e28a33af7fc472fb3f6f07c1e811136"
    private val STORAGE_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svRecipe.queryHint="Insert your recipe here..."
        binding.svRecipe.setIconifiedByDefault(false)
        binding.svRecipe.setOnQueryTextListener(this)
        initRecyclerView()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        }

        // Configura el OnClickListener para el botón FAV Recipes
        binding.btnFavRecipes.setOnClickListener {
            val intent = Intent(this, FavoriteRecipesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(){
        adapter = RecipeAdapter(recipeList)
        binding.rvRecipes.layoutManager = GridLayoutManager(this, 2)
        binding.rvRecipes.adapter = adapter
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByRecipe(query: String, offset: Int = 0) {
        // Si es el inicio de una nueva búsqueda, limpia la lista
        if (offset == 0) {
            recipeList.clear()
        }

        CoroutineScope(Dispatchers.IO).launch {
            val call: Call<RecipeResponse> = getRetrofit()
                .create(APIService::class.java)
                .getRecipes(apiKey, query, number = 50, offset = offset)
            val response: Response<RecipeResponse> = call.execute()
            runOnUiThread {
                if (response.isSuccessful) {
                    val recipeResponse: RecipeResponse? = response.body()
                    if (recipeResponse != null) {
                        recipeList.addAll(recipeResponse.results)
                        adapter.notifyDataSetChanged()
                        // Si hay más resultados, llama a la función de nuevo con el nuevo offset
                        if (recipeResponse.results.size == 50) {
                            searchByRecipe(query, offset + 50)
                        }
                    }
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError(){
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByRecipe(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // Aquí puedes manejar los cambios en el texto de la consulta
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de almacenamiento concedido", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
