package com.example.bitirmeprojesi.view.customer

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.activities.serviceCustomer
import com.example.bitirmeprojesi.adapters.CommentsRecyclerAdapter
import com.example.bitirmeprojesi.models.products.CommentDto
import com.example.bitirmeprojesi.models.products.Comments
import com.example.bitirmeprojesi.viewmodel.customer.UrunDetayiViewModel
import kotlinx.android.synthetic.main.fragment_comments.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentsFragment : Fragment() , CommentsRecyclerAdapter.CommentInterface{

    private var productId : Long = 0
    private var nereden : String = ""
    private lateinit var viewModel : UrunDetayiViewModel
    private var commentsRecyclerAdapter= CommentsRecyclerAdapter(arrayListOf(),this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            productId = CommentsFragmentArgs.fromBundle(it).productId
            nereden = CommentsFragmentArgs.fromBundle(it).nereden
        }

        commentConst.setOnClickListener {
            closeKeyboard(it)
        }

        if(nereden=="urunler"){
            yorumText.visibility = View.GONE
            buttonAddComment.visibility = View.GONE
        }

        viewModel = ViewModelProvider(requireActivity()).get(UrunDetayiViewModel::class.java)
        viewModel.getCommentList(productId)

        commentListRecyclerView.layoutManager = LinearLayoutManager(context)
        commentListRecyclerView.adapter = commentsRecyclerAdapter

        observeLiveData()


        buttonAddComment.setOnClickListener {
            closeKeyboard(it)
            addComment(null)
        }

    }


    fun observeLiveData(){
        viewModel.commentListLiveData.observe(viewLifecycleOwner, Observer { commentList ->
            commentList?.let {
                if(commentList.isEmpty()){
                    yorumYokMessage.visibility = View.VISIBLE
                    commentListRecyclerView.visibility = View.GONE
                }

                else{
                    yorumYokMessage.visibility = View.GONE
                    commentListRecyclerView.visibility = View.VISIBLE
                    commentsRecyclerAdapter.commentListUpdate(commentList as ArrayList<Comments>)
                }
            }
        })

    }


    private fun closeKeyboard(view: View){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun upKeyboard(view: View){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInputFromWindow(view.windowToken,InputMethodManager.SHOW_FORCED,0)
    }

    private fun addComment(commentId: Long?){
        val sorgu: Call<Boolean>
        if(commentId!=null){
            val comment = CommentDto(commentId,yorumText.text.toString(),productId)
            sorgu = serviceCustomer.updateComment(comment)
        }else{
            val comment = CommentDto(yorumText.text.toString(),productId)
            sorgu = serviceCustomer.addComment(comment)
        }



        sorgu.enqueue(object : Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                println(t.message)
                println("fail")
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Toast.makeText(activity, "Yorumunuz eklendi.", Toast.LENGTH_LONG).show()
                    yorumText.text = "".toEditable()
                    viewModel.getCommentList(productId)
                    observeLiveData()
                }else{
                    println("fail2")
                    Toast.makeText(activity, "Yorumunuz eklenemedi.", Toast.LENGTH_LONG).show()
                }
            }

        })
    }


    private fun  String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    override fun deleteComment(commentId: Long) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Yorumunuzu silmek istiyor musunuz?")
                .setCancelable(false)
                .setPositiveButton("Evet") { dialog, id ->
                    val sorgu = serviceCustomer.deleteComment(commentId)
                    sorgu.enqueue(object : Callback<String>{
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            println("fail")
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if(response.isSuccessful){
                                Toast.makeText(activity, "Yorumunuz silindi.", Toast.LENGTH_LONG).show()
                                viewModel.getCommentList(productId)
                                observeLiveData()
                            }
                        }

                    })
                }
                .setNegativeButton("Hayır") { dialog, id ->
                    dialog.dismiss()
                }
        val alert = builder.create()
        alert.show()


    }


    override fun updateComment(comment: Comments?) {
        yorumText.text =  comment?.comment?.toEditable()
//        view?.let {
//            yorumText.callOnClick()
//           // upKeyboard(it)
//            }
        buttonAddComment.setOnClickListener {
            addComment(comment?.id)
            closeKeyboard(it)
        }

    }


}