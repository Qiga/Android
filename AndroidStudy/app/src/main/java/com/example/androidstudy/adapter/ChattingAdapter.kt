package com.example.androidstudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy.data.ChatData
import com.example.androidstudy.databinding.ItemTextLeftLayoutBinding
import com.example.androidstudy.databinding.ItemTextRightLayoutBinding

class ChattingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var chatList: MutableList<ChatData> = mutableListOf()

    /**
     * 메시지 주인에 따른 왼쪽 채팅, 오른쪽 채팅 Hodler( View를 지정해주는 ) 구분하기
     */
    inner class ChattingRightHolder(private val binding: ItemTextRightLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val chat = chatList[position]
            binding.sendText.text = chat.msg.toString()
        }
    }

    inner class ChattingLeftHolder(private val binding: ItemTextLeftLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val chat = chatList[position]
            binding.sendText.text = chat.msg.toString()
        }
    }

    /**
     * ViewType을 구분하여 ViewHodler에 전달하기 (왼쪽 1, 오른쪽 0 )
     */
    enum class Type {
        TEXT_RIGHT, TEXT_LEFT
    }
    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].type) Type.TEXT_RIGHT.ordinal else Type.TEXT_LEFT.ordinal
    }
    
    /**
     * 앞서 구분한 viewType에 따른 Holder종류 선택 및 반환 (return type : RecyclerView.ViewHolder (상위 )
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == Type.TEXT_RIGHT.ordinal)
            ChattingRightHolder(
                ItemTextRightLayoutBinding.inflate(layoutInflater, parent, false)
            )
        else
            ChattingLeftHolder(
                ItemTextLeftLayoutBinding.inflate(layoutInflater, parent, false)
            )
    }

    /**
     * Holder 종류에 따라 해당 holder의 bind(function) 사용하여 Binding진행
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChattingLeftHolder -> holder.bind(position)
            is ChattingRightHolder -> holder.bind(position)
            else -> null
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    /**
     * chatList에 ChatData(message)추가, UI업데이트
     */
    fun addToList(list: ChatData?) {
        if (list != null) {
            chatList.add(0, list)
            notifyItemInserted(0)
        }
    }
}