package com.example.stagemanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_table")
class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    var projectId: Long = 0L,

    @ColumnInfo(name = "project_name")
    var name: String = "",

    @ColumnInfo(name = "song_used")
    var songUsed: String = ""
) {
}