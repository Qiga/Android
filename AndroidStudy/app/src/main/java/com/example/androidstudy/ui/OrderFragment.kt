package com.example.androidstudy.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy.adapter.OrderAdapter
import com.example.androidstudy.data.MenuData
import com.example.androidstudy.databinding.FragmentOrderBinding
import kotlin.collections.ArrayList

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private var orderAdapter: OrderAdapter? = null
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
        orderAdapter = OrderAdapter()
        orderAdapter?.setList(orderList)
        binding.listView.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    /**
     * bundle을 통해 주문 목록 GET( 버전 처리 ), Adapter Attach, 클릭 설정
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            orderList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelableArrayList("order", MenuData::class.java) as ArrayList<MenuData>
            } else {
                bundle.getParcelableArrayList<MenuData>("order") as ArrayList<MenuData>
            }
        }
        orderList.forEach { menuData ->
            Log.d("menu", menuData.toString())
        }
        setAdapter()
        onClick()
    }
}