package com.example.bitirmeprojesi.adapters

import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.AlisverisRecyclerRowBinding
import com.example.bitirmeprojesi.databinding.NotificationsRecyclerRowBinding
import com.example.bitirmeprojesi.models.NotificationProduct
import com.example.bitirmeprojesi.models.ShopDto
import com.example.bitirmeprojesi.models.products.CartItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_giris.*
import kotlinx.android.synthetic.main.urun_recycler_row.view.*
import java.io.File

class NotificationAdapter(val notificationList: ArrayList<NotificationProduct>,
notificationInterface:NotificationInterface?)
    : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>(){

    var notificationInterface: NotificationAdapter.NotificationInterface? = notificationInterface

    class NotificationViewHolder(val view: NotificationsRecyclerRowBinding,notificationInterface:NotificationInterface?): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.notifications_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<NotificationsRecyclerRowBinding>(inflater, R.layout.notifications_recycler_row,parent,false)

        view.notificationInterface = notificationInterface

        return NotificationViewHolder(view,notificationInterface)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.view.notification = notificationList[position]
        holder.view.notificationText.text = notificationList[position].bildirim


        val storageDirectory =   Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

            val img = notificationList.get(position).imageURI
            val file = File(storageDirectory, "${img}.jpg")
            Picasso.get().load(file)
                    .into(holder.view.notificationImage)



    }

    fun notificationsListesiniGuncelle(yeniNotificationList: List<NotificationProduct>){
        notificationList.clear()
        notificationList.addAll(yeniNotificationList)
        notifyDataSetChanged()
    }

    interface NotificationInterface {
        fun onay(notificationID: Long?)
        fun red(notificationID: Long?)
    }
}