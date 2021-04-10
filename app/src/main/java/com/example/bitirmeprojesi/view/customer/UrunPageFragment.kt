package com.example.bitirmeprojesi.view.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.databinding.FragmentUrunPageBinding
import com.example.bitirmeprojesi.models.products.Product
import com.example.bitirmeprojesi.viewmodel.customer.UrunDetayiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UrunPageFragment : Fragment() {
    private var urunId : Long = 0
    private lateinit var viewModel : UrunDetayiViewModel
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
        arguments?.let {
            urunId = UrunPageFragmentArgs.fromBundle(it).urunId
        }

        viewModel = ViewModelProviders.of(this).get(UrunDetayiViewModel::class.java)
        viewModel.getData(urunId)

        observeLiveData()

    }


    fun observeLiveData(){
        viewModel.productLiveData.observe(viewLifecycleOwner, Observer {product ->
            product?.let {
                dataBinding.secilenUrun = it
            }
        })
    }

}