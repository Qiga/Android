package com.example.androidstudy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy.adapter.TodoAdapter
import com.example.androidstudy.data.TodoData
import com.example.androidstudy.databinding.FragmentTodoBinding

class TodoFragment : Fragment(){

    private var _binding : FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private var todoList : MutableList<TodoData> = mutableListOf()
    private lateinit var todoAdapter : TodoAdapter
    private lateinit var todoDialog : TodoWriteDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        onClick()
    }

    private fun onClick() = with(binding){
        todoAddButton.setOnClickListener{
            setDialog()
        }
    }

    private fun setAdapter() {
        todoAdapter = TodoAdapter()
        todoAdapter.addTodo(todoList)
        binding.todoRV.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setDialog(){
        todoDialog = TodoWriteDialog().apply {
            result = {string ->
                todoAdapter.addTodoOne(TodoData(string))
            }
        }
        todoDialog.show(childFragmentManager, "")
    }

}