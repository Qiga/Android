package com.example.androidstudy.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy.data.TodoData
import com.example.androidstudy.databinding.ItemTodoLayoutBinding

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoHolder>() {

    private var todoList : MutableList<TodoData> = mutableListOf()
    inner class  TodoHolder(private val binding : ItemTodoLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(todo : TodoData) = with(binding) {
                todoText.text = todo.text
                todoCheck.isChecked = todo.complete

                todoCheck.setOnCheckedChangeListener{_,isCheck->
                    todo.complete = isCheck
                    binding.todoText.paintFlags = if(todo.complete){
                        todoText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }else 0
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TodoHolder(ItemTodoLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.bind(todoList[position])
    }

    fun addTodo(list: MutableList<TodoData>){
        todoList.clear()
        todoList.addAll(list)
        notifyDataSetChanged()
    }

    fun addTodoOne(todoData: TodoData){
        Log.d("wqdwq",todoList.lastIndex.toString())
        todoList.add(todoList.lastIndex+1, todoData)
        notifyItemInserted(todoList.lastIndex)
    }
}