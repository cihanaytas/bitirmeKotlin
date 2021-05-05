package com.example.bitirmeprojesi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.CommentRecyclerRowBinding
import com.example.bitirmeprojesi.models.products.CartItem
import com.example.bitirmeprojesi.models.products.Comments

class CommentsRecyclerAdapter(val commentList: ArrayList<Comments>, commentInterface: CommentsRecyclerAdapter.CommentInterface?)
    : RecyclerView.Adapter<CommentsRecyclerAdapter.CommentViewHolder>(){

    var commentInterface: CommentsRecyclerAdapter.CommentInterface? = commentInterface

    class CommentViewHolder(var view: CommentRecyclerRowBinding,commentInterface:CommentInterface?) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CommentRecyclerRowBinding>(inflater, R.layout.comment_recycler_row,parent,false)

        view.commentInterface = commentInterface

        return CommentViewHolder(view,commentInterface)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.view.comment = commentList[position]

        if(commentList[position].byYou==true){
            holder.view.commentUpdate.visibility = View.VISIBLE
            holder.view.commentDelete.visibility = View.VISIBLE
        }
        if(commentList[position].byYou==false){
            holder.view.commentUpdate.visibility = View.GONE
            holder.view.commentDelete.visibility = View.GONE
        }

        val year = commentList[position].date.substring(0,4)
        val month = commentList[position].date.substring(5,7) + "/"
        val day = commentList[position].date.substring(8,10) + "/"

        holder.view.commentDate.text = day+month+year
    }

    fun commentListUpdate(newCommentList: ArrayList<Comments>){
        commentList.clear()
        commentList.addAll(newCommentList)
        notifyDataSetChanged()
    }


    interface CommentInterface {
        fun deleteComment(commentId: Long)
        fun updateComment(comment: Comments?)
    }
}