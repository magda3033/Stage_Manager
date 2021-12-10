package com.example.stagemanager.formationlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.example.stagehelper.formatFormation
import com.example.stagemanager.database.FormationEntity
import com.example.stagemanager.database.ProjectDatabaseDao
import kotlinx.coroutines.*

class FormationListViewModel(
    val database: ProjectDatabaseDao,
    application: Application,
    private val projectKey: Long
) : AndroidViewModel(application) {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

//    val formations = database.getAllFormations()
    val formations = database.getProjectFormations(projectKey)

    val formationsString = Transformations.map(formations) { formation ->
        formatFormation(formation, application.resources)
    }

    fun onCreateNewFormation() {
        uiScope.launch {

            val newFormation = FormationEntity(projectId = projectKey)

            insert(newFormation)
        }
    }

    private suspend fun insert(formation: FormationEntity){
        withContext(Dispatchers.IO) {
            database.insert(formation)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onClear() {
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clearFormations()
        }
    }
}