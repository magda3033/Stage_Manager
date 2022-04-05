package com.example.stagemanager.projectnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stagemanager.database.ProjectDatabaseDao
import com.example.stagemanager.projectdetail.ProjectDetailViewModel

class ProjectNotesViewModelFactory(
    private val projectEntityKey: Long,
    private val dataSource: ProjectDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectNotesViewModel::class.java)) {
            return ProjectNotesViewModel(projectEntityKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}