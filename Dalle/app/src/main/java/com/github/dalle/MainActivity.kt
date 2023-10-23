package com.github.dalle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.github.dalle.databinding.ActivityMainBinding
import com.github.dalle.model.MainViewModel
import com.github.dalle.model.MyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var _host: NavHostFragment? = null
    private val host get() = _host!!
    private val navController: NavController by lazy {
        host.navController
    }
    private lateinit var model : MainViewModel
    private val myModel by viewModels<MyViewModel>()

    /**
     * 버튼 조작 및 Jetpack Navigation 설정 ( BottomNavigation 연동 )
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        _host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        model = ViewModelProvider(this)[MainViewModel::class.java]

        setContentView(binding.root)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupBottomNavMenu(navController)
    }


    /**'
     * BottomNavigation 과 navController(Jetpack) 연결
     */
    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = binding.bottomNavView
        bottomNav.setupWithNavController(navController)
    }


    private var waitTime = 0L

    /**
     * 종료 전 뒤로가기 버튼 재확인 (2번 눌러야 종료)
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            if (System.currentTimeMillis() - waitTime > 2000) {
                navController.navigate(R.id.homeFragment)
                waitTime = System.currentTimeMillis()
                Toast.makeText(this, getString(R.string.EXIT_WARNING), Toast.LENGTH_SHORT).show()
            } else {
                finish() // Activity 종료
            }
        }
    }
}