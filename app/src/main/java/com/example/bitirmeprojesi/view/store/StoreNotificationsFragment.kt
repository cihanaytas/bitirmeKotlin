package com.example.bitirmeprojesi.view.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.serviceStore
import com.example.bitirmeprojesi.adapters.NotificationAdapter
import com.example.bitirmeprojesi.models.NotificationProduct
import com.example.bitirmeprojesi.models.products.Comments
import com.example.bitirmeprojesi.viewmodel.store.StoreViewModel
import kotlinx.android.synthetic.main.fragment_comments.*
import kotlinx.android.synthetic.main.fragment_store_notifications.*
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoreNotificationsFragment : Fragment(), NotificationAdapter.NotificationInterface {

    private lateinit var viewModel : StoreViewModel
    private var notificationRecyclerAdapter = NotificationAdapter(arrayListOf(),this)


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
      //  viewModel.getNotifications()

        notificationsListRecyclerView.layoutManager = LinearLayoutManager(context)
        notificationsListRecyclerView.adapter = notificationRecyclerAdapter

        observeLiveData()


    }


    private fun observeLiveData(){
        viewModel.notifications.observe(viewLifecycleOwner, Observer { notificationList ->
            notificationList?.let {
                if(notificationList.isEmpty()){
                    noNotificationMessage.visibility = View.VISIBLE
                    notificationsListRecyclerView.visibility = View.GONE
                }

                else{
                    noNotificationMessage.visibility = View.GONE
                    notificationsListRecyclerView.visibility = View.VISIBLE
                    var newNotificationList = arrayListOf<NotificationProduct>()
                    for(notificationProduct in notificationList){

                        if(notificationProduct.onay==false){
                            newNotificationList.add(notificationProduct)
                        }
                    }
                    notificationRecyclerAdapter.notificationsListesiniGuncelle(newNotificationList)
                }
            }
        })

    }

    override fun onay(notificationID: Long?) {
        notificationConfirmation(notificationID!!,true)
    }

    override fun red(notificationID: Long?) {
        notificationConfirmation(notificationID!!,false)
    }


    private fun notificationConfirmation(notificationID: Long, onay: Boolean){
        val sorgu = serviceStore.notificationConfirmation(notificationID,onay)

        sorgu.enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    if(onay==true)
                    Toast.makeText(activity, "Onaylandı", Toast.LENGTH_LONG).show()
                    else
                    Toast.makeText(activity, "Reddedildi", Toast.LENGTH_LONG).show()

                    viewModel.getNotifications()
                }
            }

        })

    }


}