package com.example.bitirmeprojesi.methods

import android.content.SharedPreferences
import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.service.SimpleNewApi

class WorkFlow() {

    suspend fun getUserRole(username: String, service: SimpleNewApi): String {
        val carRequest = service.getUserRole(username).await()
        if (carRequest.isSuccessful) {
            val role = carRequest.body()
            return role.toString()
        } else {
            return ""
        }
    }


    suspend fun getLoginControl(user: ReqBodyLogin,service: SimpleNewApi,sharedPreferences: SharedPreferences,checked:Boolean): Boolean? {
        val req = service.login(user).await()
        if(req.isSuccessful){
                        if (req.body()?.equals(true)!!) {
                            return true
                            println("giriş başarılı")
                           //Toast.makeText(activity, "Giriş Başarılı", Toast.LENGTH_LONG).show()


//                            changeActivityGiris()
                        } else {
                            println("istek başarılı ama username veya pssword yanlşış")
                            return false

                           // Toast.makeText(activity, "Kullanıcı adınız veya parolanız hatalı", Toast.LENGTH_LONG).show()
                        }
        }else{
            return false
        }
    }



}