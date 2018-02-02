package org.wah.cloned.core.wechat.entity;

import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

public class Device extends Entity implements Createable, Updateable{

    private String organizationId;
    //型号
    private String type;
    //电话号码
    private String phone;
    //IMEI
    private String imei;
    private Date createTime;
    private Date updateTime;

    public Device(){

    }

    public String getOrganizationId(){
        return organizationId;
    }

    public void setOrganizationId(String organizationId){
        this.organizationId = organizationId;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
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
