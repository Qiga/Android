package com.example.androidstudy.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.androidstudy.R
import com.example.androidstudy.data.CoffeeOption
import com.example.androidstudy.data.MenuData
import com.example.androidstudy.data.Temperature
import com.example.androidstudy.databinding.DialogMenuOptionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuOptionDialog(
    private val isAmericano: Boolean,
    private var dismissResult : ((MenuData)-> Unit)
    ): BottomSheetDialogFragment() {

    /**
     * 사용자가 선택한 메뉴 정보에 대한 Key값 (String 이름)
     */
    companion object {
        const val MENU_KEY = "menu"
        val TAG = MenuOptionDialog::class.java.simpleName
    }

    private var _binding: DialogMenuOptionBinding? = null
    private val binding get() = _binding!!
    private var temperatureOption = Temperature.Hot
    private var coffeeOption = CoffeeOption.None
    var menu = ""

    /**
     * Dialog 생성 후 반환
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireContext()) {
            //dialog에는 onBackPressed가 살아있는..?
            override fun onBackPressed() {
                dismiss()
            }
        }
        return dialog
    }

    /**
     * 스타일 설정, arguments 받기
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {bundle ->
            menu = bundle.getString(MENU_KEY) ?: ""
            Log.d("wd",menu)
        }
    }

    /**
     *시작 시 Dialog 설정 ( dialog 취소 설정, 레이아웃, 크기 위치 조정)
     */
    override fun onStart() {
        super.onStart()
        dialog?.apply {
            isCancelable = true
            setCanceledOnTouchOutside(true)
        }

        /**
         * 레이아웃 크기, 위치 조정
         */
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    /**
     * Dialog "Fragment"처럼 View 생성
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMenuOptionBinding.inflate(inflater, container, false)
        return binding.root
    }


    /**
     * 보여줄 옵션과 옵션 선택에 따른 이벤트 설정
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * 커피 옵션 표시 여부
         */
        if (isAmericano) {
            binding.addOptionLayout.isVisible = true
        }

        /**
         * 옵션 변경 감지
         */
        binding.apply {
            temperatureGroup.setOnCheckedChangeListener(temperatureCheckedChangedListener)
            addGroup.setOnCheckedChangeListener(optionCheckedChangedListener)
        }
        setOnClick()
    }


    /**
     * Dialog, View 제거
     */
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    /**
     * 옵션 선택에 따른 listener설정
     */
    private val temperatureCheckedChangedListener = RadioGroup.OnCheckedChangeListener { radioGroup, checkedId ->
        when (checkedId) {
            binding.hotOption.id -> temperatureOption = Temperature.Hot
            binding.coldOption.id -> temperatureOption = Temperature.Ice
        }
    }

    private val optionCheckedChangedListener = RadioGroup.OnCheckedChangeListener { radioGroup, checkedId ->
        when (checkedId) {
            binding.vanillaOption.id -> coffeeOption = CoffeeOption.Vanilla
            binding.almondOption.id -> coffeeOption = CoffeeOption.Almond
            binding.noneOption.id -> coffeeOption = CoffeeOption.None
        }
    }

    /**
     * 클릭 이벤트 ( 주문, 취소에 대한 이벤트 설정 )
     */
    private fun setOnClick() = with(binding) {
        orderButton.setOnClickListener {
            Log.d("주문하기", menu)
            when (menu) {
                "아메리카노" ->{
                    val americano = MenuData.CoffeeData(
                        "americano",
                        4000,
                        temperatureOption,
                        coffeeOption
                    )
                    dismissResult?.invoke(americano)

                }
                "카페라떼" -> {
                    val latte = MenuData.CoffeeData(
                        "latte",
                        4500,
                        temperatureOption,
                        coffeeOption
                    )
                    dismissResult?.invoke(latte)
                }
                "바닐라라떼" -> {
                    val vanillaLatte = MenuData.CoffeeData(
                        "vanillaLatte",
                        5000,
                        temperatureOption,
                        coffeeOption
                    )
                    dismissResult?.invoke(vanillaLatte)
                }
                "바닐라크림프라푸치노" -> {
                    val vanillaLatte = MenuData.CoffeeData(
                        "VanillaCreamFrappuccino",
                        6000,
                        temperatureOption,
                        coffeeOption
                    )
                    dismissResult?.invoke(vanillaLatte)
                }
            }
        }

        /**
         * dismiss() : dialog 지우고, 화면 지우는 method
         */
        cancelButton.setOnClickListener { dismiss() }
    }
}