package com.example.stagemanager.projectdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stagemanager.R
import com.example.stagemanager.database.ProjectDatabase
import com.example.stagemanager.databinding.FragmentProjectDetailBinding


class ProjectDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProjectDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments: ProjectDetailFragmentArgs by navArgs()

        // Create an instance of the ViewModel Factory.
        val dataSource = ProjectDatabase.getInstance(application).projectDatabaseDao
        val viewModelFactory = ProjectDetailViewModelFactory(arguments.projectEntityId, dataSource)

        val projectDetailViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ProjectDetailViewModel::class.java)

        binding.projectDetailViewModel = projectDetailViewModel

        binding.lifecycleOwner = this

        projectDetailViewModel.navigateToProjectList.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    ProjectDetailFragmentDirections.actionProjectDetailFragmentToActivityFragment())
            projectDetailViewModel.doneNavigating()
            }
        })

        setActivityTitle()

        return binding.root
    }

    fun Fragment.setActivityTitle()
    {
        (activity as AppCompatActivity?)!!.supportActionBar?.title = "Project Details"
    }

}