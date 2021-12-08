package com.example.stagemanager.mainview.projectlist

import android.app.Application
import android.util.Log
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

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val projects = database.getAllProjects()

    val projectsString = Transformations.map(projects) { projects ->
        formatProjects(projects, application.resources)
    }

    private val _navigateToProjectForm = MutableLiveData<ProjectEntity?>()

    val navigateToProjectForm: LiveData<ProjectEntity?>
        get() = _navigateToProjectForm

    private val _navigateToProjectDetail = MutableLiveData<Long?>()

    val navigateToProjectDetail
        get() = _navigateToProjectDetail

    fun onProjectEntityClicked(id: Long) {
        _navigateToProjectDetail.value = id
    }

    fun onProjectEntityDetailNavigated() {
        _navigateToProjectDetail.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneNavigating() {
        _navigateToProjectForm.value = null
    }

    fun onCreateNewProject() {
        uiScope.launch {

            val newProject = ProjectEntity()

            insert(newProject)

            _navigateToProjectForm.value = getNewestFromDatabase()
        }
    }

    private suspend fun getNewestFromDatabase(): ProjectEntity? {
        val newest: ProjectEntity
        withContext(Dispatchers.IO) {
            newest = database.getNewest()!!
        }
        return newest
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

    fun onDelete(project: ProjectEntity) {
        uiScope.launch {
            delete(project)
        }
    }

    private suspend fun delete(project: ProjectEntity) {
        withContext(Dispatchers.IO) {
            database.deleteProject(project)
        }
    }
}