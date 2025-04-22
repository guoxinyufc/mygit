package com.example.finalwork.network;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    //默认地址
    public static String BASE_URL="https://aip.baidubce.com";
    /*
    创建服务，参数是API服务
    @param serviceClass 服务接口
    @param <T> 泛型规范
    @return api接口
     */
    public static <T> T createService(Class<T> serviceClass){
        //创建OkHttpClient构建器对象
        OkHttpClient.Builder okHttpClientBuilder=new OkHttpClient.Builder();
        //设置请求超时时间
        okHttpClientBuilder.connectTimeout(20000, TimeUnit.MILLISECONDS);
        //消息拦截器，可以看到接口返回的所有内容
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        //setlevel用来设置日志打印级别，BODY表示请求行/响应行+消息头+消息主体
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //给OkHttp添加消息拦截器
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        //在Retrofit中设置httpclient
        //网络请求的工作本质上是由okhttp完成，retrofit负责网络请求接口的封装
        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                //用Gson把服务端返回的json数据解析成实体
                .addConverterFactory(GsonConverterFactory.create())
                //将okHttp放入
                .client(okHttpClientBuilder.build()).build();
        //返回这个创建好的API服务
        return retrofit.create(serviceClass);
    }
}
