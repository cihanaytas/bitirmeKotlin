package com.example.bitirmeprojesi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.service.RetrofitInstance
import com.example.bitirmeprojesi.service.SimpleNewApi
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_password_forgot.*
import kotlinx.android.synthetic.main.fragment_password_forgot2.*
import kotlinx.android.synthetic.main.fragment_password_forgot3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PasswordForgotFragment : Fragment() {

    private lateinit var myInflater: LayoutInflater
    private lateinit var myContainer: ViewGroup
    private lateinit var placeholder: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceNew = RetrofitInstance.createInstanceNew().create(SimpleNewApi::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        myInflater = inflater
        if (container != null) {
            myContainer = container
        }
        placeholder = inflater.inflate(R.layout.fragment_password_forgot, container, false) as ViewGroup
        return placeholder

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pfB1.setOnClickListener {
            mailkontrol(pfMail.text.toString())
        }


    }

    private fun mailkontrol(mail: String){
        if(pfMail.text.toString().isNotEmpty()){
        val sorgu = serviceNew.mailControl(mail)
        sorgu.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {

            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val detail = response.body()
                    placeholder.removeAllViews()
                    placeholder.addView(myInflater.inflate(R.layout.fragment_password_forgot2, myContainer, false) as ViewGroup)
                            pfB2.setOnClickListener {
                            codeControl(pfCode.text.toString())
                           }
                        Toast.makeText(activity, "Mail Adresinize Kod Gönderildi.", Toast.LENGTH_LONG).show()
                }else{
                    val err = JsonParser().parse(response.errorBody()?.string()).asJsonObject["details"].asString
                    Toast.makeText(activity,err, Toast.LENGTH_LONG).show()
                }
            }

        })}
        else{   Toast.makeText(activity, "Mail alanı boş olamaz.", Toast.LENGTH_LONG).show()}
    }


    private fun codeControl(code: String){
        if(pfCode.text.toString().isNotEmpty()){
        val sorgu = serviceNew.codeControl(code)
        sorgu.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    placeholder.removeAllViews()
                    placeholder.addView(myInflater.inflate(R.layout.fragment_password_forgot3, myContainer, false) as ViewGroup)
                    pfB3.setOnClickListener {
                        changePassword(pfUsername.text.toString(),pfParola.text.toString())
                    }

                }else{
                    val err = JsonParser().parse(response.errorBody()?.string()).asJsonObject["details"].asString
                    Toast.makeText(activity,err, Toast.LENGTH_LONG).show()
                }
            }

        })}
        else{   Toast.makeText(activity, "Kod alanı boş olamaz.", Toast.LENGTH_LONG).show()}
    }

    private fun changePassword(mail: String, password: String){
        val body = ReqBodyLogin(mail,password)
        val sorgu = serviceNew.changePassword(body)
        sorgu.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful) {
                    Toast.makeText(activity, "Parolanız Değiştirildi", Toast.LENGTH_LONG).show()
                    val action = PasswordForgotFragmentDirections.actionPasswordForgotFragmentToGirisFragment()
                    view?.let { Navigation.findNavController(it).navigate(action) }
                }
                else
                {
                    val err = JsonParser().parse(response.errorBody()?.string()).asJsonObject["details"].asString
                    Toast.makeText(activity,err, Toast.LENGTH_LONG).show()
                }
            }

        })
    }


}