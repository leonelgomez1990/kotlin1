package com.lfg.masterdetail.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lfg.masterdetail.fragments.DetailFragment
import com.lfg.masterdetail.fragments.NewUserFragment

class ViewPagerAdapter (fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DetailFragment()
            1 -> NewUserFragment()

            else -> DetailFragment()
        }

    }

    companion object {
        private const val TAB_COUNT = 2
    }

}