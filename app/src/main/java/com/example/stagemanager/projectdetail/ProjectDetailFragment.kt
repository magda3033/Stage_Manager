package com.example.stagemanager.projectdetail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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


class ProjectDetailFragment : Fragment() {

    private lateinit var viewModel: ProjectDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProjectDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_detail, container, false)

        setHasOptionsMenu(true)

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

        projectDetailViewModel.navigateToProjectForm.observe(this, Observer {
            if (it==true) {
                this.findNavController().navigate(
                    ProjectDetailFragmentDirections.actionProjectDetailFragmentToProjectFormFragment(
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.project_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> viewModel.onEditProject()
        }
        return super.onOptionsItemSelected(item)
    }

}