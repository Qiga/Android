package com.github.cafe.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.cafe.GalleryAdapter
import com.github.cafe.R
import com.github.cafe.data.PhotoData
import com.github.cafe.databinding.FragmentGalleryBinding
import com.yeoboyastudy.cafesampleapp.rest.UnsplashClient
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private lateinit var galleryAdapter: GalleryAdapter
    private var photoList: List<PhotoData> = listOf()

    companion object {
        const val BUNDLE_URI = "uri"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    //View 생성 이후
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        setApi()
        setAdapter()
    }

    private fun setAdapter() {
        //fragment전환을 위한 manager전달
        galleryAdapter = GalleryAdapter(fragmentManager = parentFragmentManager)
        galleryAdapter?.setList(photoList)
        binding.gridList.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setApi(param: String? = null) {
        // Coroutine 사용하여 비동기적으로 API 호출
        MainScope().launch() {
            try {
                UnsplashClient.unsplashApiService.getRandomPhotos(param).let {
                    (binding.gridList.adapter as? GalleryAdapter)?.apply {
                        this.photoList = it
                        notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                Log.e("errordd", e.toString())
            }
        }
    }

    private fun getSearchParam() = with(binding) {
        val searchText = searchEdt.text.toString()
        setApi(searchText)
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
    }
}