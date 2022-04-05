package com.example.stagemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProjectDatabaseDao {

    @Insert
    fun insert(project: ProjectEntity)

    @Update
    fun update(project: ProjectEntity)

    @Query("SELECT * FROM project_table WHERE projectId = :key")
    fun get(key: Long): ProjectEntity?

    @Query("SELECT * from project_table WHERE projectId = :key")
    fun getProjectWithId(key: Long): LiveData<ProjectEntity>

    @Query("SELECT * FROM project_table ORDER BY projectId DESC LIMIT 1")
    fun getNewest(): ProjectEntity?

    @Query("DELETE FROM project_table")
    fun clearAllProjects()

    @Delete
    fun deleteProject(project: ProjectEntity)

    @Query("DELETE FROM project_table WHERE projectId = :key")
    fun deleteProjectById(key: Long)

    @Query("DELETE FROM formation_table WHERE formationId = :key")
    fun deleteFormationById(key: Long)

    @Query("DELETE FROM formation_table WHERE projectId = :key")
    fun deleteAllProjectFormations(key: Long)

    @Query("DELETE FROM position_table WHERE formationId = :key")
    fun deleteAllFormationPositions(key: Long)

    @Query("SELECT * FROM project_table ORDER BY projectId DESC")
    fun getAllProjects(): LiveData<List<ProjectEntity>>

    @Insert
    fun insert(formation: FormationEntity)

    @Update
    fun update(formation: FormationEntity)

    @Insert
    fun insert(position: PositionEntity)

    @Update
    fun update(position: PositionEntity)

    @Query("SELECT * FROM formation_table ORDER BY formationId DESC")
    fun getAllFormations(): LiveData<List<FormationEntity>>

    @Query("SELECT * FROM formation_table WHERE projectId = :projectKey ORDER BY projectId DESC")
    fun getProjectFormations(projectKey: Long): LiveData<List<FormationEntity>>

    @Query("SELECT * FROM formation_table WHERE formationId = :key")
    fun getFormationEntity(key: Long): FormationEntity?

    @Query("SELECT * from formation_table WHERE formationId = :key")
    fun getFormation(key: Long): LiveData<FormationEntity>

    @Query("DELETE FROM formation_table")
    fun clearFormations()

    @Query("SELECT projectId FROM formation_table WHERE formationId =:key")
    fun getFormationOwner(key: Long): Long?

    @Query("SELECT * FROM position_table WHERE formationId = :formationKey ORDER BY positionId DESC")
    fun getFormationPositions(formationKey: Long): LiveData<List<PositionEntity>>

    @Query("SELECT * FROM position_table WHERE formationId = :formationKey ORDER BY positionId DESC")
    fun getFormationPositionsList(formationKey: Long): List<PositionEntity>

    @Query("DELETE FROM position_table WHERE formationId = :formationKey")
    fun clearPositions(formationKey: Long)

    @Query("SELECT notes FROM project_table WHERE projectId = :projectKey")
    fun getNotes(projectKey: Long): String?
}