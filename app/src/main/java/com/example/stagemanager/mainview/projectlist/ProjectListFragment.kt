package com.example.stagemanager.mainview.projectlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stagemanager.ActivityFragmentDirections
import com.example.stagemanager.R
import com.example.stagemanager.database.ProjectDatabase
import com.example.stagemanager.databinding.FragmentProjectListBinding

class ProjectListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProjectListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_list, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = ProjectDatabase.getInstance(application).projectDatabaseDao

        val viewModelFactory = ProjectListViewModelFactory(dataSource, application)

        val projectListViewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProjectListViewModel::class.java)

        binding.projectListViewModel = projectListViewModel

        binding.lifecycleOwner = this

        projectListViewModel.navigateToProjectForm.observe(viewLifecycleOwner, Observer {
            project ->
            project?.let{
                this.findNavController().navigate(
                    ActivityFragmentDirections
                        .actionActivityFragmentToProjectFormFragment(project.projectId))
                projectListViewModel.doneNavigating()
                Log.i("ProjectListFragment", "Send Project ID: ${project.projectId}")
            }
        })

        return binding.root
    }

}