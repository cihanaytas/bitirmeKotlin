package com.example.bitirmeprojesi.view.store

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.serviceStore
import com.example.bitirmeprojesi.models.products.*
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_urun_ekleme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UrunEklemeFragment : Fragment() {

    private var images: ArrayList<Uri?>? = null
    private var positionImage = 0
    private val PICK_IMAGES_CODE = 0

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

        images = ArrayList()
        imageSwitcher.setFactory { ImageView(activity?.applicationContext) }

        pickImagesBtn.setOnClickListener {
            pickImagesIntent()
        }

        nexBtn.setOnClickListener {
            if(positionImage < images!!.size-1){
                positionImage++
                imageSwitcher.setImageURI(images!![positionImage])
            }else{
                Toast.makeText(activity,"Fotoğraf yok",Toast.LENGTH_SHORT).show()
            }
        }

        previousBtn.setOnClickListener {
            if(positionImage>0){
                positionImage--
                imageSwitcher.setImageURI(images!![positionImage])
            }else{
                Toast.makeText(activity,"Fotoğraf yok",Toast.LENGTH_SHORT).show()
            }
        }



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


        planets_spinner_urun?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    buttonUrunuEkle.setOnClickListener {
                        urunEkle(it)
                    }
            }
        }
    }


    private fun pickImagesIntent(){
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Görsel Seç"), PICK_IMAGES_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGES_CODE){
            if(resultCode == Activity.RESULT_OK){
                if(data!!.clipData != null){
                    val count = data.clipData!!.itemCount
                    for(i in 0 until count){
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        images!!.add(imageUri)
                    }
                    imageSwitcher.setImageURI(images!![0])
                }else{
                    val imageUri = data.data
                    imageSwitcher.setImageURI(imageUri)
                }
            }
        }
    }




   private fun urunEkle(view: View){
       val urun = Product(UrunFiyat.text.toString(),UrunMarka.text.toString(),UrunModel.text.toString(),
               planets_spinner_urun.selectedItem.toString(),UrunBilgi.text.toString(),UrunAdet.text.toString())

       val sorgu = serviceStore.urunEkle(urun)


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