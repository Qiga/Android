package com.example.androidstudy.adapter

import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidstudy.data.ChatData
import com.example.androidstudy.databinding.ItemImgLeftLayoutBinding
import com.example.androidstudy.databinding.ItemImgRightLayoutBinding
import com.example.androidstudy.databinding.ItemTextImgLeftLayoutBinding
import com.example.androidstudy.databinding.ItemTextImgRightLayoutBinding
import com.example.androidstudy.databinding.ItemTextLeftLayoutBinding
import com.example.androidstudy.databinding.ItemTextRightLayoutBinding
import com.example.androidstudy.databinding.ItemVideoLeftLayoutBinding
import com.example.androidstudy.databinding.ItemVideoRightLayoutBinding


class ChattingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var chatList: MutableList<ChatData> = mutableListOf()
    var firstUpdate : Boolean = false
    /**
     * 텍스트 채팅(우측)
     */
    inner class TextRightChattingHolder(private val binding: ItemTextRightLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val chat = chatList[position] as ChatData.TextChat
            binding.sendText.text = chat.msg.toString()
        }
    }

    /**
     * 이미지 채팅 (우측)
     */
    inner class ImgRightChattingHolder(private val binding: ItemImgRightLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val chat = chatList[position] as ChatData.ImgChat
            Glide.with(binding.root)
                .load(chat.img)
                .into(binding.sendImg)
            binding.sendImg.isVisible = true
        }
    }

    /**
     * 텍스트 + 이미지 채팅 (우측)
     */
    inner class TextWithImgRightChattingHolder(private val binding: ItemTextImgRightLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val chat = chatList[position] as ChatData.TextWithImgChat
            binding.sendText.text = chat.msg.toString()
            Glide.with(binding.root)
                .load(chat.img)
                .into(binding.sendImg)
            binding.sendImg.isVisible = true
        }
    }

    inner class VideoRightChattingHolder(private val binding: ItemVideoRightLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val chat = chatList[position] as ChatData.VideoChat
            binding.videoChatView.setVideoURI(Uri.parse(chat.video))

            //준비 되면 실행할 것
            binding.videoChatView.setOnPreparedListener {
                it.seekTo(0)
                it.start()
            }
        }
    }

    /**
     * 텍스트 채팅 (좌측)
     */
    inner class TextLeftChattingHolder(private val binding: ItemTextLeftLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val chat = chatList[position] as ChatData.TextChat
            binding.sendText.text = chat.msg.toString()
        }
    }

    /**
     * 이미지 채팅 (좌측)
     */
    inner class ImgLeftChattingHolder(private val binding: ItemImgLeftLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val chat = chatList[position] as ChatData.ImgChat
            Glide.with(binding.root)
                .load(chat.img)
                .into(binding.sendImg)
            binding.sendImg.isVisible = true
        }
    }

    /**
     * 텍스트 + 이미지 채팅 (좌측)
     */
    inner class TextWithImgLeftChattingHolder(private val binding: ItemTextImgLeftLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val chat = chatList[position] as ChatData.TextWithImgChat
            binding.sendText.text = chat.msg.toString()
            Glide.with(binding.root)
                .load(chat.img)
                .into(binding.sendImg)
            binding.sendImg.isVisible = true
        }
    }

    /**
     * 비디오 채팅
     */
    inner class VideoLeftChattingHolder(private val binding: ItemVideoLeftLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val chat = chatList[position] as ChatData.VideoChat
            binding.videoChatView.setVideoPath(chat.video)

            //준비 되면 실행할 것
            binding.videoChatView.setOnPreparedListener {
                it.seekTo(0)
                it.start()
            }
        }
    }


    /**
     * ViewType을 구분하여 ViewHodler에 전달하기 (왼쪽 1, 오른쪽 0 )
     */
    enum class Type {
        TEXT_RIGHT, TEXT_LEFT, TEXT_IMG_RIGHT, TEXT_IMG_LEFT, IMG_RIGHT, IMG_LEFT, VIDEO_RIGHT, VIDEO_LEFT, BIT_IMG_LEFT, BIT_IMG_RIGHT
    }


    override fun getItemViewType(position: Int): Int {
        return if ((chatList[position] as ChatData.Base).isMe) {
            when (chatList[position]) {
                is ChatData.ImgChat -> Type.IMG_RIGHT.ordinal
                is ChatData.TextChat -> Type.TEXT_RIGHT.ordinal
                is ChatData.TextWithImgChat -> Type.TEXT_IMG_RIGHT.ordinal
                is ChatData.VideoChat -> Type.VIDEO_RIGHT.ordinal
                is ChatData.BitImgChat -> Type.BIT_IMG_RIGHT.ordinal
            }
        } else {
            when (chatList[position]) {
                is ChatData.ImgChat -> Type.IMG_LEFT.ordinal
                is ChatData.TextChat -> Type.TEXT_LEFT.ordinal
                is ChatData.TextWithImgChat -> Type.TEXT_IMG_LEFT.ordinal
                is ChatData.VideoChat -> Type.VIDEO_LEFT.ordinal
                is ChatData.BitImgChat -> Type.BIT_IMG_LEFT.ordinal
            }
        }
    }

    /**
     * 앞서 구분한 viewType에 따른 Holder종류 선택 및 반환 (return type : RecyclerView.ViewHolder (상위 )
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Type.TEXT_RIGHT.ordinal -> TextRightChattingHolder(
                ItemTextRightLayoutBinding.inflate(layoutInflater, parent, false)
            )
            Type.TEXT_LEFT.ordinal -> TextLeftChattingHolder(
                ItemTextLeftLayoutBinding.inflate(layoutInflater, parent, false)
            )
            Type.IMG_RIGHT.ordinal -> ImgRightChattingHolder(
                ItemImgRightLayoutBinding.inflate(layoutInflater, parent, false)
            )
            Type.IMG_LEFT.ordinal -> ImgLeftChattingHolder(
                ItemImgLeftLayoutBinding.inflate(layoutInflater, parent, false)
            )
            Type.TEXT_IMG_RIGHT.ordinal -> TextWithImgRightChattingHolder(
                ItemTextImgRightLayoutBinding.inflate(layoutInflater, parent, false)
            )
            Type.TEXT_IMG_LEFT.ordinal -> TextWithImgLeftChattingHolder(
                ItemTextImgLeftLayoutBinding.inflate(layoutInflater, parent, false)
            )
            Type.VIDEO_RIGHT.ordinal -> VideoRightChattingHolder(
                ItemVideoRightLayoutBinding.inflate(layoutInflater, parent, false)
            )
            Type.VIDEO_LEFT.ordinal -> VideoLeftChattingHolder(
                ItemVideoLeftLayoutBinding.inflate(layoutInflater, parent, false)
            )
            Type.BIT_IMG_RIGHT.ordinal -> ImgRightChattingHolder(
                ItemImgRightLayoutBinding.inflate(layoutInflater, parent, false)
            )
            Type.BIT_IMG_LEFT.ordinal -> ImgLeftChattingHolder(
                ItemImgLeftLayoutBinding.inflate(layoutInflater, parent, false)
            )
            else -> TextRightChattingHolder(
                ItemTextRightLayoutBinding.inflate(layoutInflater, parent, false)
            )
        }
    }

    /**
     * Holder 종류에 따라 해당 holder의 bind(function) 사용하여 Binding진행
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextRightChattingHolder -> holder.bind(position)
            is TextLeftChattingHolder -> holder.bind(position)
            is ImgRightChattingHolder -> holder.bind(position)
            is ImgLeftChattingHolder -> holder.bind(position)
            is TextWithImgRightChattingHolder -> holder.bind(position)
            is TextWithImgLeftChattingHolder -> holder.bind(position)
            is VideoRightChattingHolder -> holder.bind(position)
            is VideoLeftChattingHolder -> holder.bind(position)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    /**
     * chatList 업데이트, UI업데이트
     */
    fun addToList(list: MutableList<ChatData>) {
        if(!firstUpdate){
            chatList = list
            notifyDataSetChanged()
            firstUpdate = true
        }else{
            chatList = list
            Log.d("하나", "22")
            notifyItemInserted(0)
        }
    }
}