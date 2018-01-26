package org.wah.cloned.rongcloud.commons.response;

public class TokenResponse extends CodeResponse{

    private String token;
    private String userId;

    public TokenResponse(){

    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
}
