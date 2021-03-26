package com.example.bitirmeprojesi.service



import com.example.bitirmeprojesi.models.ReqBodyLogin
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET



interface SimpleStoreApi : SimpleNewApi {


    @GET("test")
    fun testStore(): Deferred<Response<String>>

    @GET("xx")
    fun cihan(): Deferred<Response<ReqBodyLogin>>







}