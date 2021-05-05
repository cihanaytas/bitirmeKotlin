package com.example.bitirmeprojesi.models.products

data class Comments(
        val id: Long,
        val username: String,
        val comment: String,
        val productId: Long,
        val date: String,
        val byYou: Boolean
)


