package com.example.stagemanager.formationlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stagemanager.database.ProjectDatabaseDao
import java.lang.IllegalArgumentException

class FormationListViewModelFactory(
    private val dataSource: ProjectDatabaseDao,
    private val application: Application,
    private val projectKey: Long
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormationListViewModel::class.java)) {
            return FormationListViewModel(dataSource, application, projectKey) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}