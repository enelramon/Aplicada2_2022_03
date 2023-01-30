package com.ucne.tepresto.data.local


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "Ocupaciones")
data class OcupacionEntity(
    @PrimaryKey(autoGenerate = true)
    val ocupacionId: Int? = null,
    val descripcion: String,
    val salario: Double
)

@Dao
interface OcupacionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ocupacionEntity: OcupacionEntity)

    @Delete
    suspend fun delete(ocupacionEntity: OcupacionEntity)

    @Query(
        "SELECT * " +
                "FROM Ocupaciones " +
                "WHERE OcupacionId = :ocupacionId " +
                "LIMIT 1"
    )
    suspend fun find(ocupacionId: Int): OcupacionEntity?

    @Query("SELECT * FROM Ocupaciones")
    fun getList(): Flow<List<OcupacionEntity>>
}

@Database(
    entities = [
        OcupacionEntity::class
    ],
    version = 1
)
abstract class TePrestoDb : RoomDatabase() {
    abstract val ocupacionDao: OcupacionDao
}

//Hacer la entidad
//hacer la dao
//hacer la db
//hacer el repository
//hacer el viewmodel
//hacer el composable registro
//hacer el composable listado
//hacer la navegacion

