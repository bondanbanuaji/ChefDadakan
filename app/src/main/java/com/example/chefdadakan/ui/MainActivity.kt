package com.example.chefdadakan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.chefdadakan.R
import com.example.chefdadakan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)

        // Ambil NavController dari NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // AppBarConfiguration: kasih tau fragment mana yang jadi "top level"
        // (fragment ini tidak akan tampil tombol back, tapi tampil icon hamburger)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.searchFragment, R.id.aboutFragment),
            binding.drawerLayout
        )

        // Hubungkan Toolbar dengan NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Hubungkan Bottom Navigation dengan NavController
        binding.bottomNav.setupWithNavController(navController)

        // Hubungkan Navigation Drawer dengan NavController
        binding.navigationView.setupWithNavController(navController)
    }

    // Handle tombol back / tombol hamburger di toolbar
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}