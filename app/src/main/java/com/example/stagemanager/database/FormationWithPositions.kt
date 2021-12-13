package com.example.stagemanager.database

import androidx.room.Embedded
import androidx.room.Relation

data class FormationWithPositions(
    @Embedded val formation: FormationEntity,
    @Relation(
        parentColumn = "formationId",
        entityColumn = "formationId"
    )
    val positions: List<PositionEntity>
)
