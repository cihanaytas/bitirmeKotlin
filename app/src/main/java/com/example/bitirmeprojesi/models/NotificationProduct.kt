package com.example.bitirmeprojesi.models

data class NotificationProduct(
        val id: Long,
        val bildirim: String,
        val okundu: Boolean,
        val onay: Boolean
)