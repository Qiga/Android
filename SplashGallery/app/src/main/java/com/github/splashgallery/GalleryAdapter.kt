package com.github.splashgallery

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.splashgallery.databinding.ItemGalleryLayoutBinding
import com.github.splashgallery.extension.dLog
import com.github.splashgallery.model.PhotoData
import com.github.splashgallery.ui.DetailFragment
import com.github.splashgallery.ui.GalleryFragment

class GalleryAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {

    lateinit var photoList: ArrayList<PhotoData>

    inner class GalleryHolder(private val binding: ItemGalleryLayoutBinding) :
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
        return GalleryHolder(ItemGalleryLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        holder.bind(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<PhotoData>) {
        "jo".dLog("진입")
        photoList = list
        notifyDataSetChanged()
    }
}