package com.example.timerecord.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
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
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.timerecord.databinding.FragmentCameraBinding
import com.google.common.util.concurrent.ListenableFuture
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
            setCameraProvider()
        } else {
            //권한 허용 x
            showPermissionPopup()
        }
    }

    private fun startCameraPermissionRequest() {
        requestPermissionLauncher.launch(CAMERA_PERMISSION)
    }

    //권한요청을 위한 알람 Dialog ( 거부 시 )
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
                setCameraProvider()
            }

            shouldShowRequestPermissionRationale(CAMERA_PERMISSION) -> {
                showPermissionPopup()
            }

            else -> {
                startCameraPermissionRequest()
            }
        }
    }

    /**
     * CameraX를 활용한 카메라 설정
     */
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>

    private fun setCameraProvider(){
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        checkUseAble()
    }
    private fun checkUseAble(){
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder().build()
        val cameraSelector : CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider())
        var camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
    }

    /**
     * 기본 카메라 서비스
     */
    //기본 카메라를 통해서 이미지 캡쳐를 하기 위한 launcher
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

    //기본 카메라로 bitmap 이미지를 가져왔을 때 디바이스에 저장하기 위한 메서드
    private fun saveImageToAppDirectory(imageBitmap: Bitmap) {
        val timestamp =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREAN).format(Date())

        val imageFileName = "img_${timestamp}"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")

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

}