package com.github.cafe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.cafe.R
import com.github.cafe.data.CoffeeOption
import com.github.cafe.data.MenuData
import com.github.cafe.data.Temperature
import com.github.cafe.databinding.FragmentCafeBinding
import com.github.cafe.extension.showToast

class CafeFragment : Fragment() {

    private var _binding: FragmentCafeBinding? = null
    private val binding get() = _binding!!
    private var orderFragment : OrderFragment? = null

    private val cafeMenuList = arrayListOf<MenuData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCafeBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun setList(list : ArrayList<MenuData>){
        cafeMenuList.clear()
        cafeMenuList.addAll(list)
    }

    private fun onClick() = with(binding) {
        menu1.setOnClickListener {
            val americano = MenuData.CoffeeData(
                "americano",
                2500,
                Temperature.Ice,
                CoffeeOption.Vanilla
            )
            addCafeMenu(americano)
        }

        menu2.setOnClickListener {
            val cafeLatte = MenuData.CoffeeData(
                "cafeLatte",
                3000,
                Temperature.Ice,
                CoffeeOption.None
            )
            addCafeMenu(cafeLatte)
        }

        menu3.setOnClickListener {
            val vanillaLatte = MenuData.CoffeeData(
                "vanillaLatte",
                3000,
                Temperature.Ice,
                CoffeeOption.None
            )
            addCafeMenu(vanillaLatte)
        }
        count.setOnClickListener {
            orderFragment = OrderFragment().apply{
                arguments = bundleOf("order" to cafeMenuList)
            }
// null 체크하여 not null 체크

            binding.menuContainer.isVisible=true
            orderFragment?.let{ fragment->
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val value = arguments?.getString("key") ?: ""
        requireContext().showToast(value)
        onClick()
    }

}