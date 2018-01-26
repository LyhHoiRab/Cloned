package org.wah.cloned.rongcloud.token.entity;

import org.wah.doraemon.entity.User;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

public class RongCloudToken extends Entity implements Createable, Updateable{

    private String accountId;
    private String token;
    private String appKey;
    private Date loginTime;
    private Date createTime;
    private Date updateTime;
    private User user;

    public RongCloudToken(){

    }

    public String getAccountId(){
        return accountId;
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getAppKey(){
        return appKey;
    }

    public void setAppKey(String appKey){
        this.appKey = appKey;
    }

    public Date getLoginTime(){
        return loginTime;
    }

    public void setLoginTime(Date loginTime){
        this.loginTime = loginTime;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    @Override
    public Date getCreateTime(){
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime(){
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}
