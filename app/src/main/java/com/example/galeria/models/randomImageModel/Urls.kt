package com.example.galeria.models.randomImageModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize

@Entity(tableName= "urls_table")
@VersionedParcelize
data class Urls(
    var full: String,
    var raw: String,
    var regular: String,
    var small: String,
    var small_s3: String,
    var thumb: String,
    @PrimaryKey(autoGenerate = true) val likedImageId: Int = 0,

)