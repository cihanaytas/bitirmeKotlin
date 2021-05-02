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
import com.example.bitirmeprojesi.adapters.ProductRecyclerAdapter
import com.example.bitirmeprojesi.adapters.ShopRecyclerAdapter
import com.example.bitirmeprojesi.viewmodel.customer.AlisverislerViewModel
import com.example.bitirmeprojesi.viewmodel.customer.CustomerUrunlerViewModel
import kotlinx.android.synthetic.main.fragment_alisverisler.*
import kotlinx.android.synthetic.main.fragment_urunler.*


class AlisverislerFragment : Fragment() {

    private lateinit var viewModel : AlisverislerViewModel
    private val recyclerShopAdapter = ShopRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alisverisler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(AlisverislerViewModel::class.java)
        viewModel.getShoppingList()

        alisverisListRecyclerView.layoutManager = LinearLayoutManager(context)
        alisverisListRecyclerView.adapter = recyclerShopAdapter

        observeLiveData()
    }


    fun observeLiveData(){
        viewModel.alisverisler.observe(viewLifecycleOwner, Observer { shopList ->
            shopList?.let {
                if(shopList.isEmpty()){
                    alisverisHataMessage.visibility = View.VISIBLE
                    alisverisListRecyclerView.visibility = View.GONE
                }
                else {
                    alisverisListRecyclerView.visibility = View.VISIBLE
                    alisverisHataMessage.visibility = View.GONE
                    recyclerShopAdapter.alisverisListesiniGuncelle(shopList)
                }
            }
        })

    }


}