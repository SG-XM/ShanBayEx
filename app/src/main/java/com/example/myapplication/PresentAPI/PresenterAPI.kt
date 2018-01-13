package com.example.myapplication.PresentAPI

import com.example.myapplication.Model.*


/**
 * Created by 上官轩明 on 2018/1/9.
 */
interface PresenterAPI {
    fun login()
    fun check()
    fun checkin()
    fun getArticleList()
    fun getListenList()
    fun getListenStatus()
    fun getFreshStBean()
    fun read()
    fun listen()
    fun learnSt()
    fun start()
    fun showlogs(logs: String)
    fun setToken(message:String)
    fun callbackCheck(checkBean: CheckBean)
    fun callbackCheckin(checkinBean: CheckinBean)
    fun callbackGetArtList(articleListBean: ArticleListBean)
    fun callbackGetListenList(listenListBean: ListenListBean)
    fun callbackGetLisStatus(listenStatus: ListenStatus)
    fun callbackGetFreshStBean(freshStBean: FreshStBean)
    fun callbackRead(message: String)
    fun callbackListen(message: String)
    fun setAlarm(isInit:Boolean)
    fun getAlarm():String
}