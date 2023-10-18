package com.github.dalle.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.dalle.databinding.ItemLiveViewPagerBinding
import com.github.dalle.model.PagerViewModel

class LiveViewPagerAdapter : RecyclerView.Adapter<LiveViewPagerAdapter.LiveViewPagerHolder>() {

    private var photoUrlList: List<String> = listOf()

    inner class LiveViewPagerHolder(private val binding: ItemLiveViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photoUrl: String) {
            Glide.with(binding.root)
                .load(photoUrl)
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

    fun setList(list : MutableList<String>){
        photoUrlList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: LiveViewPagerHolder, position: Int) {
        holder.bind(photoUrlList[position])
    }
}