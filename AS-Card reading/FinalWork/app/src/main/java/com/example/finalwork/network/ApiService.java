package com.example.finalwork.network;

import com.example.finalwork.model.GetDiscernResultResponce;
import com.example.finalwork.model.GetTokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//API服务
public interface ApiService {
    /*
    获取鉴权认证的Token
    @param grant_type Oauth2.0的授权模式类型
    @param client_id 客户端ID
    @param client_secret 客户端密钥
     */
    @FormUrlEncoded
    @POST("/oauth/2.0/token")//指定接口
    Call<GetTokenResponse> getToken(@Field("grant_type") String grant_type,
                                    @Field("client_id") String client_id,
                                    @Field("client_secret") String client_secret);

    /*
     获取图像识别结果
     @param accessToken 获取鉴权认证Token
     @param image 图片base64
     @param url 网络图片Url
     @return JsonObject
     */
    @FormUrlEncoded
    @POST("/rest/2.0/image-classify/v2/advanced_general")//指定接口为“通用物品和场景识别”
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Call<GetDiscernResultResponce> getDiscernResult(@Field("access_token") String accessToken,
                                                    @Field("image") String image,
                                                    @Field("url") String url);
}
