package org.wah.cloned.rongcloud.user.entity;

import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

public class Friendship extends Entity implements Createable, Updateable{

    private RongCloudUser master;
    private RongCloudUser subordinate;
    private Date createTime;
    private Date updateTime;

    public Friendship(){

    }

    public RongCloudUser getMaster(){
        return master;
    }

    public void setMaster(RongCloudUser master){
        this.master = master;
    }

    public RongCloudUser getSubordinate(){
        return subordinate;
    }

    public void setSubordinate(RongCloudUser subordinate){
        this.subordinate = subordinate;
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
