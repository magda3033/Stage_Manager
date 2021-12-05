package com.example.stagemanager.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProjectDatabaseDao {

    @Insert
    fun insert(project: ProjectEntity)

    @Update
    fun update(project: ProjectEntity)

    @Query("SELECT * FROM project_table WHERE projectId = :key")
    fun get(key: Long): ProjectEntity?

    @Query("DELETE FROM project_table")
    fun clear()

    @Query("SELECT * FROM project_table ORDER BY projectId DESC")
    fun getAllProjects(): LiveData<List<ProjectEntity>>
}