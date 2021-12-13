package com.example.stagemanager.projectnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.stagemanager.R
import com.example.stagemanager.database.ProjectDatabase
import com.example.stagemanager.databinding.FragmentProjectNotesBinding
import com.example.stagemanager.form.formationform.FormationFormFragmentArgs
import com.example.stagemanager.form.formationform.FormationFormViewModel
import com.example.stagemanager.form.formationform.FormationFormViewModelFactory
import com.example.stagemanager.projectdetail.ProjectInfoTabsFragmentArgs


class ProjectNotesFragment(val projectEntityId: Long) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentProjectNotesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_notes, container, false)

        val application = requireNotNull(this.activity).application

//        val arguments: ProjectInfoTabsFragmentArgs by navArgs()
//        val projectId = arguments.projectEntityId

        val dataSource = ProjectDatabase.getInstance(application).projectDatabaseDao

        val viewModelFactory = ProjectNotesViewModelFactory(projectEntityId, dataSource)

        val projectNotesViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ProjectNotesViewModel::class.java)


        binding.projectNotesViewModel = projectNotesViewModel



        return binding.root
    }


}