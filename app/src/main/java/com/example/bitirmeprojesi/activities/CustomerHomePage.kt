package com.example.bitirmeprojesi.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import androidx.fragment.app.replace
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.service.RetrofitInstance
import com.example.bitirmeprojesi.service.SimpleCustomerApi
import com.example.bitirmeprojesi.view.customer.AlisverislerFragment
import com.example.bitirmeprojesi.view.customer.UrunlerFragment
import com.example.bitirmeprojesi.view.sharedPreferences
import kotlinx.android.synthetic.main.activity_customer_home_page.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_store_home_page.*
import kotlinx.android.synthetic.main.fragment_urunler.*


lateinit var serviceCustomer:SimpleCustomerApi
class CustomerHomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home_page)

        val username = intent.getStringExtra("username").toString()
        val password = intent.getStringExtra("password").toString()

        serviceCustomer = RetrofitInstance.createInstanceCustomer(username,password).create(SimpleCustomerApi::class.java)

       // bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.miHome -> {
                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.navhostfragment) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.urunlerFragment)

                }

                R.id.miShop -> {
                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.navhostfragment) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.alisverislerFragment)

                }

            }
            true
           // false

        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_customer_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.miCikis){
            cikisYap()
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

