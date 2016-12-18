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

    //地址模块

    @FormUrlEncoded
    @POST("index.php/Api/Address/addAddress")
    Observable<HttpResult> addAddress(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/addressList")
    Observable<HttpResult> addressList(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/addressInfo")
    Observable<HttpResult> addressInfo(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/modifyAddress")
    Observable<HttpResult> modifyAddress(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/setDefaultAddress")
    Observable<HttpResult> setDefaultAddress(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/deleteAddress")
    Observable<HttpResult> deleteAddress(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/getOneAddress")
    Observable<HttpResult> getOneAddress(@FieldMap Map<String, String> param);

    //消息模块
    @FormUrlEncoded
    @POST("index.php/Api/Message/messageIndex")
    Observable<HttpResult> messageIndex(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Message/systemMessageList")
    Observable<HttpResult> systemMessageList(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Message/systemMessageInfo")
    Observable<HttpResult> systemMessageInfo(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Message/orderNoticeList")
    Observable<HttpResult> orderNoticeList(@FieldMap Map<String, String> param);

    //    商品模块
    @Multipart
    @POST("index.php/Api/Goods/index")
    Observable<HttpResult> index(@PartMap Map<String, RequestBody> param);

    @FormUrlEncoded
    @POST("index.php/Api/Goods/hotSearch")
    Observable<HttpResult> hotSearch(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Goods/goodsTypeList")
    Observable<HttpResult> goodsTypeList(@FieldMap Map<String, String> param);

    @Multipart
    @POST("index.php/Api/Goods/searchResult")
    Observable<HttpResult> searchResult(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/hotSalesList")
    Observable<HttpResult> hotSalesList(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/goodsList")
    Observable<HttpResult> goodsList(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/goodsInfo")
    Observable<HttpResult> goodsInfo(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/addCollect")
    Observable<HttpResult> addCollect(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/deleteCollect")
    Observable<HttpResult> deleteCollect(@PartMap Map<String, RequestBody> param);
}
