package com.example.stagemanager.form.projectform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stagemanager.database.ProjectDatabaseDao
import java.lang.IllegalArgumentException

class ProjectFormViewModelFactory(
    private val projectId: Long,
    private val dataSource: ProjectDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectFormViewModel::class.java)) {
            return ProjectFormViewModel(projectId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}