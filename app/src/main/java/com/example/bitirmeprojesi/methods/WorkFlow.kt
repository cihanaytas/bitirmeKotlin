package com.example.bitirmeprojesi.methods

import android.content.SharedPreferences
import android.widget.Toast
import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.service.RetrofitInstance
import com.example.bitirmeprojesi.service.SimpleCustomerApi
import com.example.bitirmeprojesi.service.SimpleNewApi

import retrofit2.await

class WorkFlow(val service: SimpleNewApi) {


    suspend fun getUserRole(username: String): String {
        val carRequest = service.getUserRole(username).await()
        if (carRequest.isSuccessful) {
            val role = carRequest.body()
            return role.toString()
        } else {
            return ""
        }
    }


    suspend fun getLoginControl(user: ReqBodyLogin): Boolean? {
        val req = service.login(user).await()
        if(req.isSuccessful){
                        if (req.body()?.equals(true)!!) {
                            println("giriş başarılı")
                            return true
                       } else {
                            println("istek başarılı ama username veya pssword yanlşış")
                            return false
                        }
        }else{
            return false
        }
    }

}