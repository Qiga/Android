package com.example.androidstudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidstudy.data.PhotoData
import com.example.androidstudy.databinding.ItemPhotoLayoutBinding

class SelectPhotoAdapter(
    private var onClick : (String)-> Unit
): RecyclerView.Adapter<SelectPhotoAdapter.SelectHolder>() {

    private var photoList : List<PhotoData> = listOf()

    inner class SelectHolder(
        private var binding : ItemPhotoLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUrl : String) = with(binding) {
            Glide.with(binding.root)
                .load(imgUrl)
                .into(itemImg)

            itemImg.setOnClickListener {
                onClick.invoke(imgUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SelectHolder(ItemPhotoLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: SelectHolder, position: Int) {
        holder.bind(photoList[position].urls.thumb)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun setList(photoList : ArrayList<PhotoData>){
        this.photoList = photoList as List<PhotoData>
        notifyDataSetChanged()
    }
}