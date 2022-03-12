package com.example.cocktailrecipe

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CocktailEntity(
    @PrimaryKey
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "recipe") val recipe: String,
    @ColumnInfo(name = "image") val image: String
)