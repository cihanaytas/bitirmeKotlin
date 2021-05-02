package com.example.bitirmeprojesi.viewmodel.customer

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.models.ShopDto
import com.example.bitirmeprojesi.models.products.CartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AlisverislerViewModel (application: Application) : BaseViewModel(application) {

    val alisverisler = MutableLiveData<List<ShopDto>>()
    val cartUrunler = MutableLiveData<List<CartItem>>()

    val wf = CustomerWorkFlow(serviceCustomer)

    fun getShoppingList(){
        GlobalScope.launch(Dispatchers.Main) {
            alisverisler.value = wf.getShoppingList()
        }
    }


}