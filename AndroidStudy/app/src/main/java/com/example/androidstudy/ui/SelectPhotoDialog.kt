package com.example.androidstudy.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy.adapter.SelectPhotoAdapter
import com.example.androidstudy.api.UnsplashClient
import com.example.androidstudy.data.PhotoData
import com.example.androidstudy.databinding.DialogMenuOptionBinding
import com.example.androidstudy.databinding.DialogSelectPhotoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SelectPhotoDialog (
    private var onClick : (String) -> Unit
) : DialogFragment(){

    private var _binding : DialogSelectPhotoBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectAdapter : SelectPhotoAdapter
    private var photoList = arrayListOf<PhotoData>()

    override fun onStart() {
        super.onStart()
        dialog?.apply{
            isCancelable = true
            setCanceledOnTouchOutside(true)
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1500)
                setGravity(Gravity.BOTTOM)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundleOf("SELECT_PHOTO" to "")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSelectPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireContext()) {
            override fun onBackPressed() {
                dismiss()
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApi()
        setAdapter()
    }

    private fun setAdapter() {
        selectAdapter = SelectPhotoAdapter(onClick = { imgUrl ->
            onClick.invoke(imgUrl)
            dismiss()
        })
        selectAdapter.setList(photoList)
        binding.photoRV.apply{
            adapter = selectAdapter
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }
    }

    private fun callApi() {
        CoroutineScope(Dispatchers.Main).launch{
            try {
                val result = withContext(Dispatchers.IO) {
                    UnsplashClient.unsplashApiService.getItemWithName(null)
                }

                Log.d("dhk", "rufrhk")
                (binding.photoRV.adapter as SelectPhotoAdapter).setList(result)
            }catch (e : Exception){
                Log.d("error", e.toString())
            }
        }
    }
}