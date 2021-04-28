package com.example.bitirmeprojesi.view.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.adapters.CartRecyclerAdapter
import com.example.bitirmeprojesi.adapters.ProductRecyclerAdapter
import com.example.bitirmeprojesi.databinding.FragmentCartBinding
import com.example.bitirmeprojesi.databinding.FragmentGirisBinding

import com.example.bitirmeprojesi.databinding.FragmentUrunPageBinding
import com.example.bitirmeprojesi.databinding.UrunRecyclerRowBinding
import com.example.bitirmeprojesi.models.products.CartItem
import com.example.bitirmeprojesi.viewmodel.customer.CustomerUrunlerViewModel
import kotlinx.android.synthetic.main.cart_row.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_urun_ekleme.*
import kotlinx.android.synthetic.main.fragment_urunler.*

class CartFragment : Fragment() , CartRecyclerAdapter.CartInterface{

    private lateinit var viewModel : CustomerUrunlerViewModel
    private lateinit var dataBinding : FragmentCartBinding
    private val recyclerCartAdapter = CartRecyclerAdapter(arrayListOf(),this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =  ViewModelProvider(requireActivity()).get(CustomerUrunlerViewModel::class.java)

        cartRecyclerView.layoutManager = LinearLayoutManager(context)
        cartRecyclerView.adapter = recyclerCartAdapter



        observeLiveData()
    }



    fun observeLiveData(){
        viewModel.getCart().observe(viewLifecycleOwner, Observer<List<CartItem>>(){
            recyclerCartAdapter.productListesiniGuncelle(it)
        })

        viewModel.getTotalPrice().observe(viewLifecycleOwner, Observer {
            orderTotalTextView.text = it.toString() + "₺"
        })
    }

    override fun deleteItem(cartItem: CartItem?) {
        viewModel.removeItemFromCart(cartItem)
    }

    override fun changeQuantity(cartItem: CartItem?, quantity: Int) {
        viewModel.changeQuantity(cartItem,quantity)
    }

}