package com.example.cocktailrecipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailrecipe.databinding.FragmentPopularBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PopularFragment : Fragment() {

    lateinit var fragmentPopularBinding: FragmentPopularBinding
    private lateinit var service: CocktailService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentPopularBinding = FragmentPopularBinding.inflate(layoutInflater, container, false)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(CocktailService::class.java)


        return fragmentPopularBinding.root
    }

    override fun onResume() {
        super.onResume()
        getCocktail()
    }

    private fun getCocktail() {
        service.popularCocktails().enqueue(
            object: Callback<Cocktail> {
                override fun onResponse(
                    call: Call<Cocktail>,
                    response: Response<Cocktail>
                ) {
                    response.body()?.let{
                        Log.d("retrofit_test_popular", it.drinks.toString())
                        val popular = it.drinks?.get(0)
                        fragmentPopularBinding.drink = popular
                    }
                }
                override fun onFailure(call: Call<Cocktail>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}