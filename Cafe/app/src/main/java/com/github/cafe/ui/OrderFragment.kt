package com.github.cafe.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.cafe.OrderAdapter
import com.github.cafe.R
import com.github.cafe.data.Menu
import com.github.cafe.data.MenuData
import com.github.cafe.data.Temperature
import com.github.cafe.databinding.FragmentCafeBinding
import com.github.cafe.databinding.FragmentOrderBinding
import com.github.cafe.extension.showToast
import kotlin.collections.ArrayList

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private var orderAdapter : OrderAdapter? = null
    private var orderList = ArrayList<MenuData>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onClick() = with(binding) {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setAdapter() {
        orderAdapter= OrderAdapter()
        orderAdapter?.setList(orderList)
        binding.listView.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val value = arguments?.let { bundle ->
            orderList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelableArrayList("order", MenuData::class.java) as ArrayList<MenuData>
            } else {
                bundle.getParcelableArrayList<MenuData>("order") as ArrayList<MenuData>
            }
        }
        orderList.forEach {menuData ->
         Log.d("menu", menuData.toString())
        }
        setAdapter()
        onClick()

    }
}