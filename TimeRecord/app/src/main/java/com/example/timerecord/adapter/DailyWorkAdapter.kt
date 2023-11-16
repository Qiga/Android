package com.example.timerecord.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.timerecord.data.Work
import com.example.timerecord.databinding.ItemDailyWorkBinding
import java.text.DateFormat
import kotlin.math.exp

class DailyWorkAdapter : RecyclerView.Adapter<DailyWorkAdapter.DailyWorkHolder>() {

    private var workList : ArrayList<Work> = arrayListOf(Work(null, "title", true),Work(null, "title", true),Work(null, "title", true),Work(null, "title", true),Work(null, "title", true),Work(null, "title", true),Work(null, "title", true))

    inner class DailyWorkHolder(private val binding : ItemDailyWorkBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(position : Int) = with(binding) {
                val work = workList[position]
                titleTV.text = work.title
            }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWorkHolder {
        return DailyWorkHolder(ItemDailyWorkBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return workList.size
    }

    override fun onBindViewHolder(holder: DailyWorkHolder, position: Int) {
        holder.bind(position)
    }
}