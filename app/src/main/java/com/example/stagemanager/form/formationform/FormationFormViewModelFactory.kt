package com.example.stagemanager.form.formationform

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stagemanager.database.ProjectDatabaseDao
import java.lang.IllegalArgumentException

class FormationFormViewModelFactory (
    private val dataSource: ProjectDatabaseDao,
    private val application: Application,
    private val formationKey: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FormationFormViewModel::class.java)) {
                return FormationFormViewModel(dataSource, application, formationKey) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}