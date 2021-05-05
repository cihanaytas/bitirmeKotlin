package com.example.bitirmeprojesi.viewmodel.customer

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.models.ShopDto
import com.example.bitirmeprojesi.models.products.CartItem
import com.example.bitirmeprojesi.repository.CartRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AlisverislerViewModel (application: Application) : BaseViewModel(application) {

    val alisverisler = MutableLiveData<List<ShopDto>>()
    val cartUrunler = MutableLiveData<List<CartItem>>()
    val wf = CustomerWorkFlow(serviceCustomer)
    val repo = CartRepo()

    fun getShoppingList(){
        GlobalScope.launch(Dispatchers.Main) {
            alisverisler.value = wf.getShoppingList()
        }
    }


    fun getCartItemList(shoppingId: Long){
        GlobalScope.launch(Dispatchers.Main) {
            cartUrunler.value = wf.getCartItemList(shoppingId)
        }
    }

    fun getTotalPrice(): MutableLiveData<Double> {
        var totalPrice = 0.0
        println(cartUrunler.value)
        return repo.getTotalPrice()
    }


}