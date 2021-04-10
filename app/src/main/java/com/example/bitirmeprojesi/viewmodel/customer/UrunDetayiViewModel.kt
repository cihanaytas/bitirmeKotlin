package com.example.bitirmeprojesi.viewmodel.customer

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.viewmodel.BaseViewModel
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


}