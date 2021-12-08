package com.example.stagemanager.projectdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stagemanager.database.ProjectDatabaseDao

class ProjectDetailViewModelFactory(
    private val projectEntityKey: Long,
    private val dataSource: ProjectDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectDetailViewModel::class.java)) {
            return ProjectDetailViewModel(projectEntityKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}