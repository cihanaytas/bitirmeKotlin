package com.example.bitirmeprojesi.adapters

import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.AlisverisRecyclerRowBinding
import com.example.bitirmeprojesi.databinding.NotificationRecyclerRowCBinding
import com.example.bitirmeprojesi.databinding.NotificationsRecyclerRowBinding
import com.example.bitirmeprojesi.models.NotificationProduct
import com.example.bitirmeprojesi.models.ShopDto
import com.example.bitirmeprojesi.models.products.CartItem
import com.example.bitirmeprojesi.view.customer.CustomerNotificationsFragment
import com.example.bitirmeprojesi.view.customer.CustomerNotificationsFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_giris.*
import kotlinx.android.synthetic.main.urun_recycler_row.view.*
import java.io.File

class NotificationRecyclerAdapterC(val notificationList: ArrayList<NotificationProduct>)
    : RecyclerView.Adapter<NotificationRecyclerAdapterC.NotificationViewHolder>(){



    class NotificationViewHolder(val view: NotificationRecyclerRowCBinding): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.notifications_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<NotificationRecyclerRowCBinding>(inflater, R.layout.notification_recycler_row_c,parent,false)



        return NotificationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.view.notification = notificationList[position]
        val onay = if(notificationList[position].onay==true) "onaylandı" else "iptal edildi"
      //  val onay = notificationList[position].onay==true ?
        holder.view.notificationTextC.text = notificationList[position].shopId.toString() + " numaralı siparişinde bulunan ürün mağaza sahibi tarafından " + onay


        val storageDirectory =   Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES);

        val img = notificationList.get(position).imageURI
        val file = File(storageDirectory, "${img}.jpg")
        Picasso.get().load(file)
            .into(holder.view.notificationImageC)


        holder.view.buttonUruneGit.setOnClickListener {
            val action = CustomerNotificationsFragmentDirections.actionCustomerNotificationsFragmentToUrunPageFragment(notificationList[position].productId,0,"")
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.buttonSepeteGit.setOnClickListener {
            val action = CustomerNotificationsFragmentDirections.actionCustomerNotificationsFragmentToAlisverisDetayFragment(notificationList[position].shopId)
            Navigation.findNavController(it).navigate(action)
        }



    }

    fun notificationsListesiniGuncelle(yeniNotificationList: List<NotificationProduct>){
        notificationList.clear()
        notificationList.addAll(yeniNotificationList)
        notifyDataSetChanged()
    }


}