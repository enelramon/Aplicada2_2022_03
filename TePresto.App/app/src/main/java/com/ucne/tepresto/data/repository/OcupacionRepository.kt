package com.ucne.tepresto.data.repository

import com.ucne.tepresto.data.local.OcupacionDao
import com.ucne.tepresto.data.local.OcupacionEntity
import com.ucne.tepresto.data.local.TePrestoDb
import javax.inject.Inject

class OcupacionRepository @Inject constructor(
    private val ocupacionDao: OcupacionDao
) {
    suspend fun insert(ocupacionEntity: OcupacionEntity) = ocupacionDao.insert(ocupacionEntity)
    suspend fun delete(ocupacionEntity: OcupacionEntity) = ocupacionDao.delete(ocupacionEntity)
    suspend fun find(id: Int) = ocupacionDao.find(id)
    fun getList() = ocupacionDao.getList()
}