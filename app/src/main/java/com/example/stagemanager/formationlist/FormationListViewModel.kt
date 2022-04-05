package com.example.stagemanager.formationlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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

    private val _navigateToFormationForm = MutableLiveData<Long?>()

    val navigateToFormationForm
        get() = _navigateToFormationForm

    fun onFormationEntityClicked(id: Long) {
        _navigateToFormationForm.value = id
    }

    fun onFormationEntityDetailNavigated() {
        _navigateToFormationForm.value = null
    }

    fun onCreateNewFormation() {
        var newId: Long
        uiScope.launch {

            val newFormation = FormationEntity(projectId = projectKey)
            newId = newFormation.formationId
            insert(newFormation)
            _navigateToFormationForm.value = newId
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