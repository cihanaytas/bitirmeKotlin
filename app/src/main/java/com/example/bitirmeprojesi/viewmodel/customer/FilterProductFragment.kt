package com.example.bitirmeprojesi.viewmodel.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import kotlinx.android.synthetic.main.fragment_filter_product.*


class FilterProductFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonfilter.setOnClickListener {
            requireFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss()

            val list = arrayListOf<String>()
            list.add("telefon")
            list.add("laptop")
            var qwe: Array<String> = list.toTypedArray()
            val action = FilterProductFragmentDirections.actionFilterProductFragmentToUrunlerFragment(0,qwe)
            Navigation.findNavController(it).navigate(action)
        }
    }



}