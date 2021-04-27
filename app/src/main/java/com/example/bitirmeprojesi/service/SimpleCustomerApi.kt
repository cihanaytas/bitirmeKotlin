package com.example.bitirmeprojesi.service


import com.example.bitirmeprojesi.models.StoreDetails
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

    @GET("plist/{page}")
    fun getAllProductsPaging(@Path("page") page:Int): Deferred<Response<List<Product>>>

    @GET("plist/{category}/{page}")
    fun getAllProductsPagingCategory(@Path("page") page:Int, @Path("category") category:String): Deferred<Response<List<Product>>>

    @GET("products/{nickname}")
    fun getAllProductsByNickname(@Path("nickname") nickname:String): Deferred<Response<List<Product>>>

    @GET("product/{id}")
    fun getProduct(@Path("id") id:Long) : Deferred<Response<Product>>

    @POST("pointproduct/{productId}/{point}")
    fun pointToProduct(@Path("productId") productId:Long,@Path("point") point:Double): Call<Void>

    @GET("getstoredetail/{storeNickName}")
    fun getStoreDetail(@Path("storeNickName") storeNickName:String) : Deferred<Response<StoreDetails>>



}