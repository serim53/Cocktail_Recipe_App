package com.example.cocktailrecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktailrecipe.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(){

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.viewpager.adapter = ViewPagerAdapter(this)

        mainBinding.viewpager.registerOnPageChangeCallback(
            object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mainBinding.bottomNavigationView.menu.getItem(position).isChecked = true
                }
            }
        )
        mainBinding.bottomNavigationView.run {
            setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.item_popular -> {
                        mainBinding.viewpager.currentItem = 0
                        true
                    }
                    R.id.item_search -> {
                        mainBinding.viewpager.currentItem = 1
                        true
                    }
                    R.id.item_favorite -> {
                        mainBinding.viewpager.currentItem = 2
                        true
                    }
                    else -> false
                }
            }
        }
    }
}