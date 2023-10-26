package com.example.androidstudy.data

import android.graphics.Bitmap
import android.net.Uri

sealed class ChatData {

    interface Base {
        val isMe: Boolean
    }

    data class TextChat(
        override val isMe: Boolean,
        val msg: String
    ) : ChatData(), Base

    data class ImgChat(
        override val isMe: Boolean,
        val img: String
    ) : ChatData(), Base

    data class TextWithImgChat(
        override val isMe: Boolean,
        val msg : String,
        val img : String
    ) : ChatData(), Base

    data class VideoChat(
        override val isMe : Boolean,
        val video : String
    ) : ChatData(), Base

    // 이미지 활영시에는 Img가 Bitmap으로 넘어옴
    data class BitImgChat(
        override val isMe: Boolean,
        val img : Bitmap
    ) : ChatData(), Base
}