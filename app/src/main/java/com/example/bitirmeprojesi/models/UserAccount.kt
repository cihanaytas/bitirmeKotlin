package com.example.bitirmeprojesi.models

data class UserAccount(
        val nickName : String,
        val name : String,
        val surname : String,
        val e_mail : String,
        val password : String,
        val address: Address,
        val role: String
)