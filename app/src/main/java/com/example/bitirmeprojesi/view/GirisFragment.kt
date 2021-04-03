package com.example.bitirmeprojesi.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.activities.CustomerHomePage
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.StoreHomePage
import com.example.bitirmeprojesi.methods.WorkFlow
import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.service.*
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.android.synthetic.main.fragment_giris.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


lateinit var sharedPreferences: SharedPreferences
var isRemembered = false
class GirisFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("pref",android.content.Context.MODE_PRIVATE)

        isRemembered = sharedPreferences.getBoolean("CHECKBOX",false)

        if(isRemembered){
            val username = sharedPreferences.getString("USERNAME","").toString()
            val password = sharedPreferences.getString("PASSWORD","").toString()
            val user = ReqBodyLogin(username,password)
            val role = sharedPreferences.getString("ROLE","")
            role?.let { goPage(it,user) }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_giris, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonGiris.setOnClickListener { view ->
            girisYap()
        }

        buttonKayit.setOnClickListener{
            kayitOl(it)
        }
    }


    private fun girisYap(){
        val user =  ReqBodyLogin(kullaniciAdi.text.toString(),kullaniciParola.text.toString())
        val checked: Boolean = checkBoxRemember.isChecked


        val service = RetrofitInstance.createInstanceNew().create(SimpleNewApi::class.java)

        val wf = WorkFlow(service)

        GlobalScope.launch(Dispatchers.Main) {
            val sonuc = wf.getLoginControl(user)
            val role = wf.getUserRole(user.username)
            if(sonuc==true){
                if(checked) {
                    sharedPreferences.edit().putString("USERNAME", user.username).apply()
                    sharedPreferences.edit().putString("PASSWORD",user.password).apply()
                    sharedPreferences.edit().putBoolean("CHECKBOX", checked).apply()
                    sharedPreferences.edit().putString("ROLE",role).apply()
                }

                 goPage(role,user)
            }
            else{
                Toast.makeText(activity, "Kullanıcı adınız veya parolanız hatalı", Toast.LENGTH_LONG).show()
            }
        }

    }






   private fun kayitOl(view: View){
        val action = GirisFragmentDirections.actionGirisFragmentToKayitFragment()
        Navigation.findNavController(view).navigate(action)
    }


    private fun goPage(role: String,user:ReqBodyLogin){
        if(role=="CUSTOMER")
            goCustomerPage(user)
        else if(role=="STORE")
            goStorePage(user)
    }

    private fun goCustomerPage(user:ReqBodyLogin){

        val intent = Intent(getActivity(), CustomerHomePage::class.java)
        intent.putExtra("username",user.username)
        intent.putExtra("password",user.password)
        startActivity(intent)
        getActivity()?.finish()
    }

    private fun goStorePage(user:ReqBodyLogin){
        val intent = Intent(getActivity(), StoreHomePage::class.java)
        intent.putExtra("username",user.username)
        intent.putExtra("password",user.password)
        startActivity(intent)
        getActivity()?.finish()
    }


}