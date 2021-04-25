package com.example.bitirmeprojesi.view.customer

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.adapters.ProductRecyclerAdapter
import com.example.bitirmeprojesi.viewmodel.customer.CustomerUrunlerViewModel
import kotlinx.android.synthetic.main.fragment_urunler.*


class UrunlerFragment : Fragment() {
    var pageCount = 0
    private lateinit var viewModel : CustomerUrunlerViewModel
    private val recyclerProductAdapter = ProductRecyclerAdapter(arrayListOf(),"urunler")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = UrunlerFragmentDirections.actionUrunlerFragmentToCustomerHomeFragment()
            Navigation.findNavController(view).navigate(action)
        }

        arguments?.let {
            pageCount = UrunlerFragmentArgs.fromBundle(it).page
        }
        viewModel = ViewModelProviders.of(this).get(CustomerUrunlerViewModel::class.java)
        viewModel.urunleriAl(pageCount)

        if(pageCount==0){
            buttonGeri.visibility = View.GONE
        }

        buttonIleri.setOnClickListener {
            pageCount++
            buttonGeri.visibility = View.VISIBLE
            recyclerProductAdapter.page=pageCount
            viewModel.urunleriAl(pageCount)
        }

        buttonGeri.setOnClickListener {
            pageCount--
            if(pageCount==0){
                buttonGeri.visibility = View.GONE
            }
            recyclerProductAdapter.page=pageCount
            viewModel.urunleriAl(pageCount)
        }

        urunListRecyclerView.layoutManager = LinearLayoutManager(context)
        urunListRecyclerView.adapter = recyclerProductAdapter

        swipeRefreshLayout.setOnRefreshListener {
            urunlerYukleniyor.visibility = View.VISIBLE
            urunHataMessage.visibility = View.GONE
            urunListRecyclerView.visibility = View.GONE
            viewModel.urunleriAl(0)
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }



    fun observeLiveData(){
//        println(activity?.supportFragmentManager?.fragments?.last())
        viewModel.urunler.observe(viewLifecycleOwner, Observer { urunler ->
            urunler?.let {
                if(urunler.isEmpty()){
                    pageCount--
                    recyclerProductAdapter.page=pageCount
                }
                else {
                    urunListRecyclerView.visibility = View.VISIBLE
                    urunHataMessage.visibility = View.GONE
                    urunlerYukleniyor.visibility = View.GONE
                    recyclerProductAdapter.productListesiniGuncelle(urunler)
                }
            }
        })

    }


}