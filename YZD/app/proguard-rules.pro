# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
            #指定代码的压缩级别
            -optimizationpasses 5

            #包明不混合大小写
            -dontusemixedcaseclassnames

            #不去忽略非公共的库类
            -dontskipnonpubliclibraryclasses

             #优化  不优化输入的类文件
            -dontoptimize

             #不做预校验
            -dontpreverify

             #混淆时是否记录日志
            -verbose

             # 混淆时所采用的算法
            -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#支付宝混淆
-libraryjars libs/alipaySDK-20170710.jar

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
#微信混淆
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}
