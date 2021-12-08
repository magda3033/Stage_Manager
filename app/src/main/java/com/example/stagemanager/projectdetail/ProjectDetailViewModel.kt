package com.example.stagemanager.projectdetail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stagemanager.database.ProjectDatabaseDao
import com.example.stagemanager.database.ProjectEntity

class ProjectDetailViewModel(
        private val projectEntityKey: Long = 0L,
        dataSource: ProjectDatabaseDao) : ViewModel() {


    val database = dataSource

    private val project = MediatorLiveData<ProjectEntity>()

    fun getProject() = project

    init {

        project.addSource(database.getProjectWithId(projectEntityKey), project::setValue)
    }

    private val _navigateToProjectList = MutableLiveData<Boolean?>()

    val navigateToProjectList: LiveData<Boolean?>
        get() = _navigateToProjectList

    private val _navigateToProjectForm = MutableLiveData<Boolean?>()

    val navigateToProjectForm: LiveData<Boolean?>
        get() = _navigateToProjectForm

    fun doneNavigating() {
        _navigateToProjectList.value = null
        _navigateToProjectForm.value = null
    }

    fun onClose() {
        _navigateToProjectList.value = true
    }

    fun onEditProject() {
        Log.i("ProjectDetailViewModel", "Editing project!")
        _navigateToProjectForm.value = true
    }
}