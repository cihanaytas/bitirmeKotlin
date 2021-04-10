package com.example.bitirmeprojesi.view.customer

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.adapters.ProductRecyclerAdapter
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.viewmodel.CustomerUrunlerViewModel
import kotlinx.android.synthetic.main.fragment_urunler.*


class UrunlerFragment : Fragment() {
    private lateinit var viewModel : CustomerUrunlerViewModel
    private val recyclerProductAdapter = ProductRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val wf = CustomerWorkFlow(serviceCustomer)
//        GlobalScope.launch(Dispatchers.Main) {
//        val a = wf.getProductList()
//            println(a)
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_urunler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CustomerUrunlerViewModel::class.java)
        viewModel.urunleriAl()

        urunListRecyclerView.layoutManager = LinearLayoutManager(context)
        urunListRecyclerView.adapter = recyclerProductAdapter

        observeLiveData()
    }



    fun observeLiveData(){
        viewModel.urunler.observe(viewLifecycleOwner, Observer { urunler ->
            urunler?.let {
                println(urunler)
                 urunListRecyclerView.visibility = View.VISIBLE
                urunHataMessage.visibility = View.GONE
                urunlerYukleniyor.visibility = View.GONE
                recyclerProductAdapter.productListesiniGuncelle(urunler)
            }
        })

    }


}