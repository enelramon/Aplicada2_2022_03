package com.ucne.tepresto.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class OcupacionDaoTest {

    private lateinit var database: TePrestoDb
    private lateinit var dao: OcupacionDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), TePrestoDb::class.java
        ).allowMainThreadQueries().build()

        dao = database.ocupacionDao
    }

    @Test
    fun Insert_Ocupacion_Retuns_True() = runBlocking {

        //arrange
        val id = 1
        val ocupacion = OcupacionEntity(
            ocupacionId = id,
            descripcion = "Desarrollador",
            salario = 10.00
        )

        //act
        dao.insert(ocupacion)
        val encontrado = dao.find(id)

        //assert
        Truth.assertThat(ocupacion).isEqualTo(encontrado)
    }

    @After
    fun closeDatabase() {
        database.close()
    }
}