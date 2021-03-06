package com.example.stagemanager.projectdetail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stagemanager.ActivityFragmentDirections
import com.example.stagemanager.R
import com.example.stagemanager.database.ProjectDatabase
import com.example.stagemanager.databinding.FragmentProjectDetailBinding
import com.example.stagemanager.form.projectform.ProjectFormFragmentDirections
import com.example.stagemanager.form.projectform.ProjectFormViewModel


class ProjectDetailFragment(val projectKey: Long) : Fragment() {

    private lateinit var viewModel: ProjectDetailViewModel

//    var projectKey: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProjectDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_detail, container, false)

        val application = requireNotNull(this.activity).application
//        val arguments: ProjectDetailFragmentArgs by navArgs()


        // Create an instance of the ViewModel Factory.
        val dataSource = ProjectDatabase.getInstance(application).projectDatabaseDao
        val viewModelFactory = ProjectDetailViewModelFactory(projectKey!!, dataSource)

        val projectDetailViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ProjectDetailViewModel::class.java)

        binding.projectDetailViewModel = projectDetailViewModel

        binding.lifecycleOwner = this

        projectDetailViewModel.navigateToProjectList.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    ProjectInfoTabsFragmentDirections.actionProjectInfoTabsFragmentToActivityFragment())
            projectDetailViewModel.doneNavigating()
            }
        })

        projectDetailViewModel.navigateToProjectForm.observe(viewLifecycleOwner, Observer {
            if (it==true) {
                this.findNavController().navigate(
                    ProjectInfoTabsFragmentDirections.actionProjectInfoTabsFragmentToProjectFormFragment(
                        projectDetailViewModel.getProject().value!!.projectId))
                projectDetailViewModel.doneNavigating()
                Log.i("ProjectFormFragment", "Done navigating to form")
            }
        })

        setActivityTitle()

        viewModel = projectDetailViewModel

        return binding.root
    }

    private fun Fragment.setActivityTitle()
    {
        (activity as AppCompatActivity?)!!.supportActionBar?.title = "Project Details"
    }

}