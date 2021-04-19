package com.example.bitirmeprojesi.viewmodel.customer

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.models.products.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StoreProfileViewModel(application: Application) : BaseViewModel(application) {

    val urunler = MutableLiveData<List<Product>>()
    val stest = MutableLiveData<String>()
    val urunHataMesaji = MutableLiveData<Boolean>()
    val urunYukleniyor = MutableLiveData<Boolean>()

    fun urunleriAl(storename:String){
        val wf = CustomerWorkFlow(serviceCustomer)
        GlobalScope.launch(Dispatchers.Main) {
            val productList = wf.getProductListByNickname(storename)
            urunler.value = productList
        }

    }


    fun storebilgi(){
        val wf = CustomerWorkFlow(serviceCustomer)
        GlobalScope.launch(Dispatchers.Main) {
            val abc = wf.test()
            stest.value = abc
        }
    }



}