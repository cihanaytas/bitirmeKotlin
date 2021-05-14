package com.example.bitirmeprojesi.view.store

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.serviceStore
import com.example.bitirmeprojesi.models.products.*
import com.example.bitirmeprojesi.viewmodel.customer.UrunDetayiViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_urun_ekleme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class UrunEklemeFragment : Fragment() {
    var secilenBitmap : Bitmap? = null
    private var images: ArrayList<Uri?>? = null
    private var imagesUriList: MutableList<String> = mutableListOf()
    private var positionImage = 0
    private val PICK_IMAGES_CODE = 0
    private var urunId : String = ""
    private lateinit var viewModel : UrunDetayiViewModel

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

//        viewModel = ViewModelProviders.of(this).get(UrunDetayiViewModel::class.java)

        arguments?.let {
            urunId = UrunEklemeFragmentArgs.fromBundle(it).productId
        }

        if(urunId.isNotEmpty()){
            UrunMarka.text = "marka".toEditable()
            viewModel.getDataStore(urunId.toLong())
            observeLiveData()
        }

        anaconst.setOnClickListener {
            closeKeyboard(it)
        }


        images = ArrayList()
      //  imageSwitcher.setFactory { ImageView(activity?.applicationContext) }

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
                        if(buttonUrunuEkle.text == "Ekle"){
                            urunEkle(it)}
                        else{
                            urunGuncelle(it,urunId)}
                    }
            }
        }
    }

    fun observeLiveData(){
        viewModel.productLiveData2.observe(viewLifecycleOwner, androidx.lifecycle.Observer {product->
            UrunFiyat.text = product.price.toEditable()
            UrunAdet.text = product.units.toEditable()
            UrunMarka.text = product.brand.toEditable()
            UrunModel.text = product.model.toEditable()
            UrunBilgi.text = product.features.toEditable()
            buttonUrunuEkle.text = "Güncelle"
            val ap =  planets_spinner_urun.adapter
            for(i in 0..ap.count-1){
                if(product.category.toString()==ap.getItem(i).toString())
                    planets_spinner_urun.setSelection(i)
            }
           gorselEkle(product)
        })


    }


    private fun gorselEkle(product: Product){
        var positionImage = 0
        val img = product.images

        for(i in img){
            imagesUriList.add(i)
        }

        picasso("${imagesUriList[0]}.jpg")

        nexBtn.setOnClickListener {
            if(positionImage < imagesUriList!!.size-1){
                positionImage++
                picasso("${imagesUriList[positionImage]}.jpg")


            }else{
                Toast.makeText(activity,"Fotoğraf yok", Toast.LENGTH_SHORT).show()
            }
        }

        previousBtn.setOnClickListener {
            if(positionImage>0){
                positionImage--
                picasso("${imagesUriList[positionImage]}.jpg")

            }else{
                Toast.makeText(activity,"Fotoğraf yok", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun picasso(uri: String){
        val storageDirectory =   Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        val file = File(storageDirectory, uri)
        Picasso.get().load(file)
                .into(imageSwitcher)
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

               if(images!=null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(ContextCompat.checkSelfPermission(requireActivity().applicationContext,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),100)
                }
                else{  addimage()}
            }else{
                addimage()
            }

        }


       val urun = imagesUriList?.let {
           Product(UrunFiyat.text.toString(),UrunMarka.text.toString(),UrunModel.text.toString(),
                   planets_spinner_urun.selectedItem.toString(),UrunBilgi.text.toString(),UrunAdet.text.toString(), it)
       }

       val sorgu = urun?.let { serviceStore.urunEkle(it) }
       println("geldim")
       sorgu?.enqueue(object : Callback<String>{
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



    private fun urunGuncelle(view: View, urunId: String){
        val urun = imagesUriList?.let {
            println(imagesUriList)
            Product(UrunFiyat.text.toString(),UrunMarka.text.toString(),UrunModel.text.toString(),
                    planets_spinner_urun.selectedItem.toString(),UrunBilgi.text.toString(),UrunAdet.text.toString(), it)
        }

//        val sorgu = urun?.let { serviceStore.urunGuncelle(urunId.toLong(),urun) }
//
//        sorgu?.enqueue(object : Callback<String>{
//            override fun onFailure(call: Call<String>, t: Throwable) {
//            }
//
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if(response.isSuccessful){
//                    Toast.makeText(activity, "Güncellendi", Toast.LENGTH_LONG).show()
//                    val action = UrunEklemeFragmentDirections.actionUrunEklemeFragmentToStoreHomeFragment()
//                    Navigation.findNavController(view).navigate(action)
//                }
//            }
//
//        })


    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==100){
            if(grantResults.size>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            addimage()
            }
        }
    }


    private fun addimage(){
        for(i in images!!){
            if(Build.VERSION.SDK_INT >= 28) {

                val source = ImageDecoder.createSource(requireActivity().contentResolver, i!!)
                secilenBitmap = ImageDecoder.decodeBitmap(source)
                val externalStorageState = Environment.getExternalStorageState()
                if(externalStorageState.equals(Environment.MEDIA_MOUNTED)){
               //     val resim = File("/sdcard/Resimler")

                    val storageDirectory =   Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                    val uri = UUID.randomUUID()
                    val file = File(storageDirectory, "${uri}.jpg")

                    try{
                        val stream: OutputStream = FileOutputStream(file)
                        secilenBitmap!!.compress(Bitmap.CompressFormat.JPEG,100,stream)
                        stream.flush()
                        stream.close()
                        imagesUriList!!.add(uri.toString())
                    }catch (e:Exception){
                        println(e.message)
                    }
                }

            }

        }
    }

    private fun  String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private fun closeKeyboard(view: View){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}

