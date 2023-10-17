package com.github.dalle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.github.dalle.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration


    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val navController : NavController by lazy {
        host.navController
    }
    private var _host : NavHostFragment? = null
    private val host get() = _host!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        _host= supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        setContentView(binding.root)

        // Set up Action Bar
//        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupBottomNavMenu(navController)
    }


    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = binding.bottomNavView
        bottomNav?.setupWithNavController(navController)
    }

    var waitTime = 0L
    override fun onBackPressed() {
        if(navController?.currentDestination?.id == R.id.homeFragment && !navController.popBackStack()) {
            if (System.currentTimeMillis() - waitTime >= 1500) {
                waitTime = System.currentTimeMillis()
                Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            } else {
                finish() // 액티비티 종료
            }
        }
        else{
            Log.d("asda", navController?.popBackStack().toString())
            navController.popBackStack()
        }
    }
}