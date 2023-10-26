package com.example.androidstudy.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.androidstudy.R
import com.example.androidstudy.adapter.ChattingAdapter
import com.example.androidstudy.data.ChatData
import com.example.androidstudy.databinding.FragmentChattingBinding
import com.example.androidstudy.model.ChattingViewModel

class ChattingFragment : Fragment() {

    private var _binding: FragmentChattingBinding? = null
    private val binding get() = _binding!!

    private lateinit var chattingAdapter: ChattingAdapter
    private lateinit var selectPhotoDialog: SelectPhotoDialog
    private var videoSrc : String? = null
    private var img: String? = null
    private var bitImg : Bitmap? = null
    private val model: ChattingViewModel by activityViewModels()
    private var mine = true


    private val addFragment: AddFragment by lazy {
        AddFragment(setDialog = { setPhotoDialog() },
            setLocalImg = { uri ->
                img = uri
                Glide.with(binding.root)
                    .load(uri)
                    .into(binding.inputImg)
                binding.imgInputContainer.isVisible = true
            },
            setVideo = { uri ->
                videoSrc = uri
            },
            setCaptureImg = { bitmap ->
                bitImg = bitmap
                Glide.with(binding.root)
                    .load(bitmap)
                    .into(binding.inputImg)
                binding.imgInputContainer.isVisible = true
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChattingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObserver()
        onClick()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    /**
     * 클릭 이벤트 등록
     */
    private fun onClick() = with(binding) {
        //이미지 데이터 전달 후 초기화
        sendBtn.setOnClickListener {
            //더블클릭 발생으로 인한 notify오류 방지
            binding.sendBtn.isClickable = false
            val chat = if (binding.inputEdt.text.isEmpty() && img.toString().isNullOrEmpty()) null
            else if (!videoSrc.isNullOrEmpty()) ChatData.VideoChat(mine, videoSrc!!)
            else if (binding.inputEdt.text.isEmpty()) ChatData.ImgChat(mine, img!!)
            else if (img.isNullOrEmpty() && bitImg == null) ChatData.TextChat(mine, binding.inputEdt.text.toString())
            else if (bitImg == null) ChatData.TextWithImgChat(mine, binding.inputEdt.text.toString(), img!!)
            else ChatData.BitImgChat(mine, bitImg!!)
            //빈 채팅으로 인해 생기는 오류 방지
            chat?.let { model.sendChatting(chat) } ?: run { binding.sendBtn.isClickable = true }
            mine = !mine
            chattingList.scrollToPosition(0)
            clearData()
        }
        selectPhotoBtn.setOnClickListener {
            if (addFragment.isAdded) {
                childFragmentManager.beginTransaction().show(addFragment).commit()
            } else {
                childFragmentManager.beginTransaction().add(R.id.addFragmentContainer, addFragment)
                    .addToBackStack(null).commit()
            }
        }
    }

    /**
     * Adapter설정
     */
    private fun setAdapter() {
        chattingAdapter = ChattingAdapter()
        binding.chattingList.apply {
            adapter = chattingAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        }
    }

    /**
     * ViewModel 값 Observer 설정
     */
    private fun setObserver() {
        model.chatList.observe(viewLifecycleOwner) {
            chattingAdapter.addToList(it)
            binding.sendBtn.isClickable = true
        }
    }

    /**
     * 사진 첨부 Dialog띄우기 ( onClick => 선택한 사진(url)을 채팅에 추가 )
     */
    private fun setPhotoDialog() {
        selectPhotoDialog = SelectPhotoDialog(onClick = { imgUrl ->
            img = imgUrl
            Glide.with(binding.root).load(imgUrl).into(binding.inputImg)
            binding.imgInputContainer.isVisible = true
        })
        binding.selectContainer.isVisible = true
        selectPhotoDialog.show(childFragmentManager, "")
    }

    /**
     * 입력 값 ( 메시지 ) , 이미지 값 ( 첨부 ) 초기화 함수
     */
    private fun clearData() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.root.windowToken, 0)
        binding.inputEdt.apply {
            clearFocus()
            text.clear()
        }
        img = null
        videoSrc = null
        bitImg = null
        binding.imgInputContainer.isVisible = false
    }
}