package com.github.dalle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.dalle.databinding.ActivityMainBinding
import com.github.dalle.ui.AddFragment
import com.github.dalle.ui.ClipFragment
import com.github.dalle.ui.HomeFragment
import com.github.dalle.ui.MyFragment
import com.github.dalle.ui.SearchFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val homeFragment by lazy { HomeFragment() }
    private val clipFragment by lazy { ClipFragment() }
    private val addFragment by lazy { AddFragment() }
    private val searchFragment by lazy { SearchFragment() }
    private val myFragment by lazy { MyFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, homeFragment).addToBackStack(null).commit()
        onClick()
    }
    private fun onClick() = with(binding) {
        menuNav.setOnItemSelectedListener{ selectedItem ->
            val currentFragment = supportFragmentManager.fragments.last()
            changeFragment(selectedItem.title.toString(), currentFragment)
            true //항목 선택을 완료함
        }
    }

    private fun changeFragment(selected : String, currentFragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = when(selected){
            "Home" -> homeFragment
            "Clip" -> clipFragment
            "Search" -> searchFragment
            "MyPage" -> myFragment
            else -> addFragment
        }

        transaction.apply{
            hide(currentFragment)
            if(!fragment.isAdded){
                add(R.id.fragmentContainer, fragment)
                addToBackStack(null)
            }
            show(fragment)
        }.commit()

    }
}