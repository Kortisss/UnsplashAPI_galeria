package com.example.galeria.models.randomImageModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize

@Entity(tableName= "urls_table")
@VersionedParcelize
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val small_s3: String,
    val thumb: String,
    @PrimaryKey(autoGenerate = true) val likedImageId: Int = 0,

)