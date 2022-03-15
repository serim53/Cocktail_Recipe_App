package com.example.cocktailrecipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailrecipe.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.*

class FavoriteFragment : Fragment() {

    lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding

    private val adapter by lazy {
        CocktailAdapter(this::onFavoriteButtonClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onResume() {
        super.onResume()

        val db = AppDatabase.getInstance(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            val drinks: List<Drink> = withContext(Dispatchers.IO){
                db!!.cocktailDao().getAll().map{Drink(it.name, it.recipe, it.image)}
            }
            adapter.setData(drinks)
            adapter.setLocalItem(drinks)
        }
    }

    private fun initViews() {
        fragmentFavoriteBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fragmentFavoriteBinding.recyclerView.adapter =  adapter

    }

    private fun onFavoriteButtonClicked(cocktailEntity: CocktailEntity, isSelected: Boolean) {
        val db = AppDatabase.getInstance(requireContext())
        Log.d("favorite_selected", isSelected.toString())
        if (isSelected.not()) {
            CoroutineScope(Dispatchers.IO).launch {
                db!!.cocktailDao().delete(cocktailEntity)
                Log.d("ROOM", db.cocktailDao().getAll().toString())
            }
            adapter.deleteData(cocktailEntity)
        }
    }
}
