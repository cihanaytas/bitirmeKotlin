package com.example.bitirmeprojesi.service

import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.models.UserAccount
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SimpleNewApi {

    @POST("addcustomer")
    fun addCustomer(@Body userData: UserAccount) : Call<String>
//
//    @POST("login")
//    fun login(@Body reqBodyLogin: ReqBodyLogin) : Call<Boolean>

    @POST("login")
    fun login(@Body reqBodyLogin: ReqBodyLogin) : Deferred<Response<Boolean>>

//    @GET("userrole/{username}")
//    fun getUserRole(@Path("username") username: String) : Call<String>

    @GET("userrole/{username}")
    fun getUserRole(@Path("username") username: String): Deferred<Response<String>>
}