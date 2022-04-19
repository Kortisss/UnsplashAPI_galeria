package com.example.galeria.di

import android.app.Application
import androidx.room.Room
import com.example.galeria.data.ImageUrlsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app:Application, callback:ImageUrlsDatabase.Callback) =
        Room.databaseBuilder(app, ImageUrlsDatabase::class.java,"image_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    fun provideImageUrlsDao(db: ImageUrlsDatabase) = db.imageDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope