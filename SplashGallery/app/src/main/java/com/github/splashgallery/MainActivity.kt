package com.github.splashgallery

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.github.splashgallery.databinding.ActivityMainBinding
import com.github.splashgallery.ui.GalleryFragment

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
    }

    private fun onClick() = with(binding) {
        val galleryContainer : FragmentContainerView = binding.galleryContainer
        galleryBtn.setOnClickListener {
            addFragment(R.id.galleryContainer, GalleryFragment(), true)
        }
    }

    private fun addFragment(containerId : Int, fragment : Fragment, addBackStack : Boolean = false ){
        supportFragmentManager.beginTransaction().add(containerId, fragment).apply{
            if(addBackStack) addToBackStack(null)
        }.commitAllowingStateLoss()
    }
}