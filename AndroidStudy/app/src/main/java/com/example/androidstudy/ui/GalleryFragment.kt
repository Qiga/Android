package com.example.androidstudy.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy.adapter.GalleryAdapter
import com.example.androidstudy.R
import com.example.androidstudy.api.UnsplashClient
import com.example.androidstudy.data.PhotoData
import com.example.androidstudy.databinding.FragmentGalleryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private var photoList: ArrayList<PhotoData> = arrayListOf()
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var detailFragment: Fragment

    companion object {
        const val BUNDLE_URI = "uri"
    }

    /**
     * API 통신 시작 ( 안전 ? )
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        setApi()
        return binding.root
    }

    /**
     * 클릭 기능, Adapter Attach
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        setAdapter()
    }

    /**
     * RecyclerView( GridLayout ) Adapter 설정
     */
    private fun setAdapter() {
        /**
         * onClick ( funtion ) 구현을 통한 detailFragement로 전환
         */
        galleryAdapter = GalleryAdapter(onClick = { url ->
            detailFragment = DetailFragment().apply {
                arguments = bundleOf(BUNDLE_URI to url)
            }
            childFragmentManager.beginTransaction()
                .replace(R.id.detailContainer, detailFragment)
                .addToBackStack(null)
                .commit()
        })

        /**
         * GridLayout으로 설정
         */
        galleryAdapter?.setList(photoList)
        binding.galleryList.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }
    }

    /**
     * CoroutineScope Dispatcher(Main, IO)를 활용한 비동기 통신
     */
    private fun setApi(param: String? = null) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                /**
                 * 네트워크 통신 ( IO에서 진행 )
                 */
                photoList = withContext(Dispatchers.IO) {
                    UnsplashClient.unsplashApiService.getItemWithName(param)
                }
                (binding.galleryList.adapter as GalleryAdapter).setList(photoList)
            } catch (e: Exception) {
                Log.e("err", e.toString())
            }
        }
    }

    /**
     * 클릭 이벤트 설정 ( send 버튼 클릭 시 검색 및 자판, edt 비우기 )
     */
    private fun onClick() = with(binding) {
        searchBtn.setOnClickListener {
            getSearchParam()
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
            searchEdt.apply {
                clearFocus()
                text.clear()
            }
        }

        /**
         * 새로고침 설정 ( swipeRefreshLayout )
         */
        swiper.setOnRefreshListener {
            setApi()
            swiper.isRefreshing = false // 새로고침을 완료하면 아이콘 제거
        }
    }

    /**
     * 사용자 입력 값을 가지고 검색 ( API 통신 포함 )
     */
    private fun getSearchParam() = with(binding) {
        val searchText = searchEdt.text.toString()
        setApi(searchText)
    }
}