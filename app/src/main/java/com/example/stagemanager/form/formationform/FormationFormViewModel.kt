package com.example.stagemanager.form.formationform

import android.app.Application
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stagemanager.database.FormationEntity
import com.example.stagemanager.database.PositionEntity
import com.example.stagemanager.database.ProjectDatabaseDao
import com.example.stagemanager.database.ProjectEntity
import kotlinx.coroutines.*
import java.time.LocalDate

class FormationFormViewModel(
    val database: ProjectDatabaseDao,
    application: Application,
    val formationKey: Long
) :  AndroidViewModel(application) {

    val formationName= MutableLiveData<String?>()
//    val actorsAmount = MutableLiveData<Int>()
    val positions = mutableListOf<PositionEntity>()
    val actors = database.getFormationPositions(formationKey)

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToFormationList = MutableLiveData<Boolean?>()

    val navigateToFormationList: LiveData<Boolean?>
        get() = _navigateToFormationList

    private val _getData = MutableLiveData<Boolean?>()

    val getData: LiveData<Boolean?>
        get() = _getData

    fun doneNavigating() {
        _navigateToFormationList.value = null
        _getData.value = null
    }

    fun onSaveProject() {
        uiScope.launch {
            _getData.value = true
            withContext(Dispatchers.IO) {

                val formation = database.getFormationEntity(formationKey) ?: return@withContext

                formation.name = if (formationName.value != null) formationName.value!! else ""
                database.clearPositions(formationKey)
                positions.forEach{
                    database.insert(it)
                }

                database.update(formation)
            }
            _navigateToFormationList.value = true

        }
    }

}
