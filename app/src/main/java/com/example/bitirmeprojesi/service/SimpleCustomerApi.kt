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






//
//    @GET("cars")
//    fun getCars(): Call<List<Car>>
//
//    @GET("cars")
//    fun getCarss(): Deferred<Response<List<Car>>>
//
//    @GET("cars")
//    fun getDeneme() : Single<List<Car>>
//
//
//    @GET("car/{car_id}")
//    fun getCarbyCarID(@Path("car_id") carID:Long): Single<Car>
//
//    @GET("car/{car_id}")
//    fun getCarbyCarID1(@Path("car_id") carID:Long): Deferred<Response<Car>>
//
//
//    @GET("resptest")
//    fun getCihan(): Call<Customer>






}