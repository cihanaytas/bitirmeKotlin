package com.example.bitirmeprojesi.view.customer

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.adapters.ProductRecyclerAdapter
import com.example.bitirmeprojesi.databinding.FragmentUrunPageBinding
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.viewmodel.customer.CustomerUrunlerViewModel
import com.example.bitirmeprojesi.viewmodel.customer.UrunDetayiViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_urun_ekleme.*
import kotlinx.android.synthetic.main.fragment_urun_page.*
import kotlinx.android.synthetic.main.urun_recycler_row.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class UrunPageFragment : Fragment(){
    private var imagesUriList: MutableList<String> = mutableListOf()
    private var urunId : Long = 0
    private var page = 0
    private lateinit var viewModel : UrunDetayiViewModel
    private lateinit var viewModel2 : CustomerUrunlerViewModel
    private lateinit var dataBinding : FragmentUrunPageBinding
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_urun_page,container,false)

        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            urunId = UrunPageFragmentArgs.fromBundle(it).urunId
            page = UrunPageFragmentArgs.fromBundle(it).page
        }
        viewModel = ViewModelProviders.of(this).get(UrunDetayiViewModel::class.java)
       //viewModel2 = ViewModelProviders.of(this).get(CustomerUrunlerViewModel::class.java)
        viewModel2 =ViewModelProvider(requireActivity()).get(CustomerUrunlerViewModel::class.java)

        dataBinding.shopViewModel = viewModel2
        viewModel.getData(urunId)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = UrunPageFragmentDirections.actionUrunPageFragmentToUrunlerFragment(page)
            Navigation.findNavController(view).navigate(action)
        }

        observeLiveData()

        buttonPuanla.setOnClickListener {
            val sorgu = serviceCustomer.pointToProduct(dataBinding.secilenUrun!!.id.toLong(),ratingBarPoint.rating.toDouble())
            sorgu.enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    println(t.message)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        println("başarılı")
                    }

                    else{
                        println("error")}
                }

            })
        }

    storenametext.setOnClickListener {
        val action = UrunPageFragmentDirections.actionUrunPageFragmentToStoreProfileFragment(dataBinding.secilenUrun!!.storeNickName)
        Navigation.findNavController(view).navigate(action)
    }




    }



    fun observeLiveData(){
        viewModel.productLiveData.observe(viewLifecycleOwner, Observer {product ->
            product?.let {
                dataBinding.secilenUrun = it
                if(it.images.isNotEmpty()) {
                    gorselEkle()
                }
            }
        })
    }

    private fun gorselEkle(){
        var positionImage = 0
        val img = dataBinding.secilenUrun!!.images

        for(i in img){
            imagesUriList.add(i)
        }

        picasso("${imagesUriList[0]}.jpg")

        ileri.setOnClickListener {
            if(positionImage < imagesUriList!!.size-1){
                positionImage++
                picasso("${imagesUriList[positionImage]}.jpg")


            }else{
                Toast.makeText(activity,"Fotoğraf yok", Toast.LENGTH_SHORT).show()
            }
        }

        geri.setOnClickListener {
            if(positionImage>0){
                positionImage--
                picasso("${imagesUriList[positionImage]}.jpg")

            }else{
                Toast.makeText(activity,"Fotoğraf yok", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun picasso(uri: String){
        val storageDirectory =   Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        val file = File(storageDirectory, uri)
        Picasso.get().load(file)
                .into(imageSwitcherProduct)
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cart,menu)
        super.onCreateOptionsMenu(menu, inflater)

        val menuItem = menu.findItem(R.id.cart)
        val actionView = menuItem.actionView

        val cbtv = actionView.findViewById<TextView>(R.id.cart_badge_text_view)

        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }


        viewModel2.getCart().observe(viewLifecycleOwner, Observer{ list->
            cbtv.text = list.size.toString()
            if(list.size==0)
                cbtv.visibility = View.GONE
            else
                cbtv.visibility = View.VISIBLE
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val action = UrunPageFragmentDirections.actionUrunPageFragmentToCartFragment2()
        view?.let {
            Navigation.findNavController(it).navigate(action) }
        return super.onOptionsItemSelected(item)
    }




}

