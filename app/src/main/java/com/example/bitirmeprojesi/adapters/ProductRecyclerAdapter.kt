package com.example.bitirmeprojesi.adapters

import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.UrunRecyclerRowBinding
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.view.customer.*
import com.example.bitirmeprojesi.view.store.StoreUrunlerimFragment
import com.example.bitirmeprojesi.view.store.StoreUrunlerimFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.urun_recycler_row.*
import kotlinx.android.synthetic.main.urun_recycler_row.view.*
import java.io.File


class ProductRecyclerAdapter(val productListesi : ArrayList<Product>,fragment:Fragment,shopInterface: ShopInterface?) : RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>(),UrunClickListener{

    interface ShopInterface {
        fun addItem(product: Product)
    }
    val xx = fragment
  //  val x = nereden
    var page = 0

    var shopInterface: ShopInterface? = shopInterface

    class ProductViewHolder(var view : UrunRecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
       // val view = inflater.inflate(R.layout.urun_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<UrunRecyclerRowBinding>(inflater, R.layout.urun_recycler_row,parent,false)
       view.shopInterface = shopInterface

        return ProductViewHolder(view)
    }



    override fun getItemCount(): Int {
        return productListesi.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.view.product = productListesi[position]
        holder.view.listener = this

//        if(x=="storemyprofile"){
//            holder.view.addToCartButton.visibility = View.GONE
//        }

        if(xx==StoreProfileFragment::class.java){
            holder.view.addToCartButton.visibility = View.GONE
        }


        val storageDirectory =   Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES);

        if(productListesi.get(position).images.isNotEmpty()){
            val img = productListesi.get(position).images[0]
            val file = File(storageDirectory, "${img}.jpg")
            Picasso.get().load(file)
                    .into(holder.itemView.imageView)
        }


    }

    fun productListesiniGuncelle(yeniProductListesi: List<Product>, pageCount: Int){
        page = pageCount
        productListesi.clear()
        productListesi.addAll(yeniProductListesi)
        notifyDataSetChanged()
    }

    override fun urunTiklandi(view: View) {
        val id = view.urun_id.text.toString().toLong()

        id?.let {
            if(xx::class.java==StoreProfileFragment::class.java){
                val action = StoreProfileFragmentDirections.actionStoreProfileFragmentToUrunPageFragment(it,page,"urunler")
                Navigation.findNavController(view).navigate(action)
            }

            else if(xx::class.java==UrunlerFragment::class.java){
                val action = UrunlerFragmentDirections.actionUrunlerFragmentToUrunPageFragment(it,page,"urunler")
                Navigation.findNavController(view).navigate(action)
            }

            else if(xx::class.java==StoreUrunlerimFragment::class.java){
                val action = StoreUrunlerimFragmentDirections.actionStoreUrunlerimFragmentToUrunEklemeFragment(id.toString())
                Navigation.findNavController(view).navigate(action)
            }

            else if(xx::class.java==UrunPageFragment::class.java){
                val action = UrunPageFragmentDirections.actionUrunPageFragmentSelf(it,0,"urunler")
                Navigation.findNavController(view).navigate(action)
            }

        }

    }


}