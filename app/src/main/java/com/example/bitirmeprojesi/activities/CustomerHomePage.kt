package com.example.bitirmeprojesi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.methods.WorkFlow
import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.service.RetrofitInstance
import com.example.bitirmeprojesi.service.SimpleCustomerApi
import com.example.bitirmeprojesi.view.serviceCustomer
import com.example.bitirmeprojesi.view.sharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
val wf = CustomerWorkFlow()
class CustomerHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home_page)

        serviceCustomer = RetrofitInstance.createInstanceCustomer("mustaf12","987654").create(SimpleCustomerApi::class.java)
        val wf = CustomerWorkFlow()
        GlobalScope.launch(Dispatchers.Main) {
            println(wf.test(serviceCustomer))
        }


    }


    fun cikisYap(view: View) {
        sharedPreferences.edit().remove("USERNAME").apply()
        sharedPreferences.edit().remove("PASSWORD").apply()
        sharedPreferences.edit().remove("CHECKBOX").apply()

        val req = serviceCustomer.logOut()
        req.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                println("cikis")
            }

        })


        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}