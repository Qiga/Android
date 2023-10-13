package com.example.androidstudy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.androidstudy.data.PhotoData
import com.example.androidstudy.databinding.ItemPhotoLayoutBinding

class GalleryAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {

    lateinit var photoList: ArrayList<PhotoData>

    inner class GalleryHolder(private val binding: ItemPhotoLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) = with(binding) {
            val photoData = photoList[position]
            Glide.with(binding.root)
                .load(photoData.urls.thumb)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(itemImg)

            //사진을 클릭하면 해당 정보를 전달
            itemImg.setOnClickListener {
                onClick.invoke(photoData.urls.regular)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GalleryHolder(ItemPhotoLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        holder.bind(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<PhotoData>) {
        photoList = list
        notifyDataSetChanged()
    }
}