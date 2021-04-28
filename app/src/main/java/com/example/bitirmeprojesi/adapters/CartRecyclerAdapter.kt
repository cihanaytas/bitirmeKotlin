package com.example.bitirmeprojesi.adapters

import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.CartRowBinding
import com.example.bitirmeprojesi.models.products.CartItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_row.*
import kotlinx.android.synthetic.main.cart_row.view.*
import java.io.File


class CartRecyclerAdapter(val productListesi : ArrayList<CartItem>,cartInterface: CartInterface): RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder>(){

    var cartInterface: CartInterface? = cartInterface

    class CartViewHolder(var view: CartRowBinding, productListesi: ArrayList<CartItem>, cartInterface: CartInterface?) : RecyclerView.ViewHolder(view.root) {
        var myview : CartRowBinding

        init {
            myview = view
            myview.quantitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val quantity = position + 1
                    if(quantity == productListesi.get(adapterPosition).adet){return}
                    myview.productTotalPriceTextView.text = (productListesi[adapterPosition].product.price.toDouble() * quantity).toString()
                    if (cartInterface != null) {
                        cartInterface.changeQuantity(productListesi.get(adapterPosition),quantity)
                    }
                }

            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CartRowBinding>(inflater, R.layout.cart_row,parent,false)

        view.cartInterface = cartInterface
        return CartViewHolder(view,productListesi,cartInterface)
    }

    override fun getItemCount(): Int {
        return productListesi.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.view.product = productListesi[position]
        holder.view.productTotalPriceTextView.text = (productListesi[position].product.price.toDouble() * productListesi[position].adet).toString()
        holder.view.quantitySpinner.setSelection(productListesi[position].adet-1)
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

    interface CartInterface {
        fun deleteItem(cartItem: CartItem?)
        fun changeQuantity(cartItem: CartItem?, quantity: Int)
    }











}