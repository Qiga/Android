package com.example.timerecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.timerecord.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var _host : NavHostFragment? = null
    private val host get() = _host!!
    private val navController: NavController by lazy {
        host.navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        setupBottomNavMenu(navController)
    }


    /**'
     * BottomNavigation 과 navController(Jetpack) 연결
     */
    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = binding.navBottomMenu
        bottomNav.setupWithNavController(navController)
    }

}