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
import com.example.bitirmeprojesi.models.products.*
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

        goneAllConstarint()

        planets_spinner_urun?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if( planets_spinner_urun.selectedItem.toString()=="LAPTOP")
                {
                    goneAllConstarint()
                    laptopconst.visibility = View.VISIBLE
                    buttonLaptopEkle.setOnClickListener{
                        urunEkleLaptop(it)
                    }
                }
                else if(planets_spinner_urun.selectedItem.toString()=="TABLET")
                {
                    goneAllConstarint()
                    tabletconst.visibility = View.VISIBLE
                    buttonTabletEkle.setOnClickListener{
                        urunEkleTablet(it)
                    }
                }
                else if(planets_spinner_urun.selectedItem.toString()=="TELEFON")
                {
                    goneAllConstarint()
                    telefonconst.visibility = View.VISIBLE
                        buttonTVEkle.setOnClickListener{
                        urunEkleTelefon(it)
                    }

                }

                else if(planets_spinner_urun.selectedItem.toString()=="TELEVIZYON"){
                    goneAllConstarint()
                    tvconst.visibility = View.VISIBLE
                    buttonTVEkle.setOnClickListener {
                        urunEkleTV(it)
                    }
                }

                else if(planets_spinner_urun.selectedItem.toString()=="KULAKLIK"){
                    goneAllConstarint()
                    kulaklikconst.visibility = View.VISIBLE
                    buttonKulaklikEkle.setOnClickListener {
                        urunEkleKulaklik(it)
                    }
                }



            }


        }




    }



private fun goneAllConstarint(){
    laptopconst.visibility = View.GONE
    tabletconst.visibility = View.GONE
    telefonconst.visibility = View.GONE
    tvconst.visibility = View.GONE
    kulaklikconst.visibility = View.GONE
}



    private fun urunEkleLaptop(view: View){
        val urun = Product(planets_spinner_urun.selectedItem.toString(), EPrice.text.toString()
                , EUnit.text.toString(), EFeatures.text.toString())

        val laptop = Laptop(urun, laptopHafiza.text.toString(),laptopRam.text.toString(),laptopBrand.text.toString(),laptopColor.text.toString(),
        laptopModel.text.toString(),laptopislemcimodel.text.toString(),laptopislemciType.text.toString())

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


    private fun urunEkleTelefon(view: View){
        val urun = Product(planets_spinner_urun.selectedItem.toString(), EPrice.text.toString()
                , EUnit.text.toString(), EFeatures.text.toString())

        val telefon = Phone(urun,telefonBrand.text.toString(),telefonModel.text.toString(),telefonRam.text.toString(),telefonCam.text.toString(),
        telefonHafiza.text.toString(),telefonColor.text.toString(),telefonTip.text.toString())

        val sorgu = serviceStore.telefonekle(telefon)
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


    private fun urunEkleTablet(view: View){
        val urun = Product(planets_spinner_urun.selectedItem.toString(), EPrice.text.toString()
                , EUnit.text.toString(), EFeatures.text.toString())

        val tablet = Tablet(urun,tabletBrand.text.toString(),tabletModel.text.toString(),tabletRam.text.toString(),tabletCam.text.toString(),
                tabletHafiza.text.toString(),tabletColor.text.toString(),tabletTip.text.toString())

        val sorgu = serviceStore.tabletekle(tablet)
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


    private fun urunEkleTV(view: View){
        val urun = Product(planets_spinner_urun.selectedItem.toString(), EPrice.text.toString()
                , EUnit.text.toString(), EFeatures.text.toString())

        val tv = Television(urun,tvBrand.text.toString(),tvModel.text.toString(),tvInc.text.toString(),tvColor.text.toString())

        val sorgu = serviceStore.tvekle(tv)
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


    private fun urunEkleKulaklik(view: View){
        val urun = Product(planets_spinner_urun.selectedItem.toString(), EPrice.text.toString()
                , EUnit.text.toString(), EFeatures.text.toString())

        val kulaklik = HeadPhone(urun,hpTip.text.toString(),hpColor.text.toString())

       val sorgu = serviceStore.kulaklikekle(kulaklik)

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