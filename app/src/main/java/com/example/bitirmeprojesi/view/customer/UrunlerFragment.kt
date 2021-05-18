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
import com.example.bitirmeprojesi.adapters.ProductRecyclerAdapter
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.viewmodel.customer.CustomerUrunlerViewModel
import kotlinx.android.synthetic.main.fragment_urunler.*


class UrunlerFragment : Fragment() , SearchView.OnQueryTextListener,ProductRecyclerAdapter.ShopInterface{
    var pageCount = 0
    var category = ""

    private lateinit var viewModel : CustomerUrunlerViewModel
    private val recyclerProductAdapter = ProductRecyclerAdapter(arrayListOf(),"urunler",this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        setHasOptionsMenu(true)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = UrunlerFragmentDirections.actionUrunlerFragmentToCustomerHomeFragment()
            Navigation.findNavController(view).navigate(action)
        }
        arguments?.let {
            pageCount = UrunlerFragmentArgs.fromBundle(it).page
        }
        //viewModel = ViewModelProviders.of(this).get(CustomerUrunlerViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(CustomerUrunlerViewModel::class.java)
        viewModel.urunleriAl(pageCount,category)
        viewModel.getFavouriteList()

        urunListRecyclerView.layoutManager = LinearLayoutManager(context)
        urunListRecyclerView.adapter = recyclerProductAdapter

        if(pageCount==0){
            buttonGeri.visibility = View.GONE
        }

        buttonIleri.setOnClickListener {
            pageCount++
            buttonGeri.visibility = View.VISIBLE
            recyclerProductAdapter.page=pageCount
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



        swipeRefreshLayoutStore.setOnRefreshListener {
            urunlerYukleniyor.visibility = View.VISIBLE
            urunHataMessage.visibility = View.GONE
            urunListRecyclerView.visibility = View.GONE
            viewModel.urunleriAl(pageCount,category)
            swipeRefreshLayoutStore.isRefreshing = false
        }


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
                    urunListRecyclerView.visibility = View.VISIBLE
                    urunHataMessage.visibility = View.GONE
                    urunlerYukleniyor.visibility = View.GONE
                    recyclerProductAdapter.productListesiniGuncelle(urunler,pageCount)
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
        }
        if(query?.length==0){
            category=""
            viewModel.urunleriAl(0,query)
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val action = UrunlerFragmentDirections.actionUrunlerFragmentToCartFragment2()
        view?.let {
            Navigation.findNavController(it).navigate(action) }
        return super.onOptionsItemSelected(item)
    }

    override fun addItem(product: Product) {
        viewModel.cartUrunEkle(product)
    }




}