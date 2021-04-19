package com.example.bitirmeprojesi.viewmodel.customer

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.models.StoreDetails
import com.example.bitirmeprojesi.models.products.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StoreProfileViewModel(application: Application) : BaseViewModel(application) {

    val urunler = MutableLiveData<List<Product>>()
    val stest = MutableLiveData<String>()
    val storeDetail = MutableLiveData<StoreDetails>()
    val urunHataMesaji = MutableLiveData<Boolean>()
    val urunYukleniyor = MutableLiveData<Boolean>()

    fun urunleriAl(storename:String){
        val wf = CustomerWorkFlow(serviceCustomer)
        GlobalScope.launch(Dispatchers.Main) {
            val productList = wf.getProductListByNickname(storename)
            urunler.value = productList
        }

    }


    fun storebilgi(storeName: String){
        val wf = CustomerWorkFlow(serviceCustomer)
        GlobalScope.launch(Dispatchers.Main) {
            val cevap = wf.getStoreDetail(storeName)
            storeDetail.value = cevap
        }
    }



}