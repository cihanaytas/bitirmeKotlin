package com.example.bitirmeprojesi.methods

import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.service.SimpleStoreApi


class StoreWorkFlow(val serviceStore: SimpleStoreApi) {

//    suspend fun test(): String? {
//        println("susp geldim")
//        val req = serviceStore.testStore().await()
//        if (req.isSuccessful) {
//            return "success " + req.body()
//        } else {
//            return "bos"
//        }
//    }
//
//
//    suspend fun cihan(): ReqBodyLogin? {
//        val req = serviceStore.cihan().await()
//        if(req.isSuccessful){
//            return req.body()
//        }
//        else{
//            return ReqBodyLogin("","")
//        }
//    }



    suspend fun getProductListPaging(page : Int): List<Product>? {
        val sorgu = serviceStore.getMyAllProducts(page).await()

        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }

    suspend fun getProduct(id:Long): Product? {
        val sorgu = serviceStore.getProduct(id).await()
        if(sorgu.isSuccessful){
            return sorgu.body()
        }
        else{
            return null
        }
    }

}