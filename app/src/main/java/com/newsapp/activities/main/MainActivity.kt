package com.newsapp.activities.main

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.newsapp.R
import com.newsapp.activities.main.category.CategoryViewModel
import com.newsapp.activities.main.home.HomeViewModel
import com.newsapp.activities.main.home.HomeViewModelFactory
import com.newsapp.activities.main.search.SearchViewModel

class MainActivity : AppCompatActivity() {
    lateinit var searchViewModel: SearchViewModel
    lateinit var homeViewModel: HomeViewModel
    lateinit var catViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_category,
                R.id.navigation_search,
                R.id.navigation_setting
            )
        )

        navController.currentDestination
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // init view model
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        catViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        homeViewModel = ViewModelProviders.of(this, HomeViewModelFactory()).get(HomeViewModel::class.java)
    }
}
