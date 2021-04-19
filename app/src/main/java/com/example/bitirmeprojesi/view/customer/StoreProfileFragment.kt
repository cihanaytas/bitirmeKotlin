package com.example.bitirmeprojesi.view.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.adapters.ProductRecyclerAdapter
import com.example.bitirmeprojesi.viewmodel.customer.CustomerUrunlerViewModel
import com.example.bitirmeprojesi.viewmodel.customer.StoreProfileViewModel
import kotlinx.android.synthetic.main.fragment_store_profile.*
import androidx.lifecycle.Observer

class StoreProfileFragment : Fragment() {
    private lateinit var viewModel : StoreProfileViewModel
    private val recyclerProductAdapter = ProductRecyclerAdapter(arrayListOf(),"storeprofile")
    private var storeName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            storeName = StoreProfileFragmentArgs.fromBundle(it).storeId
        }

        viewModel = ViewModelProviders.of(this).get(StoreProfileViewModel::class.java)
        viewModel.urunleriAl(storeName)
        viewModel.storebilgi()

        urunListRecyclerView.layoutManager = LinearLayoutManager(context)
        urunListRecyclerView.adapter = recyclerProductAdapter

        observeLiveData()

    }


    fun observeLiveData(){
        viewModel.urunler.observe(viewLifecycleOwner, Observer { urunler ->
            urunler?.let {
                urunListRecyclerView.visibility = View.VISIBLE
//                urunHataMessage.visibility = View.GONE
//                urunlerYukleniyor.visibility = View.GONE
                recyclerProductAdapter.productListesiniGuncelle(urunler)
            }
        })

        viewModel.stest.observe(viewLifecycleOwner, Observer { stest->
            stest?.let {
println("a " + stest)

            }
        })

    }


}