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

    fun urunleriAl(page:Int,category:String){
        val wf = CustomerWorkFlow(serviceCustomer)
        GlobalScope.launch(Dispatchers.Main) {
            if(category.isNotEmpty()){
                val productList = wf.getProductListPagingCategory(page,category)
                urunler.value = productList
            }else {
                val productList = wf.getProductListPaging(page)
                urunler.value = productList
            }
        }

    }









}