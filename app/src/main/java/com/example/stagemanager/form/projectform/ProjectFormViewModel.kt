package com.example.stagemanager.form.projectform

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stagemanager.database.ProjectDatabaseDao
import com.example.stagemanager.database.ProjectEntity
import kotlinx.coroutines.*
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class ProjectFormViewModel(
    private val projectId : Long = 0L,
    var deleteAfter : Boolean = true,
    var navigatedFrom: Int = 0,
    val database: ProjectDatabaseDao
) : ViewModel() {


    val projectName= MutableLiveData<String?>()
    val songUsed = MutableLiveData<String?>()
    val deadline = MutableLiveData<LocalDate?>()
    val description = MutableLiveData<String?>()
//    var deleteAfter = false

    private var viewModelJob: Job = Job()

    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToProjectList = MutableLiveData<Boolean?>()

    val navigateToProjectList: LiveData<Boolean?>
        get() = _navigateToProjectList

    private val _navigateToProjectDetail = MutableLiveData<Boolean?>()

    val navigateToProjectDetail: LiveData<Boolean?>
        get() = _navigateToProjectDetail

//    private val project = MediatorLiveData<ProjectEntity>()
//
//    fun getProject() = project
//
//    init {
//        if (navigatedFrom == 1) {
////            setTextBoxData()
////            project.addSource(database.getProjectWithId(projectId), project::setValue)
////            Log.i("ProjectFormViewModel", "Pobrano id: ${project.value?.projectId}")
//        }
//    }

    fun doneNavigating() {
        _navigateToProjectList.value = null
        _navigateToProjectDetail.value = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveProject() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val project = database.get(projectId) ?: return@withContext
                setProjectData(project)

                if (project.name != "") {
                    database.update(project)
                    deleteAfter = false
                }

            }
            when (navigatedFrom) {
                0 -> _navigateToProjectList.value = true
                1 -> _navigateToProjectDetail.value = true
            }

        }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun getProjectData(projectId: Long) {
//        uiScope.launch {
//            withContext(Dispatchers.IO) {
//                val project = database.get(projectId) ?: return@withContext
//                projectName.value = project.name
//                deadline.value = LocalDate.parse(project.deadline)
//                description.value = project.description
//                songUsed.value = project.songUsed
//            }
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setProjectData(project: ProjectEntity) {
        if (navigatedFrom == 0) {
            project.name = if (projectName.value != null) projectName.value!! else ""
            project.songUsed = if (songUsed.value != null) songUsed.value!! else ""
            project.description = if (description.value != null) description.value!! else ""
            if (deadline.value != null) {
                project.deadline = deadline.value!!.toString()
            }
        }
        if (navigatedFrom == 1) {
            project.name = if (projectName.value != null) projectName.value!! else project.name
            project.songUsed = if (songUsed.value != null) songUsed.value!! else project.songUsed
            project.description = if (description.value != null) description.value!! else project.description
            if (deadline.value != null) {
                project.deadline = deadline.value!!.toString()
            }
        }
    }

    private fun deleteProject() {
        Log.i("ProjectFormViewModel", "Deleting ${projectId} ${projectName.value}")
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.deleteProjectById(projectId)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        if (deleteAfter) {
            deleteProject()
        }
//        viewModelJob.cancel()
    }
}