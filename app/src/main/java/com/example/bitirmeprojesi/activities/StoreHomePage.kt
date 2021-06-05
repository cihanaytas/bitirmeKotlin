package com.example.bitirmeprojesi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.methods.StoreWorkFlow
import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.service.RetrofitInstance
import com.example.bitirmeprojesi.service.SimpleStoreApi
import com.example.bitirmeprojesi.view.customer.UrunlerFragmentDirections
import com.example.bitirmeprojesi.view.sharedPreferences
import com.example.bitirmeprojesi.view.store.StoreUrunlerimFragmentDirections
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

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_notification,menu)

        val menuItem = menu.findItem(R.id.notification)
        val actionView = menuItem.actionView

        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }
        inflater.inflate(R.menu.menu_customer_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.miCikis){
            cikisYap()
        }

        if(item.itemId==R.id.notification){
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment2) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.storeNotificationsFragment)
        }


            return super.onOptionsItemSelected(item)

    }


    private fun cikisYap() {
        sharedPreferences.edit().remove("USERNAME").apply()
        sharedPreferences.edit().remove("CHECKBOX").apply()
        sharedPreferences.edit().remove("ROLE").apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }



}