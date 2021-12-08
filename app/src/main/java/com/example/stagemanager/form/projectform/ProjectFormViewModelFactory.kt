package com.example.stagemanager.form.projectform

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stagemanager.database.ProjectDatabaseDao
import java.lang.IllegalArgumentException

class ProjectFormViewModelFactory(
    private val projectId: Long,
    private val deleteAfter: Boolean,
    private val navigatedFrom: Int,
    private val dataSource: ProjectDatabaseDao) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectFormViewModel::class.java)) {
            return ProjectFormViewModel(projectId, deleteAfter, navigatedFrom, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}