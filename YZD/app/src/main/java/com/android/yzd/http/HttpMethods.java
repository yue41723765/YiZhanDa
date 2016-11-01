package com.android.yzd.http;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/29.
 */

public class HttpMethods {


    public static final String BASE_URL = "http://192.168.0.124:8080";
//    public static final String BASE_URL = "http://192.168.0.110:8088";

    public static final String UPLOAD_FILE = "http://101.200.81.142:8080";

    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private HttpService httpService;
    private static Context mContext;

    private HttpMethods(Context context) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .client(getCilent(context).build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    private HttpMethods(String url) {

        File httpCacheDirectory = new File(mContext.getCacheDir(), "image");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addNetworkInterceptor(interceptor);
        client.cache(cache);
        client.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
        httpService = retrofit.create(HttpService.class);
    }


    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods(mContext);
    }

    private static class UploadSingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods(UPLOAD_FILE);
    }


    //获取单例
    public static HttpMethods getInstance(Context context) {
        mContext = context;
        return SingletonHolder.INSTANCE;
    }

    public static HttpMethods getUploadInstance(Context context) {
        mContext = context;
        return UploadSingletonHolder.INSTANCE;
    }


    private OkHttpClient.Builder getCilent(Context context) {

        File httpCacheDirectory = new File(mContext.getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, 100 * 1024 * 1024);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addNetworkInterceptor(interceptor);
        client.interceptors().add(new ReceivedCookiesInterceptor(context));
        client.interceptors().add(new AddCookiesInterceptor(context));
        client.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        client.cache(cache);
        return client;
    }

    //    设置max-age为60s之后，这60s之内不管你有没有网,都读缓存。（这也就说明了为什么不能实现我上面说的功能）；max-stale设置为4周，意思是，网络未连接的情况下设置缓存时间为4周。
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=60";
            }
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    //添加线程管理并订阅
    private void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getStatus().equals("F")) {
                throw new ApiException(httpResult.getMsg());
            }
            return httpResult.getData();
        }
    }




}
