package com.example.bitirmeprojesi.view.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.adapters.CartRecyclerAdapter
import com.example.bitirmeprojesi.adapters.CartRecyclerAdapter2
import com.example.bitirmeprojesi.databinding.FragmentCartBinding
import com.example.bitirmeprojesi.models.products.CartItem
import com.example.bitirmeprojesi.viewmodel.customer.AlisverislerViewModel
import com.example.bitirmeprojesi.viewmodel.customer.CustomerUrunlerViewModel
import kotlinx.android.synthetic.main.fragment_alisveris_detay.*
import kotlinx.android.synthetic.main.fragment_cart.*


class AlisverisDetayFragment : Fragment(){

        private lateinit var viewModel : AlisverislerViewModel
        private lateinit var dataBinding : FragmentCartBinding
        private val recyclerCartAdapter = CartRecyclerAdapter2(arrayListOf())
        private lateinit var cartList : List<CartItem>
        private var shoppingId : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_alisveris_detay, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            shoppingId = AlisverisDetayFragmentArgs.fromBundle(it).shoppingId
        }

        viewModel =  ViewModelProvider(requireActivity()).get(AlisverislerViewModel::class.java)
        viewModel.getCartItemList(shoppingId)



        cartRecyclerViewA.layoutManager = LinearLayoutManager(context)
        cartRecyclerViewA.adapter = recyclerCartAdapter

        observeLiveData()
    }


    fun observeLiveData(){
        viewModel.cartUrunler.observe(viewLifecycleOwner, Observer<List<CartItem>>() {
            var totalPrice = 0.0
            cartList = it
            for(i in it){
                totalPrice+=i.adet.toDouble() * i.product.price.toDouble()
            }
            orderTotalTextViewA.text = "Toplam: " + totalPrice.toString() + "₺"
            recyclerCartAdapter.productListesiniGuncelle(it)
        })



    }




}