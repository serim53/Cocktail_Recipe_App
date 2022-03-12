package com.example.cocktailrecipe

import androidx.room.*

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktailentity")
    fun getAll(): List<CocktailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cocktailEntity: CocktailEntity)

    @Delete
    fun delete(cocktailEntity: CocktailEntity)
}