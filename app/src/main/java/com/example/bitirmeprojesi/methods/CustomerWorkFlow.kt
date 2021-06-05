package com.example.bitirmeprojesi.methods

import com.example.bitirmeprojesi.models.ShopDto
import com.example.bitirmeprojesi.models.StoreDetails
import com.example.bitirmeprojesi.models.products.CartItem
import com.example.bitirmeprojesi.models.products.Comments
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.service.SimpleCustomerApi
import kotlinx.coroutines.Deferred
import okhttp3.Response


class CustomerWorkFlow(val serviceCustomer: SimpleCustomerApi) {

    suspend fun test(): String? {
        val req = serviceCustomer.testCustomer().await()
        if (req.isSuccessful) {
            return "success " + req.body()
        } else {
            return "bos"
        }
    }



    suspend fun getProductList(): List<Product>? {
        val sorgu = serviceCustomer.getAllProducts().await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }


    suspend fun getProductListPaging(page : Int): List<Product>? {
        val sorgu = serviceCustomer.getAllProductsPaging(page).await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }

    suspend fun getProductListPagingCategory(page : Int,category: String): List<Product>? {
        val sorgu = serviceCustomer.getAllProductsPagingCategory(page,category).await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }


    suspend fun getProduct(id:Long): List<Product>?{
        val sorgu = serviceCustomer.getProduct(id).await()
        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }

    suspend fun getProductListByNickname(storename: String): List<Product>? {
        val sorgu = serviceCustomer.getAllProductsByNickname(storename).await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }

    suspend fun getStoreDetail(storename: String): StoreDetails? {
        val sorgu = serviceCustomer.getStoreDetail(storename).await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }

    suspend fun getShoppingList(): List<ShopDto>? {
        val sorgu = serviceCustomer.getShoppingList().await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }


    suspend fun getCartItemList(shoppingId: Long): List<CartItem>? {
        val sorgu = serviceCustomer.getCartItemList(shoppingId).await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }


    suspend fun getCommentList(productId: Long): List<Comments>? {
        val sorgu = serviceCustomer.getCommentList(productId).await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }

    suspend fun getFavouriteList(): List<Long>? {
        val sorgu = serviceCustomer.getFavouriteList().await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }

    suspend fun getFavouriteProductList(page: Int): List<Product>? {
        val sorgu = serviceCustomer.getFavouriteProducts(page).await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }


    suspend fun  getProductListByCategoryList(page:Int,categoryList: Array<String>,min:Double,max:Double): List<Product>? {
        if(categoryList.isEmpty()){
            val sorgu = serviceCustomer.getProductListByCategoryList(page,min,max).await()
            if(sorgu.isSuccessful){
                return sorgu.body()
            }
            else{
                return null
            }

        }
        else if(min==0.0 && max==0.0){
            val str = listToStr(categoryList)
            val sorgu = serviceCustomer.getProductListByCategoryList(page,str).await()
            if(sorgu.isSuccessful){
                return sorgu.body()
            }
            else{
                return null
            }
        }
        else{
            val str = listToStr(categoryList)
            val sorgu = serviceCustomer.getProductListByCategoryList(page,str,min,max).await()
            if(sorgu.isSuccessful){
                return sorgu.body()
            }
            else{
                return null
            }
        }



    }

    private fun listToStr(list: Array<String>): String {
        var str =""
        for(i in list.toList()){
            if(i==list[list.lastIndex]){
                str+=i
            }else{
                str+=i
                str+=","
            }
        }
        return str
    }



}


