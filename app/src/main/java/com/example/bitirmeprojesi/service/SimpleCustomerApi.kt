package com.example.bitirmeprojesi.service


import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.*


interface SimpleCustomerApi{


    @GET("api/data")
    fun test(@Header("Authorization") autHeader: String): Call<String>


    @GET("testc")
    fun testCustomer(): Call<String>

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


    @GET("logout")
    fun logOut(): Call<String>




}