package com.example.androidstudy.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.androidstudy.R
import com.example.androidstudy.data.MenuData
import com.example.androidstudy.databinding.FragmentCafeBinding
import com.example.androidstudy.extension.showToast
import com.example.androidstudy.ui.MenuOptionDialog.Companion.MENU_KEY
import com.example.androidstudy.ui.MenuOptionDialog.Companion.TAG

class CafeFragment : Fragment() {

    private var _binding: FragmentCafeBinding? = null
    private val binding get() = _binding!!
    private var orderFragment: OrderFragment? = null
    private val cafeMenuList: ArrayList<MenuData> = arrayListOf<MenuData>()
    private var optionDialog: MenuOptionDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCafeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val value = arguments?.getString("key") ?: ""
        requireContext().showToast(value)
        onClick()
    }

    private fun onClick() = with(binding) {
        menu1.setOnClickListener {
            showOptionDialog("아메리카노", true)
        }

        menu2.setOnClickListener {
            showOptionDialog("카페라떼", true)
        }

        menu3.setOnClickListener {
            showOptionDialog("바닐라라떼", true)
        }
        count.setOnClickListener {
            orderFragment = OrderFragment().apply {
                arguments = bundleOf("order" to cafeMenuList)
            }

            binding.menuContainer.isVisible = true
            orderFragment?.let { fragment ->
                childFragmentManager.beginTransaction()
                    .add(R.id.menuContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun addCafeMenu(menu: MenuData) = with(binding) {
        cafeMenuList.add(menu)
        count.text = cafeMenuList.size.toString()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /**
     * 메뉴에 선택에 따른 Dialog 띄우기 ( 커피 메뉴 구분 )
     */
    private fun showOptionDialog(menuName: String, isAmericano: Boolean) = with(binding) {

        fun newInstance(data: String): MenuOptionDialog {
            val fragment = MenuOptionDialog(isAmericano, dismissResult = { menuData ->
                Log.d("주문성공", "감사합니다")
                addCafeMenu(menuData)
                childFragmentManager.popBackStack()
                optionDialog?.dismiss()
                optionDialog = null
            })
            fragment.arguments = bundleOf(
                MENU_KEY to data
            )
            return fragment
        }

        optionDialog = newInstance(menuName)
        binding.optionContainer.isVisible = true
        optionDialog?.show(childFragmentManager, TAG)
    }
}