package com.example.cocktailrecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailrecipe.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    private lateinit var service: CocktailService

    private val adapter by lazy {
        CocktailAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

    }

    override fun onStart() {
        super.onStart()

        initViews()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(CocktailService::class.java)

        mainBinding.buttonSearch.setOnClickListener {
            searchCocktail()
        }
    }

    private fun initViews() {
        mainBinding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mainBinding.recyclerView.adapter =  adapter
    }

    private fun searchCocktail() {
        service.listCocktails(mainBinding.edittextSearch.text.toString()).enqueue(
            object: Callback<Cocktail> {
                override fun onResponse(
                    call: Call<Cocktail>,
                    response: Response<Cocktail>
                ) {
                    response.body()?.let{
                        Log.d("retrofit_test", it.drinks.toString())
                        if (it.drinks.isNullOrEmpty()){
                            Toast.makeText(this@MainActivity, "정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            adapter.setData(it.drinks)
                        }
                    }
                }

                override fun onFailure(call: Call<Cocktail>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}