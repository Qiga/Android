package com.qpcom.choicelotto.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.qpcom.choicelotto.databinding.DialogOptionBinding
import javax.security.auth.callback.Callback

class OptionDialog() : DialogFragment() {

    private var _binding: DialogOptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Dialog 생성하고 반환 ( 뒤로가기 구현 )
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick() = with(binding) {
        setSeedButton.setOnClickListener {
            callBack.getSeed(seedInputText.text.toString())
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    /**
     * Dialog -> Activity 로 Seed 값 전송을 위한 코드
     * --St--
     */
    private lateinit var callBack : CallBack

    fun setCallBack(operation : CallBack) {
        callBack = operation
    }

    interface CallBack {
        fun getSeed( s : String )
    }
    /**
     * --End--
     */

}