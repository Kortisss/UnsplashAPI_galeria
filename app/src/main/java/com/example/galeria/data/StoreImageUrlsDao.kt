package com.example.galeria.data

import androidx.room.*
import com.example.galeria.models.randomImageModel.Urls
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreImageUrlsDao {

    @Query("SELECT * FROM urls_table")
    fun getImages(): Flow<List<Urls>> // zamiast flow można uzyć livedata

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(urls: Urls)

    @Update
    suspend fun update(urls: Urls)

    @Delete
    suspend fun delete(urls: Urls)
}