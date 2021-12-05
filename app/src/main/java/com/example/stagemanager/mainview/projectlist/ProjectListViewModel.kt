package com.example.stagemanager.mainview.projectlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.stagehelper.formatProjects
import com.example.stagemanager.database.ProjectDatabaseDao
import com.example.stagemanager.database.ProjectEntity
import kotlinx.coroutines.*

class ProjectListViewModel(
    val database: ProjectDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val projects = database.getAllProjects()

    val projectsString = Transformations.map(projects) { projects ->
        formatProjects(projects, application.resources)
    }

    private val _navigateToProjectForm = MutableLiveData<ProjectEntity?>()

    val navigateToProjectForm: LiveData<ProjectEntity?>
        get() = _navigateToProjectForm

    fun doneNavigating() {
        _navigateToProjectForm.value = null
    }

    fun onCreateNewProject() {
        uiScope.launch {

            val newProject = ProjectEntity()

            insert(newProject)

            _navigateToProjectForm.value = newProject
        }
    }

    private suspend fun insert(project: ProjectEntity) {
        withContext(Dispatchers.IO) {
            database.insert(project)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }
}