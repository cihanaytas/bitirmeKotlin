package com.example.bitirmeprojesi.viewmodel.customer

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.models.products.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomerUrunlerViewModel(application: Application) : BaseViewModel(application) {

    val urunler = MutableLiveData<List<Product>>()
    val urunHataMesaji = MutableLiveData<Boolean>()
    val urunYukleniyor = MutableLiveData<Boolean>()

    fun urunleriAl(page:Int){
        val wf = CustomerWorkFlow(serviceCustomer)
        GlobalScope.launch(Dispatchers.Main) {
         //   val productList = wf.getProductList()
            val productList = wf.getProductListPaging(page)
            urunler.value = productList
        }

    }





}