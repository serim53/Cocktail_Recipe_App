package com.example.cocktailrecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailrecipe.databinding.ItemCocktailBinding

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
        }
    }
}