package com.lfg.masterdetail.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lfg.masterdetail.R
import com.lfg.masterdetail.adapters.ViewPagerAdapter
import com.lfg.masterdetail.viewmodels.ContainerViewModel

class ContainerFragment : Fragment() {

    companion object {
        fun newInstance() = ContainerFragment()
    }

    private lateinit var viewModel: ContainerViewModel
    lateinit var v : View
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.container_fragment, container, false)
        viewPager = v.findViewById(R.id.view_pager)
        tabLayout = v.findViewById(R.id.tabLayout)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContainerViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        viewPager.setAdapter(ViewPagerAdapter(requireActivity()))
        // viewPager.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> tab.text = "Tab1"
                1 -> tab.text = "Tab2"
                else -> tab.text = "undefined"
            }
        }).attach()

    }

}