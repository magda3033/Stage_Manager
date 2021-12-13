package com.example.stagemanager.projectnotes

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stagemanager.database.ProjectDatabaseDao
import com.example.stagemanager.database.ProjectEntity
import kotlinx.coroutines.*

class ProjectNotesViewModel(
    private val projectEntityKey: Long = 0L,
    dataSource: ProjectDatabaseDao
) : ViewModel() {

    val notes = MutableLiveData<String?>()

    val database = dataSource

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val project = MediatorLiveData<ProjectEntity>()

    fun getProject() = project

    init {

        project.addSource(database.getProjectWithId(projectEntityKey), project::setValue)
    }

    fun onSaveNotes() {
        uiScope.launch {
            withContext(Dispatchers.IO) {

                val project = database.get(projectEntityKey) ?: return@withContext

                project.notes = notes.value.toString()
                Log.i("ProjectNotesViewModel", "Notes ${notes.value}")
                database.update(project)
            }
        }
    }
}