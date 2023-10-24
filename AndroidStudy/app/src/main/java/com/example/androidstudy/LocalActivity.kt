package com.example.androidstudy

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.example.androidstudy.databinding.ActivityLocalBinding

class LocalActivity : AppCompatActivity(){

    companion object {
        const val IMAGE_PERMISSION_REQUEST = 1000
        const val LOCAL_ACTIVITY_RESULT_CODE = 2000
        const val LOCAL_ACTIVITY_RESULT_KEY = "결과"
    }

    private var _binding : ActivityLocalBinding? = null
    private val binding get()=_binding!!
    private val startActivityForResult = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        val intent = Intent()
        intent.putExtras(bundleOf(LOCAL_ACTIVITY_RESULT_KEY to result))
        setResult(LOCAL_ACTIVITY_RESULT_CODE, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLocalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAddLocalGallery()
    }

    private fun initAddLocalGallery(){
        when {
//            todo 권한 확인 하는 것
            ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
                //권한이 잘 부여되었을떄 사진 선택 코드
                setLocalPhotos()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_MEDIA_IMAGES) -> {
                showPermissionPopup()
            }
            else ->{
                requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), IMAGE_PERMISSION_REQUEST)
            }
        }
    }

    private fun showPermissionPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다")
            .setMessage("앱에서 사진을 불러오기 위해 권한이 필요합니다")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), IMAGE_PERMISSION_REQUEST)
            }
            .setNegativeButton("취소하기") {_,_ ->
                finish()
            }
            .create()
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if(grantResults.isNotEmpty() && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    //todo 권한이 부여됨
                    setLocalPhotos()
                }else{
                    finish()
                }
            }
            else -> {}
        }
    }

    private fun setLocalPhotos(){
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type =
        startActivityForResult.launch("image/*")
    }
}