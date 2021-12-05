package com.example.stagemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.stagehelper.MyPagerAdapter
import com.example.stagemanager.mainview.grouplist.GroupListFragment
import com.example.stagemanager.mainview.projectlist.ProjectListFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val viewPager2: ViewPager2 = findViewById(R.id.view_pager)

        val adapter : MyPagerAdapter = MyPagerAdapter(this)
        adapter.addFragment(ProjectListFragment(), "Projects")
        adapter.addFragment(GroupListFragment(), "Groups")
        viewPager2.adapter = adapter

        TabLayoutMediator(
            tabLayout, viewPager2
        ) { tab, position -> tab.text = adapter.getTabTitle(position) }.attach()
    }
}