package com.example.androidstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.example.androidstudy.databinding.ActivityMainBinding
import com.example.androidstudy.extension.addFragment
import com.example.androidstudy.ui.CafeFragment
import com.example.androidstudy.ui.ChattingFragment
import com.example.androidstudy.ui.GalleryFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var cafeFragment: CafeFragment? = null
    private var galleryFragment: GalleryFragment? = null
    private var chattingFragment: ChattingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
    }

    private fun onClick() {
        binding.cafeButton.setOnClickListener {
            cafeFragment()
        }
        binding.galleryButton.setOnClickListener {
            galleryFragment()
        }
        binding.chattingButton.setOnClickListener {
            chattingFragment()
        }
    }

    private fun cafeFragment() {
        cafeFragment = CafeFragment().apply {
            arguments = bundleOf("key" to "value")
        }

        binding.fragmentContainer.isVisible = true
        addFragment(R.id.fragmentContainer, cafeFragment, true)
    }

    private fun galleryFragment() {
        galleryFragment = GalleryFragment()
        binding.fragmentContainer.isVisible = true
        addFragment(R.id.fragmentContainer, galleryFragment, true)
    }

    private fun chattingFragment() {
        chattingFragment = ChattingFragment()
        binding.fragmentContainer.isVisible = true
        addFragment(R.id.fragmentContainer, chattingFragment, true)
    }
}
