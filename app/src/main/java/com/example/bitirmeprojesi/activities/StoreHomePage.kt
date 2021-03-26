package com.example.bitirmeprojesi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.methods.StoreWorkFlow
import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.service.RetrofitInstance
import com.example.bitirmeprojesi.service.SimpleStoreApi
import com.example.bitirmeprojesi.view.sharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


lateinit var serviceStore: SimpleStoreApi
class StoreHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_home_page)

        val username = intent.getStringExtra("username").toString()
        val password = intent.getStringExtra("password").toString()


        serviceStore = RetrofitInstance.createInstanceStore(username,password).create(SimpleStoreApi::class.java)

        val wf = StoreWorkFlow(serviceStore)


        GlobalScope.launch(Dispatchers.Main) {
            println(wf.test())

            println(wf.cihan())

        }




    }


    fun cikisYap(view: View){
        sharedPreferences.edit().remove("USERNAME").apply()
        sharedPreferences.edit().remove("CHECKBOX").apply()
        sharedPreferences.edit().remove("ROLE").apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}