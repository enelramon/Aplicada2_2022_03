package com.ucne.tepresto.di

import android.content.Context
import androidx.room.Room
import com.ucne.tepresto.data.local.TePrestoDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): TePrestoDb {
        val DATABASE_NAME = "TePresto.db"

        return Room.databaseBuilder(
            context,
            TePrestoDb::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideOcupacionDao(db: TePrestoDb) = db.ocupacionDao
}