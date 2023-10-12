package com.github.splashgallery.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.splashgallery.GalleryAdapter
import com.github.splashgallery.api.Unsplash
import com.github.splashgallery.databinding.FragmentGalleryBinding
import com.github.splashgallery.extension.toast
import com.github.splashgallery.model.PhotoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private var photoList: ArrayList<PhotoData> = arrayListOf()
    private lateinit var galleryAdapter: GalleryAdapter

    companion object {
        const val BUNDLE_URI = "uri"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        setApi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        setAdapter()
    }

    private fun setAdapter() {
        //fragment전환을 위한 manager전달
        galleryAdapter = GalleryAdapter(fragmentManager = parentFragmentManager)
        galleryAdapter?.setList(photoList)
        binding.galleryList.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }
    }

    // Coroutine 사용하여 비동기적으로 API 호출
    private fun setApi(param: String? = null) {
        // UI 작업은 Main에 전달
        CoroutineScope(Dispatchers.Main).launch {
            //검색 에러 처리 ( 찾을수 없는 결과 )
            try {
                //네트워크 통신은 IO에서 처리
                photoList = withContext(Dispatchers.IO) {
                    Unsplash.unsplashService.getItemWithName(param)
                }
                (binding.galleryList.adapter as GalleryAdapter).setList(photoList)
            } catch (e: Exception) {
                Log.e("err", e.toString())
                requireContext().toast("파일을 찾을 수 없습니다")
            }
        }
    }

    private fun onClick() = with(binding) {
        searchBtn.setOnClickListener {
            getSearchParam()
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            //시스템에 접근해서 자판 내려주는 것
            inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
            searchEdt.apply {
                // 포커스 없애주고
                clearFocus()
                // 내용 지워주고
                text.clear()
            }
        }
        swiper.setOnRefreshListener {
            setApi()
            swiper.isRefreshing = false // 새로고침을 완료하면 아이콘을 없앤다.
        }
    }

    private fun getSearchParam() = with(binding) {
        val searchText = searchEdt.text.toString()
        setApi(searchText)
    }
}