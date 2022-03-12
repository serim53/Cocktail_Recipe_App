package com.example.cocktailrecipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailrecipe.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    lateinit var fragmentSearchBinding: FragmentSearchBinding
    private lateinit var service: CocktailService
    private val adapter by lazy {
        CocktailAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return fragmentSearchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(CocktailService::class.java)

        fragmentSearchBinding.buttonSearch.setOnClickListener {
            searchCocktail()
        }

    }

    private fun initViews() {
        fragmentSearchBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fragmentSearchBinding.recyclerView.adapter =  adapter
    }

    private fun searchCocktail() {
        service.listCocktails(fragmentSearchBinding.edittextSearch.text.toString()).enqueue(
            object: Callback<Cocktail> {
                override fun onResponse(
                    call: Call<Cocktail>,
                    response: Response<Cocktail>
                ) {
                    response.body()?.let{
                        Log.d("retrofit_test", it.drinks.toString())
                        if (it.drinks.isNullOrEmpty()){
                            Toast.makeText(requireContext(), "정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
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