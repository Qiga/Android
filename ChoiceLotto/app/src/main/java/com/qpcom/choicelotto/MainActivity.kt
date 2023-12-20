package com.qpcom.choicelotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.qpcom.choicelotto.databinding.ActivityMainBinding
import com.qpcom.choicelotto.extension.showShortToast
import com.qpcom.choicelotto.ui.OptionDialog
import java.util.Random

class MainActivity : AppCompatActivity(){

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var optionDialog: OptionDialog
    private var _seed : Long? = null
    private val seed get() = _seed!!

    private var didRun = false

    /**
     * 숫자 중복 방지를 위해 Set 사용
     */
    private val pickNumberSet : MutableSet<Int> = mutableSetOf()
    private val showNumberSet : MutableSet<Int> = mutableSetOf()
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
        setBannerAds()
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
            getDefaultRandomNumber()
            renderingNumberSet()
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
            pickNumberSet.add(numberPicker1.value)
            showNumberSet.add(numberPicker1.value)
            renderingNumberSet()
        }

        resetButton.setOnClickListener {
            clearNumberText()
        }

        //seed 옵션 비 활성화
        optionButton.setOnClickListener {
            optionDialog = OptionDialog().apply{
                setCallBack( object : OptionDialog.CallBack{
                    override fun getSeed(s: String) {
                        Log.e("오류 알림", "seed옵션 비활성화")
                    }
                })
            }
            optionDialog.show(supportFragmentManager, "")
        }
    }

    /**
     * 랜덤 숫자 (기본 , 시드X, 광고X )
     */
    private fun getDefaultRandomNumber() {
        Log.d("st", "startDefaultNum")
        showNumberSet.clear()
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if(!pickNumberSet.contains(i)){
                    this.add(i)
                }
            }
        }
        numberList.shuffle()
        showNumberSet.addAll(pickNumberSet)
        showNumberSet.addAll(numberList.subList(pickNumberSet.size, 6))
        didRun = true
    }

    /**
     * 랜덤 숫자 생성 (옵션, 시드o, 광고x) - 랜덤값 생성 불안정으로 비활성화
     */
    private fun getSeedRandomNumber(seed : Long) {
        Log.d("st", "startSeedNum")
        showNumberSet.clear()
        showNumberSet.addAll(pickNumberSet)
        val rd = Random(seed)
        while(showNumberSet.size < 6){
            showNumberSet.add(rd.nextInt())
        }
        didRun = true
    }

    /**
     * view 전부 rendering
     */
    private fun renderingNumberSet() {
        val sortedShowSet = showNumberSet.toSortedSet()
        for(i in 0 until showNumberSet.size){
            val number = sortedShowSet.elementAt(i)
            numberTextViewList[i].apply{
                text = number.toString()
                background.setTint(getMatchColor(number))
                isVisible = true
            }
        }
    }

    /**
     * 숫자 초기화 함수
     */
    private fun clearNumberText() {
        pickNumberSet.clear()
        showNumberSet.clear()
        for(textView in numberTextViewList) {
            textView.apply{
                isVisible = false
            }
        }
        didRun = false
    }

    /**
     * 로또 번호에 따른 배경을 설정하기 위함
     */
    private fun getMatchColor(number : Int) : Int {
        val colorInt : Int = when((number-1)/10) {
            0 -> R.color.zeroLine
            1 -> R.color.tenLine
            2 -> R.color.twentyLine
            3 -> R.color.thirtyLine
            4 -> R.color.fortyLine
            else -> {R.color.zeroLine}
        }
        return getColor(colorInt)
    }

    private fun setBannerAds() {
        MobileAds.initialize(this)          // 1) 광고 SDK 초기화
        val adRequest = AdRequest.Builder().build()   // 2)
        binding.adView.loadAd(adRequest)            // 3)

        // 4) 애드뷰 리스너 추가
        binding.adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.d("ads log", "배너 광고가 로드되었습니다.") // 로그 출력
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("ads log", "배너 광고가 로드 시패했습니다. ${adError.responseInfo}")
            }

            override fun onAdOpened() {
                Log.d("ads log", "배너 광고를 열었습니다.")
                // 전면 에 광고가 오버레이 되었을 때
            }

            override fun onAdClicked() {
                Log.d("ads log", "배너 광고를 클랙했습니다.")
            }

            override fun onAdClosed() {
                Log.d("ads log", "배너 광고를 닫았습니다.")
            }
        }
    }
}