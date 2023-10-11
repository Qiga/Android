package com.github.splashgallery.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.splashgallery.GalleryAdapter
import com.github.splashgallery.api.Unsplash
import com.github.splashgallery.databinding.FragmentGalleryBinding
import com.github.splashgallery.model.PhotoData
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private var photoList: ArrayList<PhotoData> = arrayListOf()
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setApi()
        setAdapter()
    }

    private fun setAdapter() {
        galleryAdapter = GalleryAdapter()
        galleryAdapter?.setList(photoList)
        binding.galleryList.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }
    }

    private fun setApi() {
        MainScope().launch(){
            try {
                Unsplash.unsplashService.getItemWithName(null).let {
                    (binding.galleryList.adapter as? GalleryAdapter)?.apply {
                        this.photoList = it
                        notifyDataSetChanged()
                    }

                }
            } catch (e : Exception){
                Log.e("error", e.toString())
            }
        }
    }
}