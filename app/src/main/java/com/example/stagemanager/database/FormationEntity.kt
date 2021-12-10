package com.example.stagemanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "formation_table")
data class FormationEntity(
    @PrimaryKey(autoGenerate = true)
    var formationId: Long = 0L,

    @ColumnInfo(name = "formation_name")
    var name: String = "",

    @ColumnInfo(name = "projectId")
    var projectId: Long = 0L, )