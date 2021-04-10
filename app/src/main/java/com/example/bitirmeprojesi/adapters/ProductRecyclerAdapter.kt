package com.example.bitirmeprojesi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.UrunRecyclerRowBinding
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.view.customer.UrunlerFragmentDirections
import kotlinx.android.synthetic.main.urun_recycler_row.view.*


class ProductRecyclerAdapter(val productListesi : ArrayList<Product>) : RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>(),UrunClickListener{

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


//        holder.itemView.isim.text = besinListesi.get(position).besinIsim
//        holder.itemView.kalori.text = besinListesi.get(position).besinKalori
//        holder.itemView.setOnClickListener {
//            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi.get(position).uuid)
//            Navigation.findNavController(it).navigate(action)
//        }
        //holder.itemView.imageView.gorselIndir(besinListesi.get(position).besinGorsel, placeholderYap(holder.itemView.context))

    }

    fun productListesiniGuncelle(yeniProductListesi: List<Product>){
        productListesi.clear()
        productListesi.addAll(yeniProductListesi)
        notifyDataSetChanged()
    }

    override fun urunTiklandi(view: View) {
        val uuid = view.urun_id.text.toString().toIntOrNull()
        uuid?.let {
            val action = UrunlerFragmentDirections.actionUrunlerFragmentToUrunPageFragment()
            Navigation.findNavController(view).navigate(action)
        }

    }


}