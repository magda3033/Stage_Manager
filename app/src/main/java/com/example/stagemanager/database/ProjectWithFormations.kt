package com.example.stagemanager.database

import androidx.room.Embedded
import androidx.room.Relation

data class ProjectWithFormations(
    @Embedded val project: ProjectEntity,
    @Relation(
        parentColumn = "projectId",
        entityColumn = "projectId"
    )
    val formations: List<FormationEntity>
)