package com.example.suitevent.di

import android.content.Context
import androidx.room.Room
import com.example.suitevent.data.local.SuitEventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseProvider {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        SuitEventDatabase::class.java,
        "event_db"
    ).build()

    @Provides
    fun provideFavouriteMovieDao(db: SuitEventDatabase) = db.eventDao()
}