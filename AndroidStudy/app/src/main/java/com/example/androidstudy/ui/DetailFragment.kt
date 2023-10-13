package com.example.androidstudy.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidstudy.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var detailUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //onCreate 자리에서 bundle이 받아짐
        arguments?.let { bundle ->
            detailUri = bundle.getString(GalleryFragment.BUNDLE_URI)
        }
    }

    // 뷰 바인딩
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 사진 url 가져오기
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //이미지 세팅
        setImage()
        deleteBoard()
        onClick()
    }

    //이미지 뷰에 사진 연결
    private fun setImage() = with(binding) {
        Glide.with(binding.root)
            .load(detailUri)
            .into(detailImageView)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun onClick() {
        binding.root.setOnClickListener {
            parentFragmentManager?.popBackStack()
        }
    }

    private fun deleteBoard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        //시스템에 접근해서 자판 내려주는 것
        inputMethodManager?.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}