package com.example.bitirmeprojesi.models

data class Product(
    val category: String,
    val price: String,
    val storeName: String,
    val units: String,
    val features: String)

{
    constructor(category: String, price: String, unit: String, features: String) :
            this(category, price, "",unit, features)
}
