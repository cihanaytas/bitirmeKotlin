package com.example.bitirmeprojesi.view.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.serviceStore
import com.example.bitirmeprojesi.models.Laptop
import com.example.bitirmeprojesi.models.Product
import kotlinx.android.synthetic.main.fragment_kayit.*
import kotlinx.android.synthetic.main.fragment_urun_ekleme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UrunEklemeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_urun_ekleme, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.planets_spinner_urun)
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.planets_array_urun,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }

        testconst1.visibility = View.GONE
        testconst2.visibility = View.GONE
        testconst3.visibility = View.GONE

        planets_spinner_urun?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if( planets_spinner_urun.selectedItem.toString()=="LAPTOP")
                {          testconst1.visibility = View.VISIBLE
                    testconst2.visibility = View.GONE
                    testconst3.visibility = View.GONE
                    buttonUrunEkleDb.setOnClickListener{
                        urunEkleLaptop(it)
                    }
                }
                else if(planets_spinner_urun.selectedItem.toString()=="TABLET")
                {  testconst2.visibility = View.VISIBLE
                    testconst1.visibility = View.GONE
                    testconst3.visibility = View.GONE
                    buttonUrunEkleDb2.setOnClickListener{
                        urunEkle(it)
                    }
                }
                else if(planets_spinner_urun.selectedItem.toString()=="TELEFON")
                {  testconst3.visibility = View.VISIBLE
                    testconst1.visibility = View.GONE
                    testconst2.visibility = View.GONE
                    buttonUrunEkleDb3.setOnClickListener{
                        urunEkle(it)
                    }

                }





            }


        }




    }




    private fun urunEkle(view: View){
        val urun = Product(planets_spinner_urun.selectedItem.toString(),EPrice.text.toString()
        ,EUnit.text.toString(),EFeatures.text.toString())
        val sorgu = serviceStore.urunekle(urun)


        sorgu.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                println()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
               if(response.isSuccessful){
                   Toast.makeText(activity, "Eklendi", Toast.LENGTH_LONG).show()
                    val action = UrunEklemeFragmentDirections.actionUrunEklemeFragmentToStoreHomeFragment()
                   Navigation.findNavController(view).navigate(action)
               }
            }

        })

    }


    private fun urunEkleLaptop(view: View){
        val laptop = Laptop(planets_spinner_urun.selectedItem.toString(),EPrice.text.toString(),""
                ,EUnit.text.toString(),EFeatures.text.toString(),"256","6","ttbrand","SIYAH","TTMODEL","AMD","RYZEN7")

        val sorgu = serviceStore.laptopekle(laptop)

        sorgu.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
             }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    Toast.makeText(activity, "Eklendi", Toast.LENGTH_LONG).show()
                    val action = UrunEklemeFragmentDirections.actionUrunEklemeFragmentToStoreHomeFragment()
                    Navigation.findNavController(view).navigate(action)
                }
            }

        })


    }


}