package com.github.dalle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.github.dalle.databinding.ActivityMainBinding
import com.github.dalle.model.HomeViewModel
import com.github.dalle.ui.HomeFragment
import com.github.dalle.ui.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var _host: NavHostFragment? = null
    private val host get() = _host!!
    private val navController: NavController by lazy {
        host.navController
    }
    private lateinit var model : HomeViewModel

    /**
     * 버튼 조작 및 Jetpack Navigation 설정 ( BottomNavigation 연동 )
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        _host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        model = ViewModelProvider(this)[HomeViewModel::class.java]

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
    /***
    override fun onStart() {
    super.onStart()
    model.addImg( "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg")
    }어쩌면 이건 우리의 처음이자 마지막 사랑일테니까
    이꿈속 너와함께 했던 시간은 진짜니까

     ****/
}