package com.example.timerecord.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timerecord.R
import com.example.timerecord.adapter.DailyWorkAdapter
import com.example.timerecord.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {

    private var _binding : FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var dailyWorkAdapter : DailyWorkAdapter? = null

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

    private fun setOnClick() {

    }

    private fun setAdapter() {
        dailyWorkAdapter = DailyWorkAdapter()
        binding.dailyWorkRV.apply{
            adapter = dailyWorkAdapter
        }
    }

    /**
     * BottomNavigation 동기화
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu)
    }

}