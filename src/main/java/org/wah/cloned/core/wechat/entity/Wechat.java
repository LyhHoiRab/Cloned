package org.wah.cloned.core.wechat.entity;

import org.wah.cloned.core.service.entity.Service;
import org.wah.cloned.core.wechat.consts.AppStatus;
import org.wah.cloned.core.wechat.consts.WechatStatus;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;
import java.util.List;

public class Wechat extends Entity implements Createable, Updateable{

    private String wxno;
    private String organizationId;
    private WechatStatus status;
    private String phone;
    private String imei;
    private AppStatus appStatus;
    private Date lastCheckTime;
    private Date createTime;
    private Date updateTime;

    public Wechat(){

    }

    public String getWxno(){
        return wxno;
    }

    public void setWxno(String wxno){
        this.wxno = wxno;
    }

    public String getOrganizationId(){
        return organizationId;
    }

    public void setOrganizationId(String organizationId){
        this.organizationId = organizationId;
    }

    public WechatStatus getStatus(){
        return status;
    }

    public void setStatus(WechatStatus status){
        this.status = status;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getImei(){
        return imei;
    }

    public void setImei(String imei){
        this.imei = imei;
    }

    public AppStatus getAppStatus(){
        return appStatus;
    }

    public void setAppStatus(AppStatus appStatus){
        this.appStatus = appStatus;
    }

    public Date getLastCheckTime(){
        return lastCheckTime;
    }

    public void setLastCheckTime(Date lastCheckTime){
        this.lastCheckTime = lastCheckTime;
    }

    @Override
    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime(){
        return updateTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}
