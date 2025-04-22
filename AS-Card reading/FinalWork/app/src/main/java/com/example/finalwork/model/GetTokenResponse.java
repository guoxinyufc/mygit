package com.example.finalwork.model;
//获取鉴权认证Token响应实体
public class GetTokenResponse {//网络请求返回数据后会通过Retrofit解析成该实体
    private String refresh_token;
    private long expires_in;//到期时间
    private String scope;//授权范围
    private String session_key;
    private String access_token;
    private String session_secret;
    //refresh_token
    public String getRefresh_token(){
        return refresh_token;
    }
    public void setRefresh_token(String refresh_token){
        this.refresh_token=refresh_token;
    }
    //expires_in
    public long getExpires_in(){
        return expires_in;
    }
    public void setExpires_in(long expires_in){
        this.expires_in=expires_in;
    }
    //scope
    public String getScope(){
        return scope;
    }
    public void setScope(String scope){
        this.scope=scope;
    }
    //session_key
    public String getSession_key() {
        return session_key;
    }
    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
    //access_key
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    //session_secret
    public String getSession_secret() {
        return session_secret;
    }
    public void setSession_secret(String session_secret) {
        this.session_secret = session_secret;
    }
}
