package com.example.unsumeri.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import org.jetbrains.annotations.NotNull
import org.parceler.Parcel

@Parcel
@Entity(tableName = "articles")
data class Article(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NotNull
    @ColumnInfo(name = "userId")
    val userId: String,
    @NotNull
    @ColumnInfo(name = "title")
    val title: String,
    @NotNull
    @ColumnInfo(name = "content")
    var content: String,

    //LocalDateTime
    @NotNull
    @ColumnInfo(name = "createAtTime")
    val createAtTime: String,

    @ColumnInfo(name = "categoryId")
    var categoryId: Int = 0

    ///val category: Category
)