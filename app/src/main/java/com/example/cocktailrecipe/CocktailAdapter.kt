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

class CocktailAdapter(
    private val onFavoriteButtonClicked: (CocktailEntity, Boolean) -> Unit
) : RecyclerView.Adapter<CocktailAdapter.ViewHolder>() {

    private var cocktailList: ArrayList<Drink> = arrayListOf()
    private var localCocktailList: List<Drink> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cocktailList[position])
    }

    override fun getItemCount(): Int = cocktailList.size

    fun setLocalItem(list: List<Drink>) {
        localCocktailList = list
        notifyDataSetChanged()
    }

    fun setData(list: List<Drink>){
        cocktailList = list as ArrayList<Drink>
        notifyDataSetChanged()
    }

    fun deleteData(cocktailEntity: CocktailEntity){
        val drink = cocktailList.firstOrNull { it.name == cocktailEntity.name }
        val index = cocktailList.indexOf(drink)
        cocktailList.remove(drink)
        notifyItemRemoved(index)
    }

    inner class ViewHolder(
        private val binding: ItemCocktailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail: Drink) {
            binding.drink = cocktail
            binding.buttonFavorite.run {
                isSelected = localCocktailList.contains(cocktail)
                setOnClickListener { item ->
                    item.isSelected = !item.isSelected
                    onFavoriteButtonClicked(CocktailEntity(cocktail.name, cocktail.recipe, cocktail.image), item.isSelected)
                    Log.d("adapter_selected", item.isSelected.toString())
                }
            }
        }
    }
}
