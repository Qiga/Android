package com.example.androidstudy.ui

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy.R
import com.example.androidstudy.adapter.SelectPhotoAdapter
import com.example.androidstudy.api.UnsplashClient
import com.example.androidstudy.data.PhotoData
import com.example.androidstudy.databinding.DialogMenuOptionBinding
import com.example.androidstudy.databinding.DialogSelectPhotoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SelectPhotoDialog(
    private var onClick: (String) -> Unit
) : DialogFragment() {

    private var _binding: DialogSelectPhotoBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectAdapter: SelectPhotoAdapter
    private var photoList = arrayListOf<PhotoData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSelectPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.isNotFloatingDialog)
    }

    /**
     * Dialog 설정
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApi()
        setAdapter()
    }

    /**'
     * Adapter설정, onClick 메서드 구현으로 사진 정보 전달 ( to ChattingFragment )
     */
    private fun setAdapter() {
        selectAdapter = SelectPhotoAdapter(onClick = { imgUrl ->
            onClick.invoke(imgUrl)
            dismiss()
        })
        selectAdapter.setList(photoList)
        binding.photoRV.apply {
            adapter = selectAdapter
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }
    }

    /**
     * unSplash API 호출(Network요청 IO로 처리), 리스트 설정
     */
    private fun callApi() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    UnsplashClient.unsplashApiService.getItemWithName(null)
                }
                (binding.photoRV.adapter as SelectPhotoAdapter).setList(result)
            } catch (e: Exception) {
                Log.d("error", e.toString())
            }
        }
    }
}