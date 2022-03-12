package com.example.cocktailrecipe

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.cocktailrecipe.databinding.ItemCocktailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CocktailAdapter : RecyclerView.Adapter<CocktailAdapter.ViewHolder>() {

    var cocktailList: List<Drink> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cocktailList[position])
    }

    override fun getItemCount(): Int = cocktailList.size

    fun setData(list: List<Drink>){
        cocktailList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemCocktailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail: Drink) {
            binding.drink = cocktail

            val db = AppDatabase.getInstance(binding.root.context)
            binding.buttonFavorite.run {
                setOnClickListener { item ->
                    when(item.isSelected) {
                        true -> {
                            item.isSelected = false
                            CoroutineScope(Dispatchers.IO).launch {
                                db!!.cocktailDao().insertAll(CocktailEntity(cocktail.name, cocktail.recipe, cocktail.image))
                                Log.d("ROOM", db!!.cocktailDao().getAll().toString())
                            }

                        }
                        else -> {
                            item.isSelected = true
                            CoroutineScope(Dispatchers.IO).launch {
                                db!!.cocktailDao().delete(CocktailEntity(cocktail.image, cocktail.name, cocktail.recipe))
                                Log.d("ROOM", db!!.cocktailDao().getAll().toString())
                            }

                        }
                    }
                }
            }
        }
    }
}