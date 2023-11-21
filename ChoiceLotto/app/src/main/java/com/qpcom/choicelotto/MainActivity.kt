package com.qpcom.choicelotto

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.qpcom.choicelotto.databinding.ActivityMainBinding
import com.qpcom.choicelotto.extension.showShortToast

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var didRun = false
    private val pickNumberSet : MutableSet<Int> = mutableSetOf()
    private val numberTextViewList : List<TextView> by lazy {
        listOf(
            binding.numberTextView1, binding.numberTextView2, binding.numberTextView3,
            binding.numberTextView4, binding.numberTextView5, binding.numberTextView6
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNumberPicker()
        onClick()
    }

    /**
     * 로또 번호 범위 설정 1~45
     */
    private fun setNumberPicker() {
        binding.numberPicker1.apply{
            minValue = 1
            maxValue = 45
        }
    }


    private fun onClick() = with(binding){
        createRandomButton.setOnClickListener{
            clearNumberText()
            val list = getDefaultRandomNumber()
            pickNumberSet.addAll(list)
            for(i in 0 until pickNumberSet.size){
                numberTextViewList[i].apply{
                    text = pickNumberSet.elementAt(i).toString()
                    isVisible = true
                }
            }
        }

        addButton.setOnClickListener{
            if(didRun){
                showShortToast("초기화후에 다시 시도해주세요")
                return@setOnClickListener
            }
            if(pickNumberSet.size >= 6){
                showShortToast("더이상 추가할 수 없습니다")
                return@setOnClickListener
            }
            if(pickNumberSet.contains(binding.numberPicker1.value)){
                showShortToast("이미 선택한 번호입니다")
                return@setOnClickListener
            }
            numberTextViewList[pickNumberSet.size].apply{
                text = numberPicker1.value.toString()
                isVisible = true
            }
            pickNumberSet.add(numberPicker1.value)
        }

        resetButton.setOnClickListener {
            clearNumberText()
        }
    }

    /**
     * 랜덤 숫자 (기본 , 시드X, 광고X )
     */
    private fun getDefaultRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                this.add(i)
            }
        }
        numberList.shuffle()
        return numberList.subList(0, 6)
    }

    private fun clearNumberText() {
        pickNumberSet.clear()
        for(textView in numberTextViewList) {
            textView.apply{
                isVisible = false
            }
        }
    }
}