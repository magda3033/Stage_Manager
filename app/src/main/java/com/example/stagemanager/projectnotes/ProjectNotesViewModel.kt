package com.example.stagemanager.projectnotes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stagemanager.database.PositionEntity
import com.example.stagemanager.database.ProjectDatabaseDao
import com.example.stagemanager.database.ProjectEntity
import kotlinx.coroutines.*

class ProjectNotesViewModel(
    private val projectEntityKey: Long = 0L,
    val dataSource: ProjectDatabaseDao
) : ViewModel() {

     var notes= MutableLiveData<String?>()

//    private val viewModelJob = Job()

//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _saveData = MutableLiveData<Boolean?>()

    val saveData: LiveData<Boolean?>
        get() = _saveData

//    private val project = MediatorLiveData<ProjectEntity>()
//
//    fun getProject() = project
//
//    init {
//        project.addSource(dataSource.getProjectWithId(projectEntityKey), project::setValue)
//    }

    fun onSaveNotes() {
//        uiScope.launch {
//            withContext(Dispatchers.IO) {
//
//                val project = dataSource.get(projectEntityKey) ?: return@withContext
//
//                project.notes = notes.value.toString()
//                Log.i("ProjectNotesViewModel", "Notes ${notes.value}")
//                dataSource.update(project)
//            }
//        }
        _saveData.value = true
    }

    fun onDataSaved() {
        _saveData.value = null
    }

//    fun putNotes(): String? {
//        val viewModelJob = Job()
//        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//        var oldNotes: String? = "test oby sie nie wyswietlilo"
//        uiScope.launch {
//            withContext(Dispatchers.IO){
//                oldNotes = dataSource.getNotes(projectEntityKey)
//
//            }
////            notes.value = oldNotes
//
//            Log.i("ProjectNotesViewModel", "TESSSSTTT $oldNotes")
//            notes = MutableLiveData(oldNotes)
//
//
//        }
//
//        return oldNotes
//    }
}