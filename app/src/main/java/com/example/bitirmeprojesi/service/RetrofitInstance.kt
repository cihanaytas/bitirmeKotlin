package com.example.bitirmeprojesi.service


import com.example.bitirmeprojesi.service.BasicAuthInterceptor
import com.example.bitirmeprojesi.service.Constants.Companion.BASE_URL_CUSTOMER
import com.example.bitirmeprojesi.service.Constants.Companion.BASE_URL_STORE
import com.example.bitirmeprojesi.service.Constants.Companion.BASE_URL_NEW
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

object RetrofitInstance {

//    private var api: SimpleApi? = null
    private var api: Retrofit? = null
    private var client : OkHttpClient? = null

    fun createInstanceCustomer(username: String, password: String): Retrofit {
        client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(username, password))
            .build()


        val gson = GsonBuilder().apply {
            setLenient()
            registerTypeAdapter(Date::class.java,
                JsonDeserializer<Date> { json, typeOfT, context ->
                    if (json.asJsonPrimitive.isNumber) {
                        Date(json.asJsonPrimitive.asLong * 1000)
                    } else {
                        null
                    }
                })
        }.create()


        if (api == null)
            api = Retrofit.Builder()
                .baseUrl(BASE_URL_CUSTOMER)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                //.create(SimpleApi::class.java)

        return api as Retrofit
    }



    fun createInstanceStore(username: String, password: String): Retrofit {
        client = OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor(username, password))
                .build()

        val gson = GsonBuilder().apply {
            setLenient()
            registerTypeAdapter(Date::class.java,
                    JsonDeserializer<Date> { json, typeOfT, context ->
                        if(json.asJsonPrimitive.isNumber) {
                            Date(json.asJsonPrimitive.asLong * 1000)
                        } else {
                            null
                        }
                    })
        }.create()


        if (api == null)
            api =  Retrofit.Builder()
                    .baseUrl(BASE_URL_STORE)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

        return api as Retrofit
    }








    fun createInstanceNew(): Retrofit {
        val clientt = OkHttpClient.Builder().build()
        val gson = GsonBuilder()
                .setLenient()
                .create()

        if (api == null)
            api =  Retrofit.Builder()
                .baseUrl(BASE_URL_NEW)
                .client(clientt)
                    .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
               // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return api as Retrofit
    }



}