package com.github.splashgallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.splashgallery.databinding.ItemGalleryLayoutBinding
import com.github.splashgallery.model.PhotoData

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {

    lateinit var photoList: ArrayList<PhotoData>

    inner class GalleryHolder(private val binding: ItemGalleryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) = with(binding) {
            val photoData = photoList[position]
            Glide.with(binding.root)
                .load(photoData.urls.regular)
                .into(itemImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GalleryHolder(ItemGalleryLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        holder.bind(position)
    }

    fun setList(list : ArrayList<PhotoData>){
        photoList = list
    }
}