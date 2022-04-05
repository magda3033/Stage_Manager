package com.example.stagemanager.projectdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.stagemanager.MyPagerAdapter
import com.example.stagemanager.R
import com.example.stagemanager.databinding.FragmentProjectInfoTabsBinding
import com.example.stagemanager.formationlist.FormationListFragment
import com.example.stagemanager.projectnotes.ProjectNotesFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProjectInfoTabsFragment : Fragment() {

    private lateinit var viewModel: ProjectInfoTabsViewModel
    private lateinit var formationTab: TabLayout.Tab
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProjectInfoTabsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_info_tabs, container, false
        )

        val view = binding.root

        tabLayout = view.findViewById(R.id.tab_layout)
        val viewPager2: ViewPager2 = view.findViewById(R.id.view_pager)

        val arguments: ProjectInfoTabsFragmentArgs by navArgs()

        val adapter = MyPagerAdapter(activity)

        val projectDetailFragment = ProjectDetailFragment(arguments.projectEntityId)
        val formationListFragment = FormationListFragment(arguments.projectEntityId)
        val projectNotesFragment = ProjectNotesFragment(arguments.projectEntityId)

        adapter.addFragment(projectDetailFragment, "Info")
        adapter.addFragment(formationListFragment, "Formations")
        adapter.addFragment(projectNotesFragment, "Notes")
        viewPager2.adapter = adapter

        TabLayoutMediator(
            tabLayout, viewPager2
        ) { tab, position -> tab.text = adapter.getTabTitle(position) }.attach()

        formationTab = tabLayout.getTabAt(1)!!

        Log.i("ProjectInfoTabsFragment", "Created project info tabs")

        if(arguments.navigatingFromFormationForm == 1)
        {
            switchToFormationList()
        }

        return view
    }

    fun switchToFormationList()
    {
        formationTab.select()
    }

}