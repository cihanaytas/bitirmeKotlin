package com.example.bitirmeprojesi.models

data class NotificationProduct(
        val id: Long,
        val bildirim: String,
        val okundu: Boolean,
        val onay: Boolean,
        val imageURI: String,
        val shopId: Long,
        val productId: Long
)