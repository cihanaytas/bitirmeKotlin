package com.example.bitirmeprojesi.models.products

data class CommentDto(
        val id: Long,
        val comment: String,
        val productId: Long
)

{
    constructor(comment: String,productId: Long):
    this(0,comment,productId)
}