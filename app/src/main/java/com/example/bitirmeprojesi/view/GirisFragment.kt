package com.example.bitirmeprojesi.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.activities.CustomerHomePage
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.StoreHomePage
import com.example.bitirmeprojesi.methods.WorkFlow
import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.service.RetrofitInstance
import com.example.bitirmeprojesi.service.SimpleCustomerApi
import com.example.bitirmeprojesi.service.SimpleNewApi
import com.example.bitirmeprojesi.service.SimpleStoreApi
import kotlinx.android.synthetic.main.fragment_giris.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

lateinit var sharedPreferences: SharedPreferences
var isRemembered = false
lateinit var serviceCustomer: SimpleCustomerApi
lateinit var serviceStore: SimpleStoreApi
class GirisFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        sharedPreferences = requireActivity().getSharedPreferences("pref",android.content.Context.MODE_PRIVATE)

        isRemembered = sharedPreferences.getBoolean("CHECKBOX",false)



        if(isRemembered){
            val role = sharedPreferences.getString("ROLE","")
            role?.let { goPage(it) }
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


    fun girisYap(){

        var serviceNew: SimpleNewApi = RetrofitInstance.createInstanceNew().create(SimpleNewApi::class.java)
        val user =  ReqBodyLogin(kullaniciAdi.text.toString(),kullaniciParola.text.toString())
        val checked: Boolean = checkBoxRemember.isChecked
        val wf = WorkFlow()


        GlobalScope.launch(Dispatchers.Main) {
            val sonuc = wf.getLoginControl(user,serviceNew, sharedPreferences,checked)

            if(sonuc==true){
                sharedPreferences.edit().putString("USERNAME", user.username).apply()
                sharedPreferences.edit().putString("PASSWORD", user.password).apply()
                sharedPreferences.edit().putBoolean("CHECKBOX", checked).apply()

                val role = wf.getUserRole(user.username,serviceNew)

                sharedPreferences.edit().putString("ROLE",role).apply()

                goPage(role)

            }

        }
    }



    fun goPage(role: String){
        if(role=="CUSTOMER")
            goCustomerPage()
        else if(role=="STORE")
            goStorePage()
    }

    fun kayitOl(view: View){
        val action = GirisFragmentDirections.actionGirisFragmentToKayitFragment()
        Navigation.findNavController(view).navigate(action)
    }


    fun goCustomerPage(){
        val intent = Intent(getActivity(), CustomerHomePage::class.java)
        startActivity(intent)
        getActivity()?.finish()
    }

    fun goStorePage(){
        val intent = Intent(getActivity(), StoreHomePage::class.java)
        startActivity(intent)
        getActivity()?.finish()
    }


}