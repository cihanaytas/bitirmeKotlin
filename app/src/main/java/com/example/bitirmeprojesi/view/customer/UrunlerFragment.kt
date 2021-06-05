package com.example.bitirmeprojesi.view.customer

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.adapters.ProductRecyclerAdapter
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.viewmodel.customer.CustomerUrunlerViewModel
import com.example.bitirmeprojesi.viewmodel.customer.FilterProductFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_customer_home.*
import kotlinx.android.synthetic.main.fragment_urunler.*


class UrunlerFragment : Fragment() , SearchView.OnQueryTextListener,ProductRecyclerAdapter.ShopInterface{
    var pageCount = 0
    var category = ""
    var selectedCategoryList: Boolean = false
    private lateinit var viewModel : CustomerUrunlerViewModel
    private val recyclerProductAdapter = ProductRecyclerAdapter(arrayListOf(),this,this)
    private lateinit var myInflater: LayoutInflater
    private lateinit var myContainer: ViewGroup
    private lateinit var placeholder: ViewGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_urunler, container, false)
        myInflater = inflater
        if (container != null) {
            myContainer = container
        }
        placeholder = inflater.inflate(R.layout.fragment_urunler, container, false) as ViewGroup
        return placeholder
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
//            val action = UrunlerFragmentDirections.actionUrunlerFragmentToCustomerHomeFragment()
//            Navigation.findNavController(view).navigate(action)
//        }


        arguments?.let {
            pageCount = UrunlerFragmentArgs.fromBundle(it).page
            if(UrunlerFragmentArgs.fromBundle(it).selectedCategoryList!=null){
                selectedCategoryList = UrunlerFragmentArgs.fromBundle(it).selectedCategoryList!!
            }

        }
        //viewModel = ViewModelProviders.of(this).get(CustomerUrunlerViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(CustomerUrunlerViewModel::class.java)
        viewModel.getFavouriteList()
        observeLiveData()
        observeLiveDataFavourite()

        urunListRecyclerView.layoutManager = LinearLayoutManager(context)
        urunListRecyclerView.adapter = recyclerProductAdapter



        if(selectedCategoryList==false){
            viewModel.urunleriAl(pageCount)
        }


        if(pageCount==0){
            buttonGeri.visibility = View.GONE
        }




        swipeRefreshLayoutStore.setOnRefreshListener {
            urunlerYukleniyor.visibility = View.VISIBLE
            urunHataMessage.visibility = View.GONE
            urunListRecyclerView.visibility = View.GONE
            viewModel.urunleriAl(pageCount)
            swipeRefreshLayoutStore.isRefreshing = false
        }

        tablayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab : TabLayout.Tab) {
                when (tab.getPosition()) {
                    0->   {viewModel.urunleriAl(0)
                      //  observeLiveData()
                         }
                    1->   {viewModel.getFavouriteProductsList(0)

                    }
                    2-> {
//                        placeholder.removeAllViews()
//                        placeholder.addView(myInflater.inflate(R.layout.filter_products, myContainer, false) as ViewGroup)
                        val action = UrunlerFragmentDirections.actionUrunlerFragmentToFilterProductFragment()
                        Navigation.findNavController(view).navigate(action)
                    }

                else -> { // Note the block
                   // print("x is neither 1 nor 2")
                }}



            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
        })

    }



    private fun observeLiveData(){
        viewModel.urunler.observe(viewLifecycleOwner, Observer { urunler ->
            urunler?.let {
                if(urunler.isEmpty()){
                    pageCount--
                    if(pageCount<0)
                        pageCount=0
                    recyclerProductAdapter.page=pageCount
                }
                else {
                    urunListRecyclerView.visibility = View.VISIBLE
                    urunHataMessage.visibility = View.GONE
                    favoriyokmessage.visibility=View.GONE
                    urunlerYukleniyor.visibility = View.GONE
                    recyclerProductAdapter.productListesiniGuncelle(urunler,pageCount)
                }
            }
        })
        buttonIleri.setOnClickListener {
            pageCount++
            buttonGeri.visibility = View.VISIBLE
            recyclerProductAdapter.page=pageCount
            if(selectedCategoryList==true)

                else
            viewModel.urunleriAl(pageCount,category)
        }

        buttonGeri.setOnClickListener {
            pageCount--
            if(pageCount==0){
                buttonGeri.visibility = View.GONE
            }
            recyclerProductAdapter.page=pageCount
            viewModel.urunleriAl(pageCount,category)
        }
    }

    private fun observeLiveDataFavourite(){
        pageCount = 0
        viewModel.favUrunler.observe(viewLifecycleOwner, Observer { favUrunler ->
            favUrunler?.let {
                if(favUrunler.isEmpty()){
                    pageCount--
                    if(pageCount<0)
                        pageCount=0
                  //  recyclerProductAdapter.page=pageCount
                    urunListRecyclerView.visibility = View.GONE
                favoriyokmessage.visibility = View.VISIBLE
                }
                else {
                    favoriyokmessage.visibility = View.GONE
                    recyclerProductAdapter.productListesiniGuncelle(favUrunler,pageCount)
                    buttonIleri.setOnClickListener {
                        pageCount++
                        buttonGeri.visibility = View.VISIBLE
                        recyclerProductAdapter.page=pageCount
                        viewModel.getFavouriteProductsList(pageCount)
                    }

                    buttonGeri.setOnClickListener {
                        pageCount--
                        if(pageCount==0){
                            buttonGeri.visibility = View.GONE
                        }
                        recyclerProductAdapter.page=pageCount
                        viewModel.getFavouriteProductsList(pageCount)
                    }

                }
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        inflater.inflate(R.menu.menu_cart,menu)
        super.onCreateOptionsMenu(menu, inflater)


        val search = menu?.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        val menuItem = menu.findItem(R.id.cart)
        val actionView = menuItem.actionView

        val cbtv = actionView.findViewById<TextView>(R.id.cart_badge_text_view)

        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }


        viewModel.getCart().observe(viewLifecycleOwner, Observer{ list->
            cbtv.text = list.size.toString()
            if(list.size==0)
                cbtv.visibility = View.GONE
            else
                cbtv.visibility = View.VISIBLE
        })

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null){
            category=query
            viewModel.urunleriAl(0,category)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query!=null){
            category=query
            viewModel.urunleriAl(0,category)
          //  observeLiveData()
        }
        if(query?.length==0){
            category=""
            viewModel.urunleriAl(0)
           // observeLiveData()
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.cart){
            val action = UrunlerFragmentDirections.actionUrunlerFragmentToCartFragment2()
            view?.let {
                Navigation.findNavController(it).navigate(action) }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun addItem(product: Product) {
        viewModel.cartUrunEkle(product)
    }




}