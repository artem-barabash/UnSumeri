package com.example.unsumeri.domain.entities

import java.time.LocalDateTime

data class Article(
    val id: Int,
    val userId: String,
    val title: String,
    var content: String,
    val createAtTime: LocalDateTime,
    val categoryId: Int,
    val category: Category
)