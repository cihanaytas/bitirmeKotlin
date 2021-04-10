package com.example.bitirmeprojesi.models.products

import android.os.Build

data class Product(
        val id: String,
        val price: String,
        val brand: String,
        val model: String,
        val category: String,
        val storeName: String,
        val features: String,
        val units: String,
        val gorsel: String
)

{
    constructor(price: String, brand:String, model: String, category:String, features: String, units: String) :
            this("",price,brand,model,category,"",features,units,"")
}
