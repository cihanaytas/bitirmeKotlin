package com.example.bitirmeprojesi.viewmodel.customer

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.models.products.CartItem
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.repository.CartRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CustomerUrunlerViewModel(application: Application) : BaseViewModel(application) {

    val urunler = MutableLiveData<List<Product>>()
    val urunHataMesaji = MutableLiveData<Boolean>()
    val urunYukleniyor = MutableLiveData<Boolean>()

    val repo = CartRepo()


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


    fun cartUrunEkle(product: Product): Boolean {
        return repo.addItemToCart(product)
    }


    fun getCart(): MutableLiveData<List<CartItem>> {
        return repo.getCart()
    }

    fun getTotalPrice(): MutableLiveData<Double> {
        return repo.getTotalPrice()
    }


    fun removeItemFromCart(cartItem: CartItem?) {
        if (cartItem != null) {
            repo.removeItemFromCart(cartItem)
        }
    }

    fun changeQuantity(cartItem: CartItem?, quantity: Int) {
        if (cartItem != null) {
            repo.changeQuantity(cartItem, quantity)
        }
    }

    fun resetCart(){
        repo.initCart()
    }







}