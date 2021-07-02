package com.example.bitirmeprojesi.view.store

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.adapters.ProductRecyclerAdapter
import com.example.bitirmeprojesi.models.NotificationProduct
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.view.customer.UrunlerFragmentDirections
import com.example.bitirmeprojesi.viewmodel.customer.StoreProfileViewModel
import com.example.bitirmeprojesi.viewmodel.store.StoreViewModel
import kotlinx.android.synthetic.main.fragment_store_notifications.*
import kotlinx.android.synthetic.main.fragment_store_urunlerim.*
import kotlinx.android.synthetic.main.fragment_urunler.*
import kotlinx.android.synthetic.main.urun_recycler_row.*


class StoreUrunlerimFragment : Fragment() ,ProductRecyclerAdapter.ShopInterface{
    var pageCount = 0
    private lateinit var viewModel : StoreProfileViewModel
    private lateinit var viewModel2 : StoreViewModel
    private val recyclerProductAdapter = ProductRecyclerAdapter(arrayListOf(),this,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_store_urunlerim, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(StoreProfileViewModel::class.java)
        viewModel2 = ViewModelProvider(requireActivity()).get(StoreViewModel::class.java)
        viewModel.urunlerimlAl(pageCount)
        viewModel2.getNotifications()

        if(pageCount==0){
            buttonGeriStore.visibility = View.GONE
        }

        buttonIleriStore.setOnClickListener {
            pageCount++
            buttonGeriStore.visibility = View.VISIBLE
            recyclerProductAdapter.page=pageCount
            viewModel.urunlerimlAl(pageCount)
        }

        buttonGeriStore.setOnClickListener {
            pageCount--
            if(pageCount==0){
                buttonGeriStore.visibility = View.GONE
            }
            recyclerProductAdapter.page=pageCount
            viewModel.urunlerimlAl(pageCount)
        }

        urunListRecyclerViewStore.layoutManager = LinearLayoutManager(context)
        urunListRecyclerViewStore.adapter = recyclerProductAdapter

//        swipeRefreshLayoutStore.setOnRefreshListener {
//            urunlerYukleniyor.visibility = View.VISIBLE
//            urunHataMessage.visibility = View.GONE
//            urunListRecyclerView.visibility = View.GONE
//            viewModel.urunlerimlAl(pageCount)
//            swipeRefreshLayoutStore.isRefreshing = false
//        }


        observeLiveData()

    }


    fun observeLiveData(){
        viewModel.urunler.observe(viewLifecycleOwner, Observer { urunler ->
            urunler?.let {
                if(urunler.isEmpty()){
                    pageCount--
                    recyclerProductAdapter.page=pageCount
                }
                else {
                    urunListRecyclerViewStore.visibility = View.VISIBLE
                    urunHataMessageStore.visibility = View.GONE
                    urunlerYukleniyorStore.visibility = View.GONE
                    recyclerProductAdapter.productListesiniGuncelle(urunler,0)
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        super.onCreateOptionsMenu(menu, inflater)


        val menuItem = menu.findItem(R.id.notification)
        val actionView = menuItem.actionView

        val cbtv = actionView.findViewById<TextView>(R.id.cart_badge_text_view)


        viewModel2.notifications.observe(viewLifecycleOwner, Observer { notificationList ->
            notificationList?.let {
                cbtv.text = notificationList.size.toString()
                if(notificationList.size==0)
                    cbtv.visibility = View.GONE
                else
                    cbtv.visibility = View.VISIBLE
            }
        })
    }




    override fun addItem(product: Product) {
    }

}