package com.example.androidstudy.ui

import android.R.attr.bitmap
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.androidstudy.databinding.FragmentAddBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class AddFragment(
    private val setDialog: () -> Unit,
    private val setLocalImg: (Uri) -> Unit,
    private val setVideo: (Uri) -> Unit,
    private val setCaptureImg : (Uri) -> Unit
) : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val getCameraContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
//
//                val bitmap =  it.data!!.extras!!.get("data") as Bitmap
//                var imageUri : Uri? = null;

//                try {
//                    // Create a temporary file to store the image
////                    val file : File = File.createTempFile("jpeg",".jpg","image.jpg")
//
//                    // Write the Bitmap data to the file
//                    val fos : FileOutputStream = FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                    fos.close();
//
//                    // Get a Uri for the file using FileProvider (requires proper setup in the manifest)
//                    imageUri = FileProvider.getUriForFile(requireContext(), "your.package.name.fileprovider", file);
//                } catch (e : Exception){
//
//                }

                parentFragmentManager.beginTransaction().hide(this).commit()
            }
        }

    private val getImgContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                setLocalImg.invoke(uri)
                parentFragmentManager.beginTransaction().hide(this).commit()
            }
        }

    private val getVideoContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                setVideo.invoke(uri)
                parentFragmentManager.beginTransaction().hide(this).commit()
            }
        }

    companion object {
        const val IMG_PERMISSION = android.Manifest.permission.READ_MEDIA_IMAGES
        const val VIDEO_PERMISSION = android.Manifest.permission.READ_MEDIA_VIDEO
        const val IMG_PERMISSION_REQUEST = 1001
        const val VIDEO_PERMISSION_REQUEST = 1002
        const val IMG_REQUEST_CODE = 2001
        const val VIDEO_REQUEST_CODE = 2002
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick() = with(binding) {
        setPhotoDialogButton.setOnClickListener {
            setDialog.invoke()
            parentFragmentManager.popBackStack()
        }
        setCameraButton.setOnClickListener {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), IMG_PERMISSION_REQUEST)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            getCameraContent.launch(intent)
        }
        setGalleryButton.setOnClickListener {
            checkGalleryPermission()
        }
        setVideoButton.setOnClickListener {
            checkVideoPermission()
        }
    }

    /**
     * 이미지 권한 확인 후, 바로 이미지 선택할 수 있도록 구성
     */
    private fun checkGalleryPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), IMG_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED -> {
                showImg()
            }
            shouldShowRequestPermissionRationale(IMG_PERMISSION) -> {
                showPermissionPopup(IMG_PERMISSION)
            }
            else -> {
                requestPermissions(arrayOf(IMG_PERMISSION), IMG_PERMISSION_REQUEST)
            }
        }
    }


    private fun showImg() {
        getImgContent.launch("image/*")
    }

    /**
     * 비디오 권한 확인 후, 바로 동영상 확인 할 수 있도록 구성
     */
    private fun checkVideoPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), VIDEO_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED -> {
                showVideo()
            }

            shouldShowRequestPermissionRationale(VIDEO_PERMISSION) -> {
                showPermissionPopup(VIDEO_PERMISSION)
            }

            else -> {
                requestPermissions(arrayOf(VIDEO_PERMISSION), VIDEO_PERMISSION_REQUEST)
            }
        }
    }


    private fun showVideo() {
        getVideoContent.launch("video/*")
    }


    /**
     * 이전에 권한 거부 했을때, 재 확인을 위해 띄워주는 팝업창
     */
    private fun showPermissionPopup(permission: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("권한이 필요합니다")
            .setMessage("앱에서 필요한 권한을 허용하겠습니까")
            .setPositiveButton("응") { _, _ ->
                requestPermissions(arrayOf(permission), VIDEO_PERMISSION_REQUEST)
            }
            .setNegativeButton("Nope") { _, _ -> }
            .create()
            .show()
    }
}