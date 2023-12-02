package com.example.unsumeri.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unsumeri.domain.entities.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllArticle(listQuestion: List<Article>)

    @Query("SELECT * FROM articles")
    fun selectAllArticle(): Flow<List<Article>>

    @Query("DELETE FROM articles")
    fun deleteAllArticles()

}