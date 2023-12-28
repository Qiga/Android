package com.qpcom.choicelotto

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.qpcom.choicelotto.databinding.ActivityIntroBinding


class IntroActivity : AppCompatActivity() {

    private var _binding : ActivityIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler().apply{
            postDelayed(Runnable {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 1500)
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}