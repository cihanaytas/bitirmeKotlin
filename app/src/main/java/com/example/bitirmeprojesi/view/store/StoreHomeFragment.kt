package com.example.bitirmeprojesi.view.store

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.MainActivity
import com.example.bitirmeprojesi.view.GirisFragmentDirections
import com.example.bitirmeprojesi.view.sharedPreferences
import kotlinx.android.synthetic.main.fragment_store_home.*

class StoreHomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCikisStore.setOnClickListener { view ->
            cikisYap()
        }

        buttonUrunEkle.setOnClickListener {
            urunEkle(it)
        }

        buttonUrunlerim.setOnClickListener {
            val action = StoreHomeFragmentDirections.actionStoreHomeFragmentToStoreUrunlerimFragment(0)
            Navigation.findNavController(it).navigate(action)
        }
    }


    private fun cikisYap(){
        sharedPreferences.edit().remove("USERNAME").apply()
        sharedPreferences.edit().remove("CHECKBOX").apply()
        sharedPreferences.edit().remove("ROLE").apply()
        val intent = Intent(getActivity(), MainActivity::class.java)
        startActivity(intent)
        getActivity()?.finish()
    }


    private fun urunEkle(view: View){
        val action = StoreHomeFragmentDirections.actionStoreHomeFragmentToUrunEklemeFragment("")
        Navigation.findNavController(view).navigate(action)
    }

}