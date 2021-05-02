package com.example.bitirmeprojesi.service


import com.example.bitirmeprojesi.models.products.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface SimpleStoreApi : SimpleNewApi {


    @POST("addproduct")
    fun urunEkle(@Body product: Product) : Call<String>

    @PUT("addproduct/{productId}")
    fun urunGuncelle(@Path("productId") productId:Long,@Body product: Product) : Call<String>

    @GET("myproducts/{page}")
    fun getMyAllProducts(@Path("page") page:Int): Deferred<Response<List<Product>>>

    @GET("product/{id}")
    fun getProduct(@Path("id") id:Long) : Deferred<Response<Product>>



}