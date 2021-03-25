package com.example.bitirmeprojesi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.view.sharedPreferences

class StoreHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_home_page)
    }


    fun cikisYap(view: View){
        sharedPreferences.edit().remove("USERNAME").apply()
        sharedPreferences.edit().remove("PASSWORD").apply()
        sharedPreferences.edit().remove("CHECKBOX").apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}