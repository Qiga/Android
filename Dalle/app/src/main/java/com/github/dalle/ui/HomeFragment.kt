package com.github.dalle.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.dalle.R
import com.github.dalle.adpater.LiveViewPagerAdapter
import com.github.dalle.data.UserImg
import com.github.dalle.databinding.FragmentHomeBinding
import com.github.dalle.model.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var liveViewPagerAdapter: LiveViewPagerAdapter
    private val model: MainViewModel by activityViewModels()
    private var photoList: MutableList<UserImg> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObserver()
        onClick()
        setSlide()
    }

    /**
     * BottomNavigation 동기화
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu)
    }

    /**
     * Adapter 설정 및 속성 설정
     */
    private fun setAdapter() = with(binding) {
        liveViewPagerAdapter = LiveViewPagerAdapter()
        liveViewPagerAdapter.setList(photoList)
        liveViewPager.apply {
            offscreenPageLimit = 1
            adapter = liveViewPagerAdapter
        }
    }

    private fun setSlide() = with(binding) {
//        CoroutineScope(Dispatchers.Main).launch {
//            var num = 1
//            while (true) {
//                num++
//                liveViewPager.apply {
//                    setCurrentItem(num%5+1, true)
//                    delay(2000)
//                }
//                continue
//            }
//        }
    }

    /**
     * 클릭 이벤트 등록
     */
    private fun onClick() = with(binding) {
        addImgButton.setOnClickListener {
            model.addImg("https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg")
        }
    }

    /**
     * ViewModel 값 가져오기, observe설정
     */
    private fun setViewModel() {
        binding.apply {
//            model = ViewModelProvider(this@HomeFragment)[HomeViewModel::class.java]
            pager = model
        }
    }

    /**
     * viewModel Observer 설정
     */
    private fun setObserver() = with(model) {
        imgList.observe(viewLifecycleOwner) {
            Log.d("변화", it.size.toString())
            liveViewPagerAdapter.setList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}