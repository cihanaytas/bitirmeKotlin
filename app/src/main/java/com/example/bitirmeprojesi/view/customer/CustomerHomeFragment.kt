package com.example.bitirmeprojesi.view.customer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.MainActivity
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.methods.CustomerWorkFlow
import com.example.bitirmeprojesi.view.sharedPreferences
import com.example.bitirmeprojesi.view.store.StoreHomeFragmentDirections
import kotlinx.android.synthetic.main.activity_customer_home_page.*
import kotlinx.android.synthetic.main.fragment_customer_home.*
import kotlinx.android.synthetic.main.fragment_store_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CustomerHomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }

        buttonCikisCustomer.setOnClickListener {view ->
            cikisYap()
        }

        buttonUrunler.setOnClickListener {
            urunler(it)
        }


    }





    private fun cikisYap() {
        sharedPreferences.edit().remove("USERNAME").apply()
        sharedPreferences.edit().remove("CHECKBOX").apply()
        sharedPreferences.edit().remove("ROLE").apply()

        val intent = Intent(getActivity(), MainActivity::class.java)
        startActivity(intent)
        getActivity()?.finish()
    }


    private fun urunler(view: View){
        val action = CustomerHomeFragmentDirections.actionCustomerHomeFragmentToUrunlerFragment(0)
        Navigation.findNavController(view).navigate(action)
    }


}