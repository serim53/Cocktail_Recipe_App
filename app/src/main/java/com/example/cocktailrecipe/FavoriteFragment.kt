package com.example.cocktailrecipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailrecipe.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding

    private val adapter by lazy {
        CocktailAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val db = AppDatabase.getInstance(requireContext())
        fragmentFavoriteBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fragmentFavoriteBinding.recyclerView.adapter =  adapter

        val r = Runnable {
            val drink = db!!.cocktailDao().getAll()!!
        }

        val thread = Thread(r)
        thread.start()
//        CoroutineScope(Dispatchers.IO).async {
//
//            val drink = async {db!!.cocktailDao().getAll().map { Drink(it.name, it.recipe, it.image)}}
////            adapter.setData(db!!.cocktailDao().getAll().map { Drink(it.name, it.recipe, it.image)})
////            Log.d("ROOM", db!!.cocktailDao().getAll().toString())
//            adapter.setData(drink.await())
//        }
        Log.d("ROOM", db!!.cocktailDao().getAll().toString())
    }
}