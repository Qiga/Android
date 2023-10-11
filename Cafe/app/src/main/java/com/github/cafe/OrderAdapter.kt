package com.github.cafe

import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.github.cafe.data.MenuData
import com.github.cafe.databinding.ItemOrderLayoutBinding

class OrderAdapter() : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    var orderList = ArrayList<MenuData>()

    inner class OrderViewHolder(private val binding: ItemOrderLayoutBinding)
        : RecyclerView.ViewHolder(binding.root){

            fun bind(position : Int) = with(binding){
                Log.d("bind ", "bind")
                val menuData = orderList[position]
                menuName.text = menuData.name
                price.text = menuData.price.toString()
//                option.text = menuData.option.toString()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrderViewHolder(ItemOrderLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int = orderList.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setList(orderList: ArrayList<MenuData>) {
        this.orderList.clear()
        this.orderList.addAll(orderList)
    }

}