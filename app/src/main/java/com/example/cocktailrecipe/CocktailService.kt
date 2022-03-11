package com.example.cocktailrecipe

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {

    @GET("search.php")
    fun listCocktails(@Query("s") name: String) : Call<Cocktail>
}