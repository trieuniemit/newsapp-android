package com.newsapp.adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.newsapp.activities.main.category.CategoryViewModel
import com.newsapp.activities.main.category.ViewPagerFragment
import com.newsapp.activities.main.home.HomeFragment

class CategoryPagerAdapter(fm: FragmentManager, private val viewModel: CategoryViewModel) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ViewPagerFragment(viewModel, position)
    }

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> "Nông nghiệp"
        1 -> "Đời sống"
        2 -> "Môi trường"
        3 -> "Thể thao"
        4 -> "Du lịch"
        5 -> "Chính trị & pháp luật"
        6 -> "Xã hội"
        else -> ""
    }

    override fun getCount(): Int = 5
}