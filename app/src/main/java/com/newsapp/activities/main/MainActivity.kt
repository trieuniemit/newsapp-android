package com.newsapp.activities.main

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.newsapp.R
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var mStacks = HashMap<Int, Int>()

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
                R.id.navigation_search,
                R.id.navigation_setting
            )
        )

        mStacks[R.id.navigation_home] = R.id.navigation_home

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnNavigationItemSelectedListener {
            if(navView.selectedItemId != it.itemId) {
                if (!mStacks.contains(it.itemId)) {
                    navController.navigate(it.itemId)
                    Log.d("Navigation", "Navigate to new tab")
                } else {
                    Log.d("Navigation", "Back stack to old tab")
                    navController.popBackStack(it.itemId, false)
                }
                mStacks[it.itemId] = it.itemId
            }
            true
        }
    }
}
