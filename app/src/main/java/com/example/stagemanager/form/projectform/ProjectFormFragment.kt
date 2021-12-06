package com.example.stagemanager.form.projectform

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stagemanager.ActivityFragmentDirections
import com.example.stagemanager.R
import com.example.stagemanager.database.ProjectDatabase
import com.example.stagemanager.databinding.FragmentProjectFormBinding
import com.example.stagemanager.databinding.FragmentProjectListBinding
import java.time.LocalDate
import java.util.*

class ProjectFormFragment : Fragment() {

    private lateinit var viewModel: ProjectFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProjectFormBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_form, container, false
        )

        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application

        val arguments: ProjectFormFragmentArgs by navArgs()

        val dataSource = ProjectDatabase.getInstance(application).projectDatabaseDao

        val viewModelFactory = ProjectFormViewModelFactory(arguments.projectId, dataSource)

        val projectFormViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ProjectFormViewModel::class.java)

        viewModel = projectFormViewModel


        binding.projectFormViewModel = projectFormViewModel

        projectFormViewModel.navigateToProjectList.observe(this, Observer {
            if (it==true) {
                this.findNavController().navigate(
                    ProjectFormFragmentDirections.actionProjectFormFragmentToActivityFragment())
                projectFormViewModel.doneNavigating()
                Log.i("ProjectFormFragment", "Done navigating")
            }
        })

        binding.inputDeadline.setOnDateChangeListener {
                view, year, month, day ->
            var date = LocalDate.of(year, month + 1, day)
            projectFormViewModel.deadline.value = date
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.project_save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> viewModel.onSaveProject()
        }
        return super.onOptionsItemSelected(item)
    }
}