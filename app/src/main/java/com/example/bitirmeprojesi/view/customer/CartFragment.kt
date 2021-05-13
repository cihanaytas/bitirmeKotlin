package com.example.bitirmeprojesi.view.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.adapters.CartRecyclerAdapter
import com.example.bitirmeprojesi.databinding.FragmentCartBinding

import com.example.bitirmeprojesi.models.products.CartItem
import com.example.bitirmeprojesi.models.products.CartItemDto
import com.example.bitirmeprojesi.viewmodel.customer.CustomerUrunlerViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : Fragment() , CartRecyclerAdapter.CartInterface{

    private lateinit var viewModel : CustomerUrunlerViewModel
    private lateinit var dataBinding : FragmentCartBinding
    private val recyclerCartAdapter = CartRecyclerAdapter(arrayListOf(),this)
    private lateinit var cartList : List<CartItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =  ViewModelProvider(requireActivity()).get(CustomerUrunlerViewModel::class.java)

        cartRecyclerView.layoutManager = LinearLayoutManager(context)
        cartRecyclerView.adapter = recyclerCartAdapter
        placeOrderButton.setOnClickListener {
            var cartItemDtoList = arrayListOf<CartItemDto>()
            for(i in cartList){
                val cartItemDto = CartItemDto(i.product.id,i.product.price.toDouble(),i.adet,i.product.category)
                cartItemDtoList.add(cartItemDto)
            }

            alisverisTamamla(cartItemDtoList,it)


        }
        observeLiveData()
    }



    fun observeLiveData(){
        viewModel.getCart().observe(viewLifecycleOwner, Observer<List<CartItem>>(){
            cartList = it
            recyclerCartAdapter.productListesiniGuncelle(it)
        })

        viewModel.getTotalPrice().observe(viewLifecycleOwner, Observer {
            orderTotalTextView.text = it.toString() + "₺"
        })
    }

    override fun deleteItem(cartItem: CartItem?) {
        viewModel.removeItemFromCart(cartItem)
    }

    override fun changeQuantity(cartItem: CartItem?, quantity: Int) {
        viewModel.changeQuantity(cartItem,quantity)
    }

    private fun alisverisTamamla(cartItemDto: List<CartItemDto>,view: View){
        val sorgu = serviceCustomer.sales(cartItemDto)
        sorgu.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                println("fail")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    viewModel.resetCart()
                    Toast.makeText(activity, "Satın Alma Başarılı", Toast.LENGTH_LONG).show()
                    val action = CartFragmentDirections.actionCartFragment2ToUrunlerFragment(0)
                    Navigation.findNavController(view).navigate(action)
                }else{
                    println("hata")
                }
            }

        })

    }

}