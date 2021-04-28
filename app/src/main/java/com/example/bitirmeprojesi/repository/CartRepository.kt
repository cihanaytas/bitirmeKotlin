package com.example.bitirmeprojesi.repository

import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.models.products.CartItem
import com.example.bitirmeprojesi.models.products.Product

class CartRepo {

    val mutableCart = MutableLiveData<List<CartItem>>()
    val mutableTotalPrice = MutableLiveData<Double>()


    fun getCart(): MutableLiveData<List<CartItem>> {
        if(mutableCart.value == null){
            initCart()
        }
        return mutableCart
    }


    fun initCart(){
        val list = arrayListOf<CartItem>()
        mutableCart.value = list
        calculateCartTotal()
    }

    fun addItemToCart(product: Product): Boolean {
        if(mutableCart.value == null){
            initCart()
        }

        var cartItemList  = mutableCart.value as ArrayList<CartItem>

        for(cartItem in cartItemList){
            if(cartItem.product.id.equals(product.id)){
                if(cartItem.adet == 5){
                    return false
                }

                val index = cartItemList.indexOf(cartItem)
                cartItem.adet++
                cartItemList.set(index,cartItem)

                mutableCart.value = cartItemList
                calculateCartTotal()
                return true
            }
        }

        val cartItem = CartItem(product,1)
        cartItemList.add(cartItem)
        mutableCart.value = cartItemList
        calculateCartTotal()
        return true
    }


    fun removeItemFromCart(cartItem: CartItem){
        if(mutableCart.value == null){
            return
        }
        var cartItemList  = mutableCart.value as ArrayList<CartItem>
        cartItemList.remove(cartItem)
        mutableCart.value = cartItemList
        calculateCartTotal()
    }


    fun changeQuantity(cartItem: CartItem, quantity:Int){
        if(mutableCart.value == null){
            return
        }
        var cartItemList  = mutableCart.value as ArrayList<CartItem>
        val updatedItem = CartItem(cartItem.product,quantity)
        cartItemList.set(cartItemList.indexOf(cartItem),updatedItem)
        mutableCart.value = cartItemList
        calculateCartTotal()
    }


    fun calculateCartTotal(){
        if(mutableCart.value == null){
            return
        }
        var total = 0.0
        val cartItemList = mutableCart.value
        if (cartItemList != null) {
            for(cartItem in cartItemList){

                total+= cartItem.product.price.toDouble() * cartItem.adet.toDouble()
            }
            mutableTotalPrice.value = total
        }
    }


    fun getTotalPrice(): MutableLiveData<Double> {
        if(mutableCart.value == null){
            mutableTotalPrice.value = 0.0
        }
        return mutableTotalPrice
    }

}