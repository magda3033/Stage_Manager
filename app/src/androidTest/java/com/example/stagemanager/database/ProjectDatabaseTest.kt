package com.example.stagemanager.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
//import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class ProjectDatabaseTest : TestCase(){

    private lateinit var db: ProjectDatabase
    private lateinit var dao: ProjectDatabaseDao

    @Before
     public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, ProjectDatabase::class.java).build()

        dao = db.projectDatabaseDao
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun readAndWriteProject() {
        val project = ProjectEntity()

        project.projectId = 1
        project.name = "Test project"
        project.songUsed = "Song"
        project.description = "A little more info about test project"
        project.deadline = LocalDate.of(2021,1,1).toString()
        project.notes = "Additional notes."

        dao.insert(project)

        val fromDatabase = dao.getNewest()

        assertThat(project).isEqualTo(fromDatabase)
    }

    @Test
    fun updateProject() {
        val project = ProjectEntity()

        project.projectId = 1
        project.name = "Test project"

        dao.insert(project)

        project.songUsed = "Song used"
        dao.update(project)

        val fromDatabase = dao.getNewest()

        assertThat(project).isEqualTo(fromDatabase)
    }

    @Test
    fun getById() {
        val project = ProjectEntity()

        project.projectId = 1
        project.name = "Test project"

        dao.insert(project)

        val project2 = ProjectEntity()

        project2.projectId = 2
        project2.name = "Test project 2"

        dao.insert(project2)

        val fromDatabase = dao.get(1)

        assertThat(project).isEqualTo(fromDatabase)
    }

    @Test
    fun clearAll() {
        val project = ProjectEntity()

        project.projectId = 1
        project.name = "Test project"

        val project2 = ProjectEntity()

        project2.projectId = 2
        project2.name = "Test project2"

        dao.insert(project)
        dao.insert(project2)
        dao.clearAllProjects()

        val fromDatabase = dao.getNewest()

        assertThat(fromDatabase).isNull()
    }

    @Test
    fun deleteProject() {
        val project = ProjectEntity()

        project.projectId = 1
        project.name = "Test project"

        dao.insert(project)

        val project2 = ProjectEntity()

        project2.projectId = 2
        project2.name = "Test project 2"

        dao.insert(project2)

        dao.deleteProject(project)
        val fromDatabase = dao.get(1)

        assertThat(fromDatabase).isNull()
    }

    @Test
    fun deleteProjectById() {
        val project = ProjectEntity()

        project.projectId = 1
        project.name = "Test project"

        dao.insert(project)

        val project2 = ProjectEntity()

        project2.projectId = 2
        project2.name = "Test project 2"

        dao.insert(project2)

        dao.deleteProjectById(1)
        val fromDatabase = dao.get(1)

        assertThat(fromDatabase).isNull()
    }

    @Test
    fun readAndWriteFormation() {
        val formation = FormationEntity()
        formation.projectId = 1
        formation.formationId = 1
        formation.name = "Test formation"
        dao.insert(formation)

        val fromDatabase = dao.getFormationEntity(1)
        assertThat(fromDatabase).isEqualTo(formation)
    }

    @Test
    fun updateFormation() {
        val formation = FormationEntity()
        formation.projectId = 1
        formation.formationId = 1
        formation.name = "Test formation"
        dao.insert(formation)

        formation.name = "Different name"
        dao.update(formation)

        val fromDatabase = dao.getFormationEntity(1)
        assertThat(fromDatabase).isEqualTo(formation)
    }

    @Test
    fun deleteFormationById() {
        val formation = FormationEntity()
        formation.projectId = 1
        formation.formationId = 1
        formation.name = "Test formation"
        dao.insert(formation)

        val formation2 = FormationEntity()
        formation2.projectId = 1
        formation2.formationId = 2
        formation2.name = "Test formation 2"
        dao.insert(formation2)

        dao.deleteFormationById(1)
        val fromDatabase = dao.getFormationEntity(1)
        assertThat(fromDatabase).isNull()
    }

    @Test
    fun deleteFormationsFromProject() {
        val formation = FormationEntity()
        formation.projectId = 1
        formation.formationId = 1
        formation.name = "Test formation"
        dao.insert(formation)

        val formation2 = FormationEntity()
        formation2.projectId = 1
        formation2.formationId = 2
        formation2.name = "Test formation 2"
        dao.insert(formation2)

        dao.deleteAllProjectFormations(1)
        val fromDatabase = dao.getFormationEntity(1)
        assertThat(fromDatabase).isNull()
    }

    @Test
    fun getFormationOwner() {
        val formation = FormationEntity()
        formation.projectId = 1
        formation.formationId = 1
        formation.name = "Test formation"
        dao.insert(formation)

        val owner = dao.getFormationOwner(1)
        assertThat(owner).isEqualTo(1)
    }

    @Test
    fun readAndWritePosition() {
        val position = PositionEntity()
        position.positionId = 1
        position.formationId = 1
        dao.insert(position)

        val fromDatabase = dao.getFormationPositionsList(1)
        assertThat(fromDatabase.contains(position)).isTrue()
    }

    @Test
    fun updatePosition() {
        val position = PositionEntity()
        position.positionId = 1
        position.formationId = 1
        position.x = 1
        dao.insert(position)

        position.x = 3
        dao.update(position)

        val fromDatabase = dao.getFormationPositionsList(1)
        assertThat(fromDatabase.contains(position)).isTrue()
    }

    @Test
    fun deletePositionsFromFormation() {
        val position = PositionEntity()
        position.positionId = 1
        position.formationId = 1
        position.x = 1
        dao.insert(position)

        val position2 = PositionEntity()
        position2.positionId = 2
        position2.formationId = 1
        position2.x = 1
        dao.insert(position2)

        dao.deleteAllFormationPositions(1)
        val fromDatabase = dao.getFormationPositionsList(1)
        assertThat(fromDatabase).isEmpty()
    }
}