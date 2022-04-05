package com.example.stagemanager.projectnotes

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
import androidx.navigation.fragment.navArgs
import com.example.stagemanager.R
import com.example.stagemanager.database.ProjectDatabase
import com.example.stagemanager.database.ProjectDatabaseDao
import com.example.stagemanager.databinding.FragmentProjectNotesBinding
import com.example.stagemanager.form.formationform.FormationFormFragmentArgs
import com.example.stagemanager.form.formationform.FormationFormViewModel
import com.example.stagemanager.form.formationform.FormationFormViewModelFactory
import com.example.stagemanager.projectdetail.ProjectInfoTabsFragmentArgs
import com.example.stagemanager.projectdetail.ProjectInfoTabsFragmentDirections
import kotlinx.coroutines.*


class ProjectNotesFragment(val projectEntityId: Long) : Fragment() {

    lateinit var dataSource: ProjectDatabaseDao
    lateinit var binding: FragmentProjectNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project_notes, container, false)

        val application = requireNotNull(this.activity).application

//        val arguments: ProjectInfoTabsFragmentArgs by navArgs()
//        val projectId = arguments.projectEntityId

        dataSource = ProjectDatabase.getInstance(application).projectDatabaseDao

        val viewModelFactory = ProjectNotesViewModelFactory(projectEntityId, dataSource)

        val projectNotesViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ProjectNotesViewModel::class.java)

        projectNotesViewModel.saveData.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                saveData()
                projectNotesViewModel.onDataSaved()
            }
        })

        binding.projectNotesViewModel = projectNotesViewModel

        putNotes()


        return binding.root
    }

    private fun saveData() {
        val viewModelJob = Job()

        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
        uiScope.launch {
            withContext(Dispatchers.IO) {

                val project = dataSource.get(projectEntityId) ?: return@withContext

                project.notes = binding.notesEditText.text.toString()
                dataSource.update(project)
            }
        }
    }

    private fun putNotes() {
        val viewModelJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
        var oldNotes: String?
        uiScope.launch {
            withContext(Dispatchers.IO){
                oldNotes = dataSource.getNotes(projectEntityId)
            }
            binding.notesEditText.setText(oldNotes)
        }
    }


}