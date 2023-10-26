package com.example.androidstudy.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.androidstudy.data.TodoData
import com.example.androidstudy.databinding.DialogTodoWriteBinding

class TodoWriteDialog : DialogFragment(){

    var result : ((String) -> Unit)? = null

    private var _binding : DialogTodoWriteBinding? = null
    private val binding get()=_binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("dialog", "2")
//        setStyle()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireContext(), theme){
            override fun onBackPressed() {
                dismiss()
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogTodoWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick() = with(binding) {
        binding.todoSendBtn.setOnClickListener{
            result?.invoke(binding.todoWrite.text.toString())
            dismiss()
        }
    }
}