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

    @Query("SELECT * FROM project_table ORDER BY projectId DESC LIMIT 1")
    fun getNewest(): ProjectEntity?

    @Query("DELETE FROM project_table")
    fun clear()

    @Delete
    fun deleteProject(project: ProjectEntity)

    @Query("DELETE FROM project_table WHERE projectId = :key")
    fun deleteById(key: Long)

    @Query("SELECT * FROM project_table ORDER BY projectId DESC")
    fun getAllProjects(): LiveData<List<ProjectEntity>>
}