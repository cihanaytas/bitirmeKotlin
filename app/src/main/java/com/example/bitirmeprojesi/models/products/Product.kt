package com.example.bitirmeprojesi.models.products

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build

data class Product(
        val id: String,
        val price: String,
        val brand: String,
        val model: String,
        val category: String,
        val storeNickName: String,
        val features: String,
        val units: String,
        val images: MutableList<String>,
        val points: List<Double>,
        val comments: List<Comments>
)

{
    constructor(price: String, brand:String, model: String, category:String, features: String, units: String,images: MutableList<String>) :
            this("",price,brand,model,category,"",features,units,images,listOf(),listOf())


    }

