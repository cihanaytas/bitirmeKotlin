package com.example.bitirmeprojesi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.AlisverisRecyclerRowBinding
import com.example.bitirmeprojesi.databinding.UrunRecyclerRowBinding
import com.example.bitirmeprojesi.models.ShopDto
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.view.customer.AlisverislerFragmentDirections
import kotlinx.android.synthetic.main.alisveris_recycler_row.view.*
import kotlinx.android.synthetic.main.urun_recycler_row.view.*
import java.text.ParsePosition
import java.text.SimpleDateFormat

class ShopRecyclerAdapter (val alisverisListesi: ArrayList<ShopDto>) : RecyclerView.Adapter<ShopRecyclerAdapter.ShopViewHolder>(),UrunClickListener{


    class ShopViewHolder(var view : AlisverisRecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<AlisverisRecyclerRowBinding>(inflater, R.layout.alisveris_recycler_row,parent,false)
        return ShopViewHolder(view)
    }

    override fun getItemCount(): Int {
        return alisverisListesi.size
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.view.shop = alisverisListesi[position]

        val year = alisverisListesi[position].date.substring(0,4)
        val month = alisverisListesi[position].date.substring(5,7) + "/"
        val day = alisverisListesi[position].date.substring(8,10) + "/"
        
        holder.view.tarih.text = day+month+year
        holder.view.listener = this
    }


    fun alisverisListesiniGuncelle(yeniAlisverisListesi: List<ShopDto>){
        alisverisListesi.clear()
        alisverisListesi.addAll(yeniAlisverisListesi)
        notifyDataSetChanged()
    }


    override fun urunTiklandi(view: View) {
        val shoppingId = view.shop_id.text.toString().toLong()

        val action = AlisverislerFragmentDirections.actionAlisverislerFragmentToAlisverisDetayFragment(shoppingId)
        Navigation.findNavController(view).navigate(action)
    }







}



