package com.example.bitirmeprojesi.view.store

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
import com.example.bitirmeprojesi.models.NotificationProduct
import com.example.bitirmeprojesi.models.products.Comments
import com.example.bitirmeprojesi.viewmodel.store.StoreViewModel
import kotlinx.android.synthetic.main.fragment_comments.*
import kotlinx.android.synthetic.main.fragment_store_notifications.*


class StoreNotificationsFragment : Fragment() {

    private lateinit var viewModel : StoreViewModel
    private var notificationRecyclerAdapter = NotificationAdapter(arrayListOf())


            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(StoreViewModel::class.java)
        viewModel.getNotifications()

        notificationsListRecyclerView.layoutManager = LinearLayoutManager(context)
        notificationsListRecyclerView.adapter = notificationRecyclerAdapter

        observeLiveData()


    }


    fun observeLiveData(){
        viewModel.notifications.observe(viewLifecycleOwner, Observer { notificationList ->
            notificationList?.let {
                if(notificationList.isEmpty()){
                 //   yorumYokMessage.visibility = View.VISIBLE
                    notificationsListRecyclerView.visibility = View.GONE
                }

                else{
                    //yorumYokMessage.visibility = View.GONE
                    notificationsListRecyclerView.visibility = View.VISIBLE
                    notificationRecyclerAdapter.notificationsListesiniGuncelle(notificationList as ArrayList<NotificationProduct>)
                }
            }
        })

    }



}