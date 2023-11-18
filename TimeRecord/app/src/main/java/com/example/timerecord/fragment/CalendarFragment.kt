package com.example.timerecord.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timerecord.R
import com.example.timerecord.adapter.DailyWorkAdapter
import com.example.timerecord.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var dailyWorkAdapter: DailyWorkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setOnClick()
    }

    private fun setOnClick() = with(binding) {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            Log.d("date", year.toString() + month.toString() + dayOfMonth.toString())
        }
        addWorkBtn.setOnClickListener {
            popAddDialog()
        }
    }

    private fun setAdapter() {
        dailyWorkAdapter = DailyWorkAdapter()
        binding.dailyWorkRV.apply {
            adapter = dailyWorkAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    /**
     * 일정 추가 Dialog 띄우기
     */
    private fun popAddDialog() {
        
    }

    /**
     * BottomNavigation 동기화
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu)
    }
}