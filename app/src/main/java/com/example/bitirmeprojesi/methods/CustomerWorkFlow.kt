package com.example.bitirmeprojesi.methods

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


}


