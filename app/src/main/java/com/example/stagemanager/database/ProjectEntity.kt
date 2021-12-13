package com.example.stagemanager.database

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "project_table")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    var projectId: Long = 0L,

    @ColumnInfo(name = "project_name")
    var name: String = "",

    @ColumnInfo(name = "song_used")
    var songUsed: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "deadline")
    var deadline: String = "",

    @ColumnInfo(name = "notes")
    var notes: String = ""
) {
}