package com.example.bitirmeprojesi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.AlisverisRecyclerRowBinding
import com.example.bitirmeprojesi.databinding.NotificationsRecyclerRowBinding
import com.example.bitirmeprojesi.models.NotificationProduct
import com.example.bitirmeprojesi.models.ShopDto
import kotlinx.android.synthetic.main.fragment_giris.*

class NotificationAdapter(val notificationList: ArrayList<NotificationProduct>) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>(){

    class NotificationViewHolder(val view: NotificationsRecyclerRowBinding): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.notifications_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<NotificationsRecyclerRowBinding>(inflater, R.layout.notifications_recycler_row,parent,false)

        return NotificationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.view.notificationText.text = notificationList[position].bildirim
    }

    fun notificationsListesiniGuncelle(yeniNotificationList: List<NotificationProduct>){
        notificationList.clear()
        notificationList.addAll(yeniNotificationList)
        notifyDataSetChanged()
    }
}