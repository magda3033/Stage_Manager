package com.example.stagemanager.form.projectform

import android.os.Build
import android.util.Log
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stagemanager.database.ProjectDatabaseDao
import com.example.stagemanager.database.ProjectEntity
import kotlinx.coroutines.*
import java.time.LocalDate
import java.util.*

class ProjectFormViewModel(
    private val projectId : Long = 0L,
    val database: ProjectDatabaseDao
) : ViewModel() {

    val projectName = MutableLiveData<String?>()
    val songUsed = MutableLiveData<String?>()
    val deadline = MutableLiveData<LocalDate?>()
    val description = MutableLiveData<String?>()
    var hasBeenSaved = false

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToProjectList = MutableLiveData<Boolean?>()

    val navigateToProjectList: LiveData<Boolean?>
        get() = _navigateToProjectList

    fun doneNavigating() {
        _navigateToProjectList.value = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveProject() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val project = database.get(projectId) ?: return@withContext
                setProjectData(project)

                if (project.name != "") {
                    database.update(project)
                    hasBeenSaved = true
                }

            }
            _navigateToProjectList.value = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setProjectData(project: ProjectEntity) {
        project.name = if (projectName.value != null) projectName.value!! else ""
        project.songUsed = if (songUsed.value != null) songUsed.value!! else ""
        project.description = if (description.value != null) description.value!! else ""
        if (deadline.value != null) {
            project.deadline = deadline.value!!.toString()
        }
        Log.i("ProjectFormViewModel", "Project ID: ${project.projectId}")
        Log.i("ProjectFormViewModel", "Name: ${project.name}")
        Log.i("ProjectFormViewModel", "Music Piece: ${project.songUsed}")
        Log.i("ProjectFormViewModel", "Date: ${project.deadline}")
        Log.i("ProjectFormViewModel", "Description: ${project.description}")
    }

    private fun deleteProject() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.deleteById(projectId)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!hasBeenSaved) {
            deleteProject()
        }
        hasBeenSaved = false
//        viewModelJob.cancel()
    }
}