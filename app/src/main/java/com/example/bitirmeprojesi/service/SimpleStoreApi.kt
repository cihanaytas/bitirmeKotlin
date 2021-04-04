package com.example.bitirmeprojesi.service



import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.models.products.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface SimpleStoreApi : SimpleNewApi {





    @POST("addlaptop")
    fun laptopekle(@Body laptop: Laptop) : Call<String>

    @POST("addphone")
    fun telefonekle(@Body phone: Phone) : Call<String>

    @POST("addtablet")
    fun tabletekle(@Body tablet: Tablet) : Call<String>

    @POST("addtv")
    fun tvekle(@Body television: Television) : Call<String>

    @POST("addheadphone")
    fun kulaklikekle(@Body headPhone: HeadPhone) : Call<String>







}