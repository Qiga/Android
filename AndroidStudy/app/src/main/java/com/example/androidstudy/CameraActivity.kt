package com.example.androidstudy

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.androidstudy.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    private var _binding : ActivityCameraBinding? = null
    private val binding get() = _binding!!
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 1000
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
    private val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        checkCameraPermission()
        onClick()
    }

    private fun checkCameraPermission() {
        if(allPermissionsGranted()){
            startCamera(binding.viewFinder)
        }else{
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_PERMISSIONS) {
            startCamera(binding.viewFinder)
        }else {
            finish()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all{
        ContextCompat.checkSelfPermission(
            baseContext , it
        )== PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera(viewFinder: PreviewView){
        Log.d("권한 인정", "확인")
    }

    private fun onClick() = with(binding) {
        imageCaptureButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_CAMERA_BUTTON)
            startActivityForResult.launch(intent)
        }
    }

}