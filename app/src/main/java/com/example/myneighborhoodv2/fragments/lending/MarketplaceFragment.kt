package com.example.myneighborhoodv2.fragments.lending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myneighborhoodv2.R
import com.example.myneighborhoodv2.adapters.MarketplaceViewpagerAdapter
import com.example.myneighborhoodv2.databinding.FragmentMarketplaceBinding
import com.example.myneighborhoodv2.fragments.categories.AppliancesFragment
import com.example.myneighborhoodv2.fragments.categories.FoodItemsFragment
import com.example.myneighborhoodv2.fragments.categories.FurnitureFragment
import com.example.myneighborhoodv2.fragments.categories.MainCategoryFragment
import com.example.myneighborhoodv2.fragments.categories.OtherFragment
import com.example.myneighborhoodv2.fragments.categories.VehicleFragment
import com.google.android.material.tabs.TabLayoutMediator


class MarketplaceFragment: Fragment(R.layout.fragment_marketplace){
    private lateinit var binding: FragmentMarketplaceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarketplaceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf(
            MainCategoryFragment(),
            AppliancesFragment(),
            FoodItemsFragment(),
            FurnitureFragment(),
            VehicleFragment(),
            OtherFragment()
        )

        binding.viewpagerMarketplace.isUserInputEnabled = false

        val viewPager2Adapter = MarketplaceViewpagerAdapter(categoriesFragments,childFragmentManager, lifecycle)
        binding.viewpagerMarketplace.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpagerMarketplace){tab, position ->
            when(position){
                0 -> tab.text = "Featured"
                1 -> tab.text = "Appliances"
                2 -> tab.text = "Food Items"
                3 -> tab.text = "Furniture"
                4 -> tab.text = "Vehicles"
                5 -> tab.text = "Other"
            }
        }.attach()
    }
}