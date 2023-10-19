package com.github.dalle.adpater

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.dalle.data.UserImg
import com.github.dalle.databinding.ItemLiveViewPagerBinding

class LiveViewPagerAdapter : RecyclerView.Adapter<LiveViewPagerAdapter.LiveViewPagerHolder>() {

    private var photoUrlList: MutableList<UserImg> = mutableListOf()

    inner class LiveViewPagerHolder(private val binding: ItemLiveViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photoUrl: UserImg) {
            Glide.with(binding.root)
                .load(photoUrl.urls.regular)
                .into(binding.liveViewPagerImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveViewPagerHolder {
        return LiveViewPagerHolder(
            ItemLiveViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return photoUrlList.size
    }

    fun setList(list : MutableList<UserImg>){
        Log.d("세팅", list.size.toString())
        photoUrlList.clear()
        photoUrlList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: LiveViewPagerHolder, position: Int) {
        holder.bind(photoUrlList[position])
    }
}