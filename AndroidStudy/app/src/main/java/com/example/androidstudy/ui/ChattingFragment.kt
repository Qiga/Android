package com.example.androidstudy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.androidstudy.adapter.ChattingAdapter
import com.example.androidstudy.data.ChatData
import com.example.androidstudy.databinding.DialogSelectPhotoBinding
import com.example.androidstudy.databinding.FragmentChattingBinding

class ChattingFragment : Fragment() {

    private var _binding: FragmentChattingBinding? = null
    private val binding get() = _binding!!
    private var message: ChatData? = null
    private lateinit var chattingAdapter: ChattingAdapter
    private lateinit var selectPhotoDialog: SelectPhotoDialog
    private var mine = true
    private var img : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChattingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        setAdapter()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    /**
     * 클릭 이벤트 등록
     */
    private fun onClick() = with(binding) {
        sendBtn.setOnClickListener {
            Log.d("qwfwq", img.toString())
            message = ChatData(binding.inputEdt.text.toString(), mine, img)
            mine = !mine
            chattingAdapter.addToList(message)
            chattingList.scrollToPosition(0)
            img = null
            imgInputContainer.isVisible = false
        }
        selectPhotoBtn.setOnClickListener {
            setPhotoBinding()
        }
    }

    private fun setAdapter() {
        chattingAdapter = ChattingAdapter()
        binding.chattingList.apply {
            adapter = chattingAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        }
        chattingAdapter.addToList(message)
    }

    /**
     * 사진 첨부 Dialog띄우기 ( onClick => 선택한 사진(url)을 채팅에 추가 )
     */
    private fun setPhotoBinding() {
        selectPhotoDialog = SelectPhotoDialog(onClick = {imgUrl ->
            Glide.with(binding.root)
                .load(imgUrl)
                .into(binding.inputImg)
            img = imgUrl
            binding.imgInputContainer.isVisible = true
        })
        binding.selectContainer.isVisible = true
        selectPhotoDialog.show(childFragmentManager, "")
    }
}