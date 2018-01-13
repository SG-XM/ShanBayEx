package com.example.myapplication.Model

import com.google.gson.JsonObject
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import rx.Observable

/**
 * Created by 上官轩明 on 2018/1/9.
 */
interface RetrofitAPI {

    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "X-CSRFToken:null",
            "Content-Type:application/json")
    @PUT("api/v1/account/login/web/")
    fun login(@Body body: RequestBody): Observable<String>


    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "X-CSRFToken:null",
            "Content-Type:application/json")
    @GET("api/v1/checkin/?for_web=true&}")
    fun check(@Query("_=") time: String): Observable<CheckBean>//@Header("Cookie") auth_token:String


    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "X-CSRFToken:null",
            "Content-Type:application/json")
    @POST("api/v1/checkin/?for_web=true")
    fun checkin(@Header("Cookie") auth_token: String): Observable<CheckinBean>

    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "X-CSRFToken:null",
            "Content-Type:application/json")
    @GET("api/v2/news/articles/?ipp=10&page=1")
    fun getArticleList(): Observable<ArticleListBean>


    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "Content-Type:application/json")
    @GET("api/v2/news/articles/{id}/")
    fun pre_read(@Header("Referer") refer: String, @Path("id") id: String): Observable<String>

    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "Content-Type:application/json")
    @PUT("api/v2/news/user/articles/{id}/")
    fun read(@Header("Referer") refer: String, @Path("id") id: String, @Body requestBody: RequestBody): Observable<String>


    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "Content-Type:application/json")
    @GET("api/v1/listen/usersentence/?level=1")
    fun getListenList(@Query("_") time: String): Observable<ListenListBean>

    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "Content-Type:application/json",
            "Referer:https://www.shanbay.com/listen/sentence/")
    @GET("api/v1/listen/note/?")
    fun preListen1(@Query("sentence_ids") id: String, @Query("_") time: String): Observable<String>


    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "Content-Type:application/json",
            "Referer:https://www.shanbay.com/listen/sentence/")
    @GET("api/v1/listen/note/?note_type=notown")
    fun preListen2(@Query("sentence_ids") id: String, @Query("_") time: String): Observable<String>


    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "Content-Type:application/json",
            "Referer:https://www.shanbay.com/listen/sentence/")
    @PUT("api/v1/listen/usersentence/{article_id}/{id}")
    fun listen(@Path("article_id") article_id: String, @Path("id") id: String, @Body body: RequestBody): Observable<ListenBean>


    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "Content-Type:application/json",
            "Referer:https://www.shanbay.com/listen/sentence/")
    @GET("api/v1/listen/userstats/?")
    fun getListenCnt(@Query("_") time: String): Observable<ListenStatus>


    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "Content-Type:application/json",
            "Referer:https://www.shanbay.com/sentence/review/")
    @GET("api/v1/sentence/review/?update_type=fresh")
    fun getFreshStBean(@Query("_") time:String):Observable<FreshStBean>


    @Headers("Accept:*/*",
            "Origin:https://www.shanbay.com",
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36",
            "Content-Type:application/json",
            "Referer:https://www.shanbay.com/sentence/review/")
    @PUT("api/v1/sentence/review/")
    fun st1(@Body requestBody: RequestBody):Observable<String>
}

data class LoginBody(val username: String, val password: String)



/////////////////////////////////////////////////////FreshStBean///////////////////////////////////////////////////////
data class FreshStBean(
        val msg: String = "", //SUCCESS
        val status_code: Int = 0, //0
        val data: FreshStData = FreshStData()
)

data class FreshStData(
        val reviews: List<Review> = listOf(),
        val article: Article = Article()
)

data class Article(
        val content: String = "",
        val article_type: Int = 0, //1
        val xml_content: String = "",
        val id: Int = 0 //346
)

data class Review(
        val due_review_date: String = "", //2018-01-13
        val review_status: Int = 0, //1
        val sentence_id: Int = 0, //2311468
        val last_review_status: Int = 0, //0
        val id: Int = 0, //212645908
        val retention: Int = 0 //3
)
/////////////////////////////////////////////////////FreshStBean///////////////////////////////////////////////////////



////////////////////////////////////////////////////ListenStatus////////////////////////////////////////////////////////

data class ListenStatus(
        val msg: String = "", //SUCCESS
        val status_code: Int = 0, //0
        val data: ListenStatusData = ListenStatusData()
)

data class ListenStatusData(
        val articles: Int = 0, //0
        val user_id: Int = 0, //98011411
        val num_score: Int = 0, //120
        val finished_articles: Int = 0, //0
        val accumulated_finished_articles: Int = 0, //0
        val update_at: String = "", //2018-01-13T16:57:15
        val create_at: String = "", //2018-01-13T12:45:54
        val accumulated_num_score: Int = 0, //120
        val finished_article_sentences: Int = 0, //0
        val used_minutes: Int = 0, //0
        val accumulated_finished_sentences: Int = 0, //32
        val sentences: Int = 0, //40
        val date: String = "", //2018-01-13
        val accumulated_sentences: Int = 0, //40
        val finished_sentences: Int = 0, //32
        val accumulated_articles: Int = 0, //0
        val id: Int = 0, //300089575
        val user: User = User()
)

data class User(
        val username: String = "", //mobile_9a96943138
        val timezone: String = "", //Asia/Shanghai
        val nickname: String = "", //mobile_9a96943138
        val id: Int = 0, //98011411
        val avatar: String = "" //https://static.baydn.com/static/img/icon_head.png
)

////////////////////////////////////////////////////ListenStatus////////////////////////////////////////////////////////


///////////////////////////////////////////////////////ListenBody//////////////////////////////////////////////////////////
//result=1&correct_ratio=100&delta_failed_times=0&num_hints=0&num_score=3
data class ListenBody(val result: String, val correct_ratio: String, val delta_failed_times: Int, val num_hints: Int, val num_score: Int)


///////////////////////////////////////////////////////ListenBody//////////////////////////////////////////////////////////


//////////////////////////////////////////////////////ListenBean////////////////////////////////////////////////////////
data class ListenBean(
        val msg: String = "", //SUCCESS
        val status_code: Int = 0, //0
        val data: ListenBeanData = ListenBeanData()
)

data class ListenBeanData(
        val user_id: Int = 0, //98011411
        val review_status: Int = 0, //9
        val sentence_id: Long = 0, //13326247849
        val num_score: Int = 0, //3
        val failed_times: Int = 0, //0
        val num_hints: Int = 0, //0
        val article_id: Int = 0, //1645
        val id: Long = 0, //57978275461
        val correct_ratio: Int = 0 //100
)

//////////////////////////////////////////////////////ListenBean////////////////////////////////////////////////////////


/////////////////////////////////////////////////////ListenListBean///////////////////////////////////////////////////

data class ListenListBean(
        val msg: String = "", //SUCCESS
        val status_code: Int = 0, //0
        val data: List<ListenListData> = listOf()
)

data class ListenListData(
        val blanks: List<Blank> = listOf(),
        val audio_addresses: List<String> = listOf(),
        val review_status: Int = 0, //0
        val share_urls: ListenShareUrls = ListenShareUrls(),
        val completion_content: String = "",
        val audio_url: String = "", //https://media-audio0.qiniu.baydn.com/listen/sentence/29a1b68e4f18dfc5b1cf1825c77499ef_1.mp3?sign=90bbae880e61a775cb541e4a6587be1f&t=5a599c04
        val sentence_id: Long = 0, //13326220099
        val audio_name: String = "", //512dc2c1a16d687cf89645b69ecbd989
        val audio_length: Int = 0, //30092
        val audio_md5: String = "", //9ece889f383f90d47ee6fcea83179251
        val id: Long = 0, //57977534578
        val correct_ratio: Int = 0, //0
        val num_verified_notes: Int = 0, //1
        val end: Int = 0, //0
        val user_id: Int = 0, //98011411
        val highlights: List<Any> = listOf(),
        val has_audio: Boolean = false, //true
        val share_url: String = "", //http://share.shanbei-cet6.com/listen/sentence/13326220099
        val content: String = "", // We go to school from Monday to Friday.
        val start: Int = 0, //0
        val article_id: Int = 0, //1642
        val num_score: Int = 0, //0
        val failed_times: Int = 0, //0
        val num_hints: Int = 0 //0
)

data class ListenShareUrls(
        val weibo: String = "", //http://share.shanbei-toefl.com/listen/sentence/13326220099?channel=weibo
        val shanbay: String = "", //http://share.shanbei-toefl.com/listen/sentence/13326220099?channel=shanbay
        val wechat: String = "", //http://share.shanbei-toefl.com/listen/sentence/13326220099?channel=wechat
        val qzone: String = "" //http://share.shanbei-toefl.com/listen/sentence/13326220099?channel=qzone
)

data class Blank(
        val content: String = "", //go
        val offset: Int = 0 //1
)


/////////////////////////////////////////////////////ListenListBean///////////////////////////////////////////////////


///////////////////////////////////////////////////ArticleListBean////////////////////////////////////////////

data class ArticleListBean(
        val data: ArticleListData = ArticleListData(),
        val status_code: Int = 0, //0
        val msg: String = "" //SUCCESS
)

data class ArticleListData(
        val total: Int = 0, //100
        val ipp: Int = 0, //10
        val page: Int = 0, //1
        val objects: List<Object> = listOf()
)

data class Object(
        val id: String = "", //vknna
        val title_en: String = "", //University professor sacked for sexual harassment
        val title_cn: String = "", //北航教授因性骚扰学生被撤职
        val date: String = "", //2018-01-12
        val grade: Int = 0, //4
        val length: Int = 0, //145
        val num_reviews: Int = 0, //515
        val grade_info: String = "", //四级
        val thumbnail_urls: List<String> = listOf(),
        val summary: String = "", //A professor has been sacked from Beihang University for sexual harassment, the Beijing-based University said in a statement Thursday night.  Chen Xiaowu was removed from all his posts for seriously violating teachers ethics and code of conduct, the statement said.  The investigation into Chen was launched after several of his former students reported his sexual harassment to the university. ...
        val source_id: String = "", //bgnca
        val grade_scores: String = "", //{"L3:high": 77.05, "L5:cet6": 98.36, "L1:primary": 29.51, "L2:secondary": 63.93, "L4:cet4": 96.72, "L6:IELTS&TOEFL": 98.36, "L8:GRE": 98.36, "L7:SAT": 98.36}
        val article_desc: ArticleDesc = ArticleDesc(),
        val types: Int = 0, //1
        val is_liked: Boolean = false, //false
        val is_reviewed: Boolean = false, //false
        val is_finished: Boolean = false //false
)

data class ArticleDesc(
        val has_video: Boolean = false, //false
        val has_audio: Boolean = false //true
)
///////////////////////////////////////////////////ArticleListBean////////////////////////////////////////////


///////////////////////////////////////////////////ReadBody//////////////////////////////////////////////////////

data class ReadBody(
        val operation: String = "", //finish
        val used_time: Double = 0.0 //138.838
)

///////////////////////////////////////////////////ReadBody//////////////////////////////////////////////////////
////////////////////////////////////////CheckBean//////////////////////////////////////////
data class CheckBean(
        val msg: String = "", //SUCCESS
        val status_code: Int = 0, //0
        val data: Data = Data()
)

data class Data(
        val tasks: List<Task> = listOf(),
        val checked: Boolean = false, //false
        val share_type: String = "", //image
        val num_checkin_days: Int = 0, //451
        val finished: Boolean = false, //false
        val need_captcha: Boolean = false, //false
        val user_note: String = ""
)

data class Task(
        val status: Int = 0, //0
        val is_in_plan: Int = 0, //0
        val finished: Boolean = false, //false
        val meta: Meta = Meta(),
        val name: String = "", //bdc
        val daily_init_finished: Boolean = false, //true
        val task_name_en: String = "", //Vocabulary
        val tip: String = "", //开始单词任务
        val task_name_cn: String = "", //单词
        val redirect_url: String = "", ///review/
        val msg: String = "" //你今天需要学习 100 个单词，包括 0 个新词，还剩下 100 个
)

data class Meta(
        val num_left: Int = 0, //100
        val url: String = "", ///bdc/review/
        val num_today: Int = 0, //100
        val used_time: Double = 0.0, //0.0
        val correctness: Int = 0 //0
)
////////////////////////////////////////CheckBean//////////////////////////////////////////

////////////////////////////////////////CheckinBean//////////////////////////////////////////

data class CheckinBean(
        val msg: String = "", //SUCCESS
        val status_code: Int = 0, //0
        val data: CheckinData = CheckinData()
)

data class CheckinData(
        val tasks: List<CheckinTask> = listOf(),
        val checked: Boolean = false, //true
        val track_object_img: TrackObjectImg = TrackObjectImg(),
        val msgs: Msgs = Msgs(),
        val user_id: Int = 0, //98011411
        val url: String = "", //https://www.shanbay.com/web/users/98011411/certificate/57936286183/
        val track_object: TrackObject = TrackObject(),
        val num_checkin_days: Int = 0, //4
        val share_urls: ShareUrls = ShareUrls(),
        val share_url: String = "", //https://www.shanbay.com/web/users/98011411/certificate/57936286183/
        val checkin_days: Int = 0, //4
        val note: String = "",
        val finished: Boolean = false, //true
        val share_type: String = "", //image
        val user_note: String = "",
        val share_image: String = "", //http://share.shanbei-ielts.com/checkin/certificate/98011411/57936286183.png
        val accumulated_value: Int = 0, //0
        val daily_value: Int = 0, //0
        val id: Long = 0, //57936286183
        val share_content: ShareContent = ShareContent()
)

data class ShareUrls(
        val weibo: String = "", //https://www.shanbay.com/web/users/98011411/certificate/57936286183/?channel=weibo
        val shanbay: String = "", //https://www.shanbay.com/web/users/98011411/certificate/57936286183/?channel=shanbay
        val wechat: String = "", //https://www.shanbay.com/web/users/98011411/certificate/57936286183/?channel=wechat
        val qzone: String = "", //https://www.shanbay.com/web/users/98011411/certificate/57936286183/?channel=qzone
        val wechat_user: String = "" //https://www.shanbay.com/web/users/98011411/certificate/57936286183/?channel=wechat_user
)

data class CheckinTask(
        val status: Int = 0, //0
        val is_in_plan: Int = 0, //0
        val finished: Boolean = false, //true
        val meta: CheckinMeta = CheckinMeta(),
        val name: String = "", //bdc
        val daily_init_finished: Boolean = false, //true
        val task_name_en: String = "", //Vocabulary
        val tip: String = "", //完成
        val task_name_cn: String = "", //单词
        val redirect_url: String = "", ///review/
        val msg: String = "" //你今天学习了 100 个单词，用时 1 分钟
)

data class CheckinMeta(
        val num_left: Int = 0, //0
        val url: String = "", ///bdc/review/
        val num_today: Int = 0, //100
        val used_time: Double = 0.0, //1.0
        val correctness: Int = 0 //0
)

data class ShareContent(
        val topic: String = "", //扇贝打卡
        val image_url: String = "", //https://media-image1.baydn.com/checkin_pub_image%2Fvfwkxe%2Ff0b420fb5d0d579216e5d7107ad231fe.8aac239eccc94a90e93373ed3403e83c.png
        val description: String = "", //加入我，一起扇贝打卡
        val title: String = "" //我在扇贝学英语的第4天，坚持学习，未来可期 (๑•̀ㅂ•́)و✧
)

data class Msgs(
        val weibo: List<String> = listOf(),
        val wechat: List<String> = listOf(),
        val qzone: List<String> = listOf()
)

data class TrackObject(
        val code: String = "", //dcb80
        val object_id: Long = 0, //57936286183
        val share_url: String = "" //https://www.shanbay.com/web/users/98011411/certificate/57936286183/
)

data class TrackObjectImg(
        val code: String = "", //eb3fd
        val object_id: Long = 0, //57936286183
        val share_url: String = "" //https://www.shanbay.com/web/users/98011411/certificate/57936286183/
)
////////////////////////////////////////CheckinBean//////////////////////////////////////////

