package com.example.bitirmeprojesi.viewmodel.store

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.bitirmeprojesi.activities.serviceStore
import com.example.bitirmeprojesi.methods.StoreWorkFlow
import com.example.bitirmeprojesi.models.NotificationProduct
import com.example.bitirmeprojesi.viewmodel.customer.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StoreViewModel(application: Application) :  BaseViewModel(application){

    val notifications = MutableLiveData<List<NotificationProduct>>()


    fun getNotifications(){
        GlobalScope.launch(Dispatchers.Main) {
            val wf = StoreWorkFlow(serviceStore)
            val notificationList = wf.getNotifications()
            notifications.value = notificationList
        }
    }


}