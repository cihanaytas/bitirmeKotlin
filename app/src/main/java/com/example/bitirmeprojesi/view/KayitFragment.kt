package com.example.bitirmeprojesi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.models.Address
import com.example.bitirmeprojesi.models.UserAccount
import com.example.bitirmeprojesi.service.RetrofitInstance
import com.example.bitirmeprojesi.service.SimpleNewApi
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_kayit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class KayitFragment : Fragment(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kayit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.planets_spinner)

        context?.let {
            ArrayAdapter.createFromResource(
                    it,
                R.array.planets_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        }



        buttonKayitOl.setOnClickListener{
            kayitOl()
        }


    }



    fun kayitOl(){
        val userAddress = Address(UserCity.text.toString(),UserDistrict.text.toString(),UserStreet.text.toString(),UserHomeNo.text.toString())
        val user = UserAccount(UserNickName.text.toString(),UserName.text.toString(),UserSurname.text.toString(),UserMail.text.toString(),UserPassword.text.toString(),userAddress,planets_spinner.selectedItem.toString())
        println(user)
        var serviceNew: SimpleNewApi = RetrofitInstance.createInstanceNew().create(SimpleNewApi::class.java)
        val sorgu  = serviceNew.addCustomer(user)
        sorgu.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                println(t.message)
                println("fail")
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful) {
                    println(response.body())
                    Toast.makeText(activity, "Kayıt Başarılı", Toast.LENGTH_LONG).show()
                }
                else
                {
                    //validation hatası gösterir
                    val err = JsonParser().parse(response.errorBody()?.string()).asJsonObject["details"].asString
                    Toast.makeText(activity,err, Toast.LENGTH_LONG).show()
                    println(err)
                }

            }

        })


    }

}