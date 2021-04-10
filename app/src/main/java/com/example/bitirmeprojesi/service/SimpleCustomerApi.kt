package com.example.bitirmeprojesi.service


import com.example.bitirmeprojesi.models.products.Product
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.*


interface SimpleCustomerApi{


    @GET("logout")
    fun logOut(): Call<String>

    @GET("test")
    fun testCustomer(): Deferred<Response<String>>

    @GET("products")
    fun getAllProducts(): Deferred<Response<List<Product>>>

    @GET("product/{id}")
    fun getProduct(@Path("id") id:Long) : Deferred<Response<Product>>




}