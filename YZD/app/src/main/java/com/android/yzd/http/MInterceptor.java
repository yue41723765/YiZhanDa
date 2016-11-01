package com.android.yzd.http;

import android.content.Context;


import com.android.yzd.tools.NetUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/28.
 */

public class MInterceptor implements Interceptor {
    Context context;

    public MInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //如果没有网络，则启用 FORCE_CACHE
        if (!NetUtils.isConnected(context)) {
            request = request
                    .newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        return originalResponse
                .newBuilder()
                //在这里生成新的响应并修改它的响应头 1小时内部更新图片
                .header("Cache-Control", "public,max-age=3600")
                .removeHeader("Pragma").build();
    }
}
