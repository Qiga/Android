package com.github.cafe;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.cafe.data.PhotoData
import com.github.cafe.databinding.ItemPhotoLayoutBinding
import com.github.cafe.ui.DetailFragment
import com.github.cafe.ui.GalleryFragment

class GalleryAdapter(val fragmentManager : FragmentManager) : RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {

    lateinit var photoList: List<PhotoData>
    private lateinit var detailFragment: Fragment

    inner class GalleryHolder(private val binding: ItemPhotoLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) = with(binding) {
            val photoData = photoList[position]

            Glide.with(binding.root)
                .load(photoData.urls.regular)
                .into(photoIn)

            //사진을 클릭하면 해당 정보를 전달
            photoIn.setOnClickListener {
                detailFragment = DetailFragment().apply {
                    arguments = bundleOf(GalleryFragment.BUNDLE_URI to  photoData.urls.regular)
                }
                fragmentManager.beginTransaction()
                    .replace(R.id.detailContainer, detailFragment)
                    .addToBackStack(null)
                    .commit()
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

    fun setList(photoList: List<PhotoData>) {
        this.photoList = photoList
    }
}

