package com.example.bitirmeprojesi.service



import com.example.bitirmeprojesi.models.Laptop
import com.example.bitirmeprojesi.models.Product
import com.example.bitirmeprojesi.models.ReqBodyLogin
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface SimpleStoreApi : SimpleNewApi {


    @GET("test")
    fun testStore(): Deferred<Response<String>>

    @GET("xx")
    fun cihan(): Deferred<Response<ReqBodyLogin>>


    @POST("addproduct")
    fun urunekle(@Body product: Product): Call<String>


    @POST("addlaptop")
    fun laptopekle(@Body laptop: Laptop) : Call<String>







}