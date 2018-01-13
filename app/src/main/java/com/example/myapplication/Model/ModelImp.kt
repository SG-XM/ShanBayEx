package com.example.myapplication.Model

import android.util.Log
import com.example.myapplication.PresentAPI.PresenterImp
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by 上官轩明 on 2018/1/9.
 */
class ModelImp(val presenter: PresenterImp) : ModelAPI {
    lateinit var auth_token: String
    lateinit var csrftoken: String
    val retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().addNetworkInterceptor(LoggingInterceptor()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl("https://www.shanbay.com/")
            .build()

    val putRetrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().addNetworkInterceptor(LoggingInterceptor()).build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl("https://www.shanbay.com/")
            .build()

    override fun login() {
        //添加拦截器
        class HttpLogger : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                callbackToken(message)
            }
        }

        val logInterceptor = HttpLoggingInterceptor(HttpLogger())
        logInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        val mOkHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(logInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                //.addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://www.shanbay.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        //巨坑：提交格式为json需要GsonConverter，但是就不能添加ScalarsConverter，于是只能手动转换json
        val obj = Gson().toJson(LoginBody("USERNAME", "PASSWORD"));
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), obj)
        val mApi = retrofit.create(RetrofitAPI::class.java)
        //rxjava配合retrofit
        mApi.login(body)
                .subscribeOn(Schedulers.io())//io线程发起网络请求
                .observeOn(AndroidSchedulers.mainThread())//在主线程观察
                .subscribe(this::callback, { e -> e.printStackTrace() })
    }


    override fun check(token: String, callback: (CheckBean) -> Unit) {

        val mApi = retrofit.create(RetrofitAPI::class.java)
        mApi.check(System.currentTimeMillis().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback, { e -> e.printStackTrace() })
    }


    override fun checkin(token: String, callback: (CheckinBean) -> Unit) {
        val mApi = retrofit.create(RetrofitAPI::class.java)
        mApi.checkin("auth_token=" + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback, { e -> e.printStackTrace() })
    }

    override fun getArticleList(callback: (ArticleListBean) -> Unit) {
        val mApi = retrofit.create(RetrofitAPI::class.java)
        mApi.getArticleList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback, { e -> e.printStackTrace() })
    }


    override fun getListenList(callback: (ListenListBean) -> Unit) {
        val mApi = retrofit.create(RetrofitAPI::class.java)
        mApi.getListenList(System.currentTimeMillis().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback, { e -> e.printStackTrace() })
    }

    override fun getListenStatus(callback: (ListenStatus) -> Unit) {
        val mApi = retrofit.create(RetrofitAPI::class.java)
        mApi.getListenCnt(System.currentTimeMillis().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback, { e -> e.printStackTrace() })
    }


    override fun getFreshStBean(callback: (FreshStBean) -> Unit) {
        val mApi = retrofit.create(RetrofitAPI::class.java)
        mApi.getFreshStBean(System.currentTimeMillis().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback)
    }

    override fun read(id: String, callback: (String) -> Unit) {

        val random = Random(System.currentTimeMillis())
        val used_time = (random.nextDouble() % 200) + 300
        val mApi = putRetrofit.create(RetrofitAPI::class.java)
        val obj = Gson().toJson(ReadBody("finish", used_time))
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), obj)

        mApi.pre_read("https://www.shanbay.com/news/articles/" + id, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ ->
                    mApi.read("https://www.shanbay.com/news/articles/" + id, id, body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(callback, { e -> e.printStackTrace() })
                }, { e -> e.printStackTrace() })
    }


    override fun listen(article_id: String, id: String, callback: (ListenStatus) -> Unit) {

        val obj = Gson().toJson(ListenBody("1", "100", 0, 0, 3))
        Log.d("woggle", obj)
        val body = RequestBody.create(okhttp3.MediaType.parse("Content-Type: application/x-www-form-urlencoded; charset=UTF-8"), obj)

        val mApi = putRetrofit.create(RetrofitAPI::class.java)
        mApi.preListen1(id, System.currentTimeMillis().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    mApi.preListen2(id, System.currentTimeMillis().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe({
                                retrofit.create(RetrofitAPI::class.java).listen(article_id, id, body)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe({
                                            mApi.preListen2(id, System.currentTimeMillis().toString())
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(Schedulers.io())
                                                    .subscribe({ getListenStatus(callback) }, { e -> e.printStackTrace() })
                                        }, { e -> e.printStackTrace() })
                            }, { e -> e.printStackTrace() })
                }, { e -> e.printStackTrace() })
    }


    override fun learnSt(id: String, callback: (String) -> Unit) {
        val obj: String = "{\"" + id + "\":1}"
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), obj)

        val mApi = putRetrofit.create(RetrofitAPI::class.java)
        mApi.st1(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val obj: String = "{\"" + id + "\":2}"
                    val body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), obj)
                    mApi.st1(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(callback,{e->e.printStackTrace()})},{e->e.printStackTrace()})
    }

    override fun callback(res: String) {
        presenter.showlogs(res)
    }

    override fun callbackToken(header: String) {
        presenter.setToken(header)
    }


    inner class LoggingInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val request = chain!!
                    .request()
                    .newBuilder()
                    .addHeader("Cookie", "auth_token=" + this@ModelImp.auth_token + ";csrftoken=" + this@ModelImp.csrftoken)
                    .addHeader("x-csrftoken", csrftoken)
                    .build()
            return chain.proceed(request)
        }
    }
//        @Override public Response intercept(Interceptor.Chain chain) throws IOException {
//            Request request = chain.request();
//
//            long t1 = System.nanoTime();
//            logger.info(String.format("Sending request %s on %s%n%s",
//                    request.url(), chain.connection(), request.headers()));
//
//            Response response = chain.proceed(request);
//
//            long t2 = System.nanoTime();
//            logger.info(String.format("Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//
//            return response;
//        }


}


//override fun login() {


////        val okHttpClient = OkHttpClient.Builder()
////                .addInterceptor { chain ->
////                    val request = chain.request()
////                    var bf = okio.Buffer()
////                    request.body()?.writeTo(bf)
////                    //Log.i("woggle", "request====111111111111111111111111111111")
////                    //Log.i("woggle", "request====" +bf.readUtf8())
////                    val proceed = chain.proceed(request)
////                    // Log.i("woggle", "proceed====" + proceed.headers().toString())
////                    proceed
////                }.build()
//
//        val retrofit = Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                //.client(okHttpClient)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .baseUrl("https://www.shanbay.com/")
//                .build()
//
//        val map = HashMap<String, String>()
//        map.put("username", "13821547562")
//        map.put("password", "zh1154097852")
//        val mApi = retrofit.create(RetrofitAPI::class.java)
//
//        var rb = FormBody.Builder().add("username", "13821547562")
//                .add("password", "zh1154097852")
//                .build()
//            mApi.login(rb)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(this::callback)
//
//
////        mApi.login1(rb).enqueue(object : Callback<String> {
////            override fun onFailure(call: Call<String>?, t: Throwable?) {
////
////                t?.printStackTrace()
////            }
////
////            override fun onResponse(call: Call<String>?, response: Response<String>) {
////
////                //if(response.isSuccessful)
////                Log.d("woggle", response.body().toString())
////            }
////
////        })
//}
