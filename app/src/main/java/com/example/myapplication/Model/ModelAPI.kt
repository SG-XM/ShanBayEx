package com.example.myapplication.Model

/**
 * Created by 上官轩明 on 2018/1/9.
 */
interface ModelAPI {
    fun login()
    fun check(token:String,callback:(CheckBean)->Unit)
    fun checkin(token:String,callback:(CheckinBean)->Unit)
    fun getArticleList(callback:(ArticleListBean)->Unit)
    fun getListenList(callback: (ListenListBean) -> Unit)
    fun getListenStatus(callback: (ListenStatus)->Unit)
    fun getFreshStBean(callback: (FreshStBean) -> Unit)
    fun read(id:String,callback: (String) -> Unit)
    fun listen(article_id:String,id:String,callback: (ListenStatus) -> Unit)
    fun learnSt(id:String,callback: (String) -> Unit)
    fun callback(res:String)
    fun callbackToken(header:String)
}