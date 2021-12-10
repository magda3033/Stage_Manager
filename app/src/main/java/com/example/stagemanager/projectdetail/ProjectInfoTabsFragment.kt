package com.example.stagemanager.projectdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.stagehelper.MyPagerAdapter
import com.example.stagemanager.BlankFragment
import com.example.stagemanager.R
import com.example.stagemanager.databinding.FragmentActivityMainBinding
import com.example.stagemanager.databinding.FragmentProjectInfoTabsBinding
import com.example.stagemanager.formationlist.FormationListFragment
import com.example.stagemanager.mainview.grouplist.GroupListFragment
import com.example.stagemanager.mainview.projectlist.ProjectListFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProjectInfoTabsFragment : Fragment() {

    private lateinit var viewModel: ProjectInfoTabsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProjectInfoTabsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_info_tabs, container, false
        )

        val view = binding.root

        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        val viewPager2: ViewPager2 = view.findViewById(R.id.view_pager)

        val arguments: ProjectInfoTabsFragmentArgs by navArgs()

        val adapter = MyPagerAdapter(activity)

        val projectDetailFragment = ProjectDetailFragment(arguments.projectEntityId)
//        projectDetailFragment.projectKey = arguments.projectEntityId
        val formationListFragment = FormationListFragment(arguments.projectEntityId)

        adapter.addFragment(projectDetailFragment, "Info")
        adapter.addFragment(formationListFragment, "Formations")
        viewPager2.adapter = adapter

        TabLayoutMediator(
            tabLayout, viewPager2
        ) { tab, position -> tab.text = adapter.getTabTitle(position) }.attach()


        return view
    }


}