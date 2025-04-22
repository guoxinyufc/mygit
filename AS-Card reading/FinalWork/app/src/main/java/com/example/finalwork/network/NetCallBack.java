package com.example.finalwork.network;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//网络请求回调
public abstract class NetCallBack<T> implements Callback<T>{
    //访问成功回调
    @Override
    public void onResponse(Call<T> call, Response<T> response) {//数据返回
        if(response!=null && response.body()!=null && response.isSuccessful()){
            onSuccess(call, response);
        }
        else{
            onFailed(response.raw().toString());
        }
    }
    //访问失败回调
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d("data str",t.toString());
        onFailed(t.toString());
    }

    public abstract void onSuccess(Call<T> call, Response<T> response);//成功
    public abstract void onFailed(String errorStr);//失败
}


