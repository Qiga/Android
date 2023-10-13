package com.example.androidstudy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidstudy.adapter.ChattingAdapter
import com.example.androidstudy.data.ChatData
import com.example.androidstudy.databinding.FragmentChattingBinding

class ChattingFragment : Fragment() {

    private var _binding: FragmentChattingBinding? = null
    private val binding get() = _binding!!
    private var message: ChatData? = null
    private lateinit var chattingAdapter: ChattingAdapter
    private var mine = true

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
            message = ChatData(binding.inputEdt.text.toString(), mine)
            mine = !mine
            chattingAdapter.addToList(message)
            chattingList.scrollToPosition(0)
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
}