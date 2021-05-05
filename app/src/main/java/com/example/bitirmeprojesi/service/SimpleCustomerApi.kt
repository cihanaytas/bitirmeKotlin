package com.example.bitirmeprojesi.service


import com.example.bitirmeprojesi.models.ReqBodyLogin
import com.example.bitirmeprojesi.models.ShopDto
import com.example.bitirmeprojesi.models.StoreDetails
import com.example.bitirmeprojesi.models.products.*
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*


interface SimpleCustomerApi{


    @GET("logout")
    fun logOut(): Call<String>

    @GET("test")
    fun testCustomer(): Deferred<Response<String>>

    //product listesini alır
    @GET("products")
    fun getAllProducts(): Deferred<Response<List<Product>>>

    //product listesi paging ile alınır
    @GET("plist/{page}")
    fun getAllProductsPaging(@Path("page") page:Int): Deferred<Response<List<Product>>>

    //product listesi categorye göre alınır
    @GET("plist/{category}/{page}")
    fun getAllProductsPagingCategory(@Path("page") page:Int, @Path("category") category:String): Deferred<Response<List<Product>>>

    //
    @GET("products/{nickname}")
    fun getAllProductsByNickname(@Path("nickname") nickname:String): Deferred<Response<List<Product>>>

    //id'si verilen product alınır
    @GET("product/{id}")
    fun getProduct(@Path("id") id:Long) : Deferred<Response<Product>>

    //product puanlama
    @POST("pointproduct/{productId}/{point}")
    fun pointToProduct(@Path("productId") productId:Long,@Path("point") point:Double): Call<Void>

    //store detayları
    @GET("getstoredetail/{storeNickName}")
    fun getStoreDetail(@Path("storeNickName") storeNickName:String) : Deferred<Response<StoreDetails>>

    //alışveriş tamamlanır
    @POST("sales")
    fun sales(@Body cartItemDtoList: List<CartItemDto>) : Call<String>

    //daha önce yapılan alışverişleri listeler
    @GET("shoppinglist")
    fun getShoppingList(): Deferred<Response<List<ShopDto>>>

    @GET("getcartitem/{shoppingId}")
    fun getCartItemList(@Path("shoppingId") shoppingId: Long) : Deferred<Response<List<CartItem>>>

    @GET("getcommentlist/{productId}")
    fun getCommentList(@Path("productId") productId: Long) : Deferred<Response<List<Comments>>>

    @POST("addcomment")
    fun addComment(@Body comments: CommentDto) : Call<Boolean>

    @PUT("updatecomment")
    fun updateComment(@Body comments: CommentDto) : Call<Boolean>

    @DELETE("deletecomment/{commentId}")
    fun deleteComment(@Path("commentId") commentId: Long) : Call<String>


}