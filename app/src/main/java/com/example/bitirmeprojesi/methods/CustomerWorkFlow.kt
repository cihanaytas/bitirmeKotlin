package com.example.bitirmeprojesi.methods

import com.example.bitirmeprojesi.service.SimpleCustomerApi
import com.example.bitirmeprojesi.view.serviceCustomer


class CustomerWorkFlow() {

    suspend fun test(serviceCustomer: SimpleCustomerApi): String? {
        val req = serviceCustomer.testCustomer().await()
        if (req.isSuccessful) {
            return "success " + req.body()
        } else {
            return "bos"
        }
    }


    }


