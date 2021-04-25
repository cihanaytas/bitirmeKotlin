package com.example.bitirmeprojesi.adapters

import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.UrunRecyclerRowBinding
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.view.customer.StoreProfileFragmentDirections
import com.example.bitirmeprojesi.view.customer.UrunlerFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_urun_page.*
import kotlinx.android.synthetic.main.urun_recycler_row.view.*
import java.io.File


class ProductRecyclerAdapter(val productListesi : ArrayList<Product>,nereden:String) : RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>(),UrunClickListener{

    val x = nereden
    var page = 0
    class ProductViewHolder(var view : UrunRecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

//    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
       // val view = inflater.inflate(R.layout.urun_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<UrunRecyclerRowBinding>(inflater, R.layout.urun_recycler_row,parent,false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productListesi.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.view.urun = productListesi[position]
        holder.view.listener = this

        val storageDirectory =   Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES);

        if(productListesi.get(position).images.isNotEmpty()){
            val img = productListesi.get(position).images[0]
            val file = File(storageDirectory, "${img}.jpg")
            Picasso.get().load(file)
                    .into(holder.itemView.imageView)
        }


    }

    fun productListesiniGuncelle(yeniProductListesi: List<Product>){
        productListesi.clear()
        productListesi.addAll(yeniProductListesi)
        notifyDataSetChanged()
    }

    override fun urunTiklandi(view: View) {
        val id = view.urun_id.text.toString().toLong()

        id?.let {
            if(x=="storeprofile"){
                val action = StoreProfileFragmentDirections.actionStoreProfileFragmentToUrunPageFragment(it,page)
                Navigation.findNavController(view).navigate(action)
            }

            else if(x=="urunler"){
                val action = UrunlerFragmentDirections.actionUrunlerFragmentToUrunPageFragment(it,page)
                Navigation.findNavController(view).navigate(action)
            }

        }

    }




}