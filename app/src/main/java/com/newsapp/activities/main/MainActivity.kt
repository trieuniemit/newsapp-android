package com.newsapp.activities.main

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.newsapp.NotificationService
import com.newsapp.R
import com.newsapp.activities.main.home.HomeViewModel
import com.newsapp.activities.main.home.HomeViewModelFactory
import com.newsapp.activities.main.search.SearchViewModel


class MainActivity : AppCompatActivity() {
    lateinit var searchViewModel: SearchViewModel
    lateinit var homeViewModel: HomeViewModel

    fun isServiceRunning(serviceClassName: String): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE).any { it.service.className == serviceClassName }
    }

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
        homeViewModel = ViewModelProviders.of(this, HomeViewModelFactory()).get(HomeViewModel::class.java)

        if(!isServiceRunning(NotificationService::class.java.simpleName)) {
            val serviceIntent = Intent(this, NotificationService::class.java)
            startService(serviceIntent)
        }
    }
}
