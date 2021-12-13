package com.example.stagemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.stagehelper.MyPagerAdapter
import com.example.stagemanager.databinding.FragmentActivityMainBinding
import com.example.stagemanager.mainview.grouplist.GroupListFragment
import com.example.stagemanager.mainview.projectlist.ProjectListFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ActivityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentActivityMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_activity_main, container, false
        )

        val view = binding.root

        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        val viewPager2: ViewPager2 = view.findViewById(R.id.view_pager)

        val adapter : MyPagerAdapter = MyPagerAdapter(activity)
        adapter.addFragment(ProjectListFragment(), "Projects")
//        adapter.addFragment(GroupListFragment(), "Groups")
        viewPager2.adapter = adapter

        TabLayoutMediator(
            tabLayout, viewPager2
        ) { tab, position -> tab.text = adapter.getTabTitle(position) }.attach()


        return view
    }
}