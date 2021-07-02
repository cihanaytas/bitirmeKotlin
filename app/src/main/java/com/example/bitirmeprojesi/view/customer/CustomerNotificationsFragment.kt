package com.example.bitirmeprojesi.view.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.adapters.NotificationAdapter
import com.example.bitirmeprojesi.adapters.NotificationRecyclerAdapterC
import com.example.bitirmeprojesi.models.NotificationProduct
import com.example.bitirmeprojesi.viewmodel.customer.AlisverislerViewModel
import com.example.bitirmeprojesi.viewmodel.store.StoreViewModel
import kotlinx.android.synthetic.main.fragment_customer_notifications.*
import kotlinx.android.synthetic.main.fragment_store_notifications.*


class CustomerNotificationsFragment : Fragment() {

    private lateinit var viewModel : AlisverislerViewModel
    private var notificationRecyclerAdapter = NotificationRecyclerAdapterC(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(AlisverislerViewModel::class.java)

        CustomerNotificationsListRecyclerView.layoutManager = LinearLayoutManager(context)
        CustomerNotificationsListRecyclerView.adapter = notificationRecyclerAdapter

        viewModel.getNotifications()

        observeLiveData()
    }


    private fun observeLiveData(){
        viewModel.liveCustomerNotifications.observe(viewLifecycleOwner, Observer { notificationList->
            notificationList?.let {
                if(notificationList.isEmpty()){
                    noNotificationMessage2.visibility = View.VISIBLE
                    CustomerNotificationsListRecyclerView.visibility = View.GONE
                }

                else{
                    noNotificationMessage2.visibility = View.GONE
                    CustomerNotificationsListRecyclerView.visibility = View.VISIBLE
                    notificationRecyclerAdapter.notificationsListesiniGuncelle(notificationList)
                }
            }
        })
    }



}