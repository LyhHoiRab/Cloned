package org.wah.cloned.core.wechat.entity;

import org.wah.cloned.core.wechat.consts.WechatStatus;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

public class Wechat extends Entity implements Createable, Updateable{

    private String wxno;
    private String organizationId;
    private String deviceId;
    private WechatStatus status;
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

    public String getDeviceId(){
        return deviceId;
    }

    public void setDeviceId(String deviceId){
        this.deviceId = deviceId;
    }

    public WechatStatus getStatus(){
        return status;
    }

    public void setStatus(WechatStatus status){
        this.status = status;
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
