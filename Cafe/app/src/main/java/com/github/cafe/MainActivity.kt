package com.github.cafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.github.cafe.databinding.ActivityMainBinding
import com.github.cafe.extension.addFragment
import com.github.cafe.ui.CafeFragment
import com.github.cafe.ui.GalleryFragment

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    protected val binding get() = _binding!!
    private var cafeFragment : CafeFragment? = null
    private var galleryFragment : GalleryFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
    }


    private fun onClick(){
        binding.cafeButton.setOnClickListener {
            cafeFragment()
        }
        binding.galleryButton.setOnClickListener {
            galleryFragment()
        }
    }

    private fun cafeFragment() {
        cafeFragment = CafeFragment().apply{
            arguments = bundleOf( "key" to "value")
        }

        binding.container.isVisible = true
        addFragment(R.id.container, cafeFragment, true)
    }

    private fun galleryFragment(){
        galleryFragment = GalleryFragment()
        binding.galleryContainer.isVisible = true
        addFragment(R.id.galleryContainer, galleryFragment, true)
    }

}
