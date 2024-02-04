package com.example.apirecetas

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface APIService {


    /*@GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): Response<Recipe>*/

    /*@GET
     suspend fun getRecipes(
        @Query("apiKey") apiKey: String
     ):Response<Recipe>*/
   /* @POST("category/{cat}/")
    fun categoryList(@Path("cat") category: String, @Query("page") page: Int): Call<List<Item>>*/

    @GET("recipes/{id}/information")
    fun getRecipeDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): Call<RecipeDetail>

    @GET("recipes/complexSearch")
    fun getRecipes(@Query("apiKey") apiKey: String,
                   @Query("query") query: String,
                   @Query("number") number: Int = 10,
                   @Query("offset") offset: Int = 0): Call<RecipeResponse>

    /*@GET("recipesResponse/random")
    suspend fun getRandomRecipesResponse(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): Response<RecipeResponse>*/

   /* @GET
    suspend fun getRecipesResponse(
        @Query("apiKey") apiKey: String
    ):Response<RecipeResponse>*/
}