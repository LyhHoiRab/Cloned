package org.wah.cloned.rongcloud.user.entity;

import org.wah.cloned.rongcloud.commons.security.consts.RoleName;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

public class RongCloudUser extends Entity implements Createable, Updateable{

    private String name;
    private String userId;
    private String portraitUri;
    private String token;
    private String appKey;
    private String wechatId;
    private RoleName roleName;
    private Date loginTime;
    private Date createTime;
    private Date updateTime;

    public RongCloudUser(){

    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getPortraitUri(){
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri){
        this.portraitUri = portraitUri;
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

    public String getWechatId(){
        return wechatId;
    }

    public void setWechatId(String wechatId){
        this.wechatId = wechatId;
    }

    public RoleName getRoleName(){
        return roleName;
    }

    public void setRoleName(RoleName roleName){
        this.roleName = roleName;
    }

    public Date getLoginTime(){
        return loginTime;
    }

    public void setLoginTime(Date loginTime){
        this.loginTime = loginTime;
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
