package com.android.yzd.http;


import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/29.
 */

public interface HttpService {

    @FormUrlEncoded
    @POST("index.php/Api/Sms/sendVerify")
    Observable<HttpResult> sendVerify(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Sms/checkVerify")
    Observable<HttpResult> checkVerify(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Article/userRegAgr")
    Observable<HttpResult> userRegAgr(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/register")
    Observable<HttpResult> register(@FieldMap Map<String, String> param);


    @FormUrlEncoded
    @POST("index.php/Api/Member/forgetPass")
    Observable<HttpResult> forgetPassword(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/login")
    Observable<HttpResult> login(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/modifyPass")
    Observable<HttpResult> modifyPassword(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/memberCenter")
    Observable<HttpResult> memberCenter(@FieldMap Map<String, String> param);


    @FormUrlEncoded
    @POST("index.php/Api/Member/modifyAccount")
    Observable<HttpResult> modifyAccount(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/memberBaseData")
    Observable<HttpResult> memberBaseData(@FieldMap Map<String, String> param);

    @Multipart
    @POST("index.php/Api/Member/modifyMemberBaseData")
    Observable<HttpResult> modifyMemberBaseData(@PartMap Map<String, RequestBody> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/couponList")
    Observable<HttpResult> couponList(@FieldMap Map<String, String> param);


}
