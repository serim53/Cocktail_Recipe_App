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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    lateinit var fragmentSearchBinding: FragmentSearchBinding
    private lateinit var service: CocktailService

    private val adapter by lazy {
        CocktailAdapter(this::onFavoriteButtonClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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
        fragmentSearchBinding.recyclerView.adapter = adapter
    }

    private fun searchCocktail() {
        service.listCocktails(fragmentSearchBinding.edittextSearch.text.toString()).enqueue(
            object : Callback<Cocktail> {
                override fun onResponse(
                    call: Call<Cocktail>,
                    response: Response<Cocktail>
                ) {
                    response.body()?.let {
                        Log.d("retrofit_test", it.drinks.toString())
                        if (it.drinks.isNullOrEmpty()) {
                            Toast.makeText(requireContext(), "정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            adapter.setData(it.drinks)
                            setAdapterData()
                        }
                    }
                }

                override fun onFailure(call: Call<Cocktail>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun setAdapterData() {
        val db = AppDatabase.getInstance(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            val drinks: List<Drink> = withContext(Dispatchers.IO){
                db!!.cocktailDao().getAll().map { Drink(name = it.name, recipe = it.recipe, image = it.image) }
            }
            adapter.setLocalItem(drinks)
        }
    }

    private fun onFavoriteButtonClicked(cocktailEntity: CocktailEntity, isSelected: Boolean) {
        val db = AppDatabase.getInstance(requireContext())
        Log.d("search_selected", isSelected.toString())
        when (isSelected) {
            true -> {
                CoroutineScope(Dispatchers.IO).launch {
                    db!!.cocktailDao().insertAll(cocktailEntity)
                    Log.d("ROOM", db.cocktailDao().getAll().toString())
                }
            }
            else -> {
                CoroutineScope(Dispatchers.IO).launch {
                    db!!.cocktailDao().delete(cocktailEntity)
                    Log.d("ROOM", db.cocktailDao().getAll().toString())
                }
            }
        }
    }
}
