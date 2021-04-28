package com.example.bitirmeprojesi.service


import com.example.bitirmeprojesi.models.products.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface SimpleStoreApi : SimpleNewApi {


    @POST("addproduct")
    fun urunEkle(@Body product: Product) : Call<String>





}