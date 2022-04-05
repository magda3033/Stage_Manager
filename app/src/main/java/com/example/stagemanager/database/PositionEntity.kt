package com.example.stagemanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "position_table")
data class PositionEntity (
    @PrimaryKey(autoGenerate = true)
    var positionId: Long =0L,

    @ColumnInfo(name = "formationId")
    var formationId: Long = 0L,

    @ColumnInfo(name="x_position")
    var x: Int = 0,

    @ColumnInfo(name="y_position")
    var y: Int = 0
)
