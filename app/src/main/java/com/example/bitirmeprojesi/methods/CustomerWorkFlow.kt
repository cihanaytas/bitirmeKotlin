package com.example.bitirmeprojesi.methods

import com.example.bitirmeprojesi.models.StoreDetails
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.service.SimpleCustomerApi


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


    suspend fun getProduct(id:Long): Product? {
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



}


