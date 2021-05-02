package com.example.bitirmeprojesi.viewmodel.customer

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.activities.serviceStore
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.methods.StoreWorkFlow
import com.example.bitirmeprojesi.models.products.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class UrunDetayiViewModel(application: Application) : BaseViewModel(application) {

    val productLiveData = MutableLiveData<Product>()


    fun getData(id : Long) {
        val wf = CustomerWorkFlow(serviceCustomer)
        GlobalScope.launch(Dispatchers.Main)  {
            val product = wf.getProduct(id)
            productLiveData.value = product
        }
    }


    fun getDataStore(id : Long) {
        val wf = StoreWorkFlow(serviceStore)
        GlobalScope.launch(Dispatchers.Main)  {
            val product = wf.getProduct(id)
            productLiveData.value = product
        }
    }


}