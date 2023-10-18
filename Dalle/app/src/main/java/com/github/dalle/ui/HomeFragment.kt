package com.github.dalle.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.dalle.R
import com.github.dalle.adpater.LiveViewPagerAdapter
import com.github.dalle.databinding.FragmentHomeBinding
import com.github.dalle.model.PagerViewModel

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var liveViewPagerAdapter : LiveViewPagerAdapter
    private val model : PagerViewModel by viewModels()
    private var photoList : MutableList<String> = mutableListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewModel()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    /**
     * BottomNavigation 동기화
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu)
    }

    override fun onResume() {
        super.onResume()
        setViewModel()
    }

    /**
     * Adapter 설정 및 속성 설정
     */
    private fun setAdapter() = with(binding) {
        liveViewPagerAdapter = LiveViewPagerAdapter()
        liveViewPagerAdapter.setList(photoList)
        liveViewPager.apply{
            offscreenPageLimit = 1
            adapter = liveViewPagerAdapter
        }
    }

    /**
     * ViewModel 값 가져오기, observe설정
     */
    private fun setViewModel() {
        photoList = model.imgList.value as MutableList<String>
        model.imgList.observe(this) { result ->
            photoList = result as MutableList<String>
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}