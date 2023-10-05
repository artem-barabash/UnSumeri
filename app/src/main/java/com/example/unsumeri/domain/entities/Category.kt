package com.example.unsumeri.domain.entities

import java.time.LocalDateTime

class Category(
    val Id: Int,
    val userId: String,
    val name: String,
    val createdAtTime: LocalDateTime,
    val parent: Category,
    val parentId: Int,
    val subCategories: ArrayList<Category>,
    val articles: ArrayList<Article>
)