package com.example.cocktailrecipe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CocktailEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cocktailDao() : CocktailDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "cocktail-database"
                    ).build()
                }
            }
            return instance
        }
    }
}