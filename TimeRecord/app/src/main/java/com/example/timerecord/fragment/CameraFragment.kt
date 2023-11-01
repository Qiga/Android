package com.example.timerecord.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.timerecord.databinding.FragmentCameraBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraFragment : Fragment() {

    companion object {
        const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
        const val CAMERA_REQUEST_CODE = 1000
    }

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkCameraPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //권한 허용 여부 확인을 위한  launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            //권환이 허용되었을 때
            Log.d("permission", "true")
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            getCameraLauncher.launch(intent)
        } else {
            //권한 허용 x
            showPermissionPopup()
        }
    }

    private fun startCameraPermissionRequest() {
        requestPermissionLauncher.launch(CAMERA_PERMISSION)
    }

    private fun showPermissionPopup() {
        AlertDialog.Builder(requireContext()).setTitle("권한이 필요합니다")
            .setMessage("앱에서 필요한 권한을 허용하시겠습니까").setPositiveButton("네") { _, _ ->
                startCameraPermissionRequest()
            }.setNegativeButton("아니오") { _, _ ->
                parentFragmentManager.beginTransaction().remove(this).commit()
            }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), CAMERA_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("permission", "true")
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                getCameraLauncher.launch(intent)
            }

            shouldShowRequestPermissionRationale(CAMERA_PERMISSION) -> {
                showPermissionPopup()
            }

            else -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                getCameraLauncher.launch(intent)
            }
        }
    }

    private val getCameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data
                if (data != null) {
                    val bmp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        data.extras?.getParcelable("data", android.graphics.Bitmap::class.java)
                    } else {
                        data.extras?.get("data") as Bitmap
                    }
                    saveImageToAppDirectory(bmp!!)
                }
            }
        }

    private fun saveImageToAppDirectory(imageBitmap: Bitmap) {
        val timestamp =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREAN).format(Date())

        val imageFileName = "img_${timestamp}"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/webp")

        val contentResolver = requireContext().contentResolver
        val imageUri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
        )

        imageUri?.let { uri ->
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.close()
            }
            // 이제 사진이 앱의 전용 디렉토리에 저장되었습니다.
        }
    }
//    private val getCameraContent =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val data = result.data
//
//
//
//                    val format =
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Bitmap.CompressFormat.WEBP_LOSSY else Bitmap.CompressFormat.WEBP
//
//                    //thread 작업으로 block되는 것을 방지 위함
//                    CoroutineScope(Dispatchers.IO).launch {
//                        try {
//                            val file = withContext(Dispatchers.IO) {
//                                File.createTempFile("jpeg_${timestamp}", ".jpg")
//                            }
//
//                            withContext(Dispatchers.IO) {
//                                FileOutputStream(file).use { result ->
//                                    bmp!!.compress(
//                                        format, 100, result
//                                    )
//                                }
//                            }
//                        } catch (e: Exception) {
//                            Log.e("이미지 오류", "bitmap 오류")
//                        }
//                    }
//                    bmp?.recycle()
//                }
//            }
//        }
//    private fun startCaptureLauncher(){
//        //파일을 분류하여 저장하기 위함
//        val timestamp: String =
//            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
//
//        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1001)
//        } else {
//            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//            // storageDir을 사용하여 작업을 수행
//        }
//        val file = File.createTempFile("jpg_${timestamp}", ".jpg")
//
//        val intent = Intent(MediaStore.ACTION_IMSimAGE_CAPTURE)
//        getCameraContent.launch(intent)
//    }

}