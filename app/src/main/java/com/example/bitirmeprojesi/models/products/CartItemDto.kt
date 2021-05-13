package com.example.bitirmeprojesi.models.products

data class CartItemDto(
        val productId: String,
        val price: Double,
        val quantity: Int,
        val category: String
)