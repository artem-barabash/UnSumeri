package com.example.unsumeri.domain.entities

data class Category(
    val Id: Int,
    val userId: String,
    val name: String,
    val createdAtTime: String,
    val parent: Category?,
    val parentId: Int,
    val subCategories: ArrayList<Category>?,
    val articles: ArrayList<Article>
)