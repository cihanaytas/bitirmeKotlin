package com.example.bitirmeprojesi.adapters

import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.CartRowBinding
import com.example.bitirmeprojesi.models.products.CartItem
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.view.customer.AlisverisDetayFragmentDirections
import com.example.bitirmeprojesi.view.customer.AlisverislerFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_row.view.*
import java.io.File



class CartRecyclerAdapter2(val productListesi: ArrayList<CartItem>): RecyclerView.Adapter<CartRecyclerAdapter2.CartViewHolder>(), UrunClickListener{


    class CartViewHolder(var view: CartRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CartRowBinding>(inflater, R.layout.cart_row,parent,false)


        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productListesi.size
    }

    override fun onBindViewHolder(holder: CartRecyclerAdapter2.CartViewHolder, position: Int) {
        holder.view.product = productListesi[position]
        holder.view.listener = this

        val total = productListesi[position].product.price.toDouble() * productListesi[position].adet

        holder.view.textViewAdet.text = "Adet: " + productListesi[position].adet.toString()
        holder.view.productTotalPriceTextView.text = (total).toString() + "₺"
        holder.view.quantitySpinner.visibility = View.GONE
        holder.view.deleteProductButton.visibility = View.GONE



        val storageDirectory =   Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        if(productListesi.get(position).product.images.isNotEmpty()){
            val img = productListesi.get(position).product.images[0]
            val file = File(storageDirectory, "${img}.jpg")
            Picasso.get().load(file)
                    .into(holder.itemView.productImageView)
        }
    }

    fun productListesiniGuncelle(yeniProductListesi: List<CartItem>){
        productListesi.clear()
        productListesi.addAll(yeniProductListesi)
        notifyDataSetChanged()
    }

    override fun urunTiklandi(view: View) {
        val id = view.product_id.text.toString().toLong()

        val action = AlisverisDetayFragmentDirections.actionAlisverisDetayFragmentToUrunPageFragment(id,0,"cart")
        Navigation.findNavController(view).navigate(action)

    }


}