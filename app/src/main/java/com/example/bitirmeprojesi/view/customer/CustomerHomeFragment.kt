//package com.example.bitirmeprojesi.view.customer
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.*
//import androidx.fragment.app.Fragment
//import androidx.activity.addCallback
//import androidx.navigation.Navigation
//import com.example.bitirmeprojesi.R
//import com.example.bitirmeprojesi.activities.MainActivity
//import com.example.bitirmeprojesi.activities.serviceCustomer
//import com.example.bitirmeprojesi.methods.CustomerWorkFlow
//import com.example.bitirmeprojesi.view.sharedPreferences
//import com.example.bitirmeprojesi.view.store.StoreHomeFragmentDirections
//import kotlinx.android.synthetic.main.activity_customer_home_page.*
//import kotlinx.android.synthetic.main.fragment_customer_home.*
//import kotlinx.android.synthetic.main.fragment_store_home.*
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
//class CustomerHomeFragment : Fragment() {
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_customer_home, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setHasOptionsMenu(true)
//
//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
//            requireActivity().finish()
//        }
//
//
//        buttonUrunler.setOnClickListener {
//            urunler(it)
//        }
//
//        buttonAlisverislerim.setOnClickListener {
//            alisverisler(it)
//        }
//
//
//
//    }
//
//
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_customer_home, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//
//
//
//    private fun urunler(view: View){
//        val action = CustomerHomeFragmentDirections.actionCustomerHomeFragmentToUrunlerFragment(0)
//        Navigation.findNavController(view).navigate(action)
//    }
//
//    private fun alisverisler(view: View){
//        val action = CustomerHomeFragmentDirections.actionCustomerHomeFragmentToAlisverislerFragment()
//        Navigation.findNavController(view).navigate(action)
//    }
//
//
//}