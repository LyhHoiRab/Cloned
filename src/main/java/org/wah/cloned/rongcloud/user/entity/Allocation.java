package org.wah.cloned.rongcloud.user.entity;

import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

public class Allocation extends Entity implements Createable, Updateable{

    private String rongCloudUserId;
    private Double probability;
    private Double defaultProbability;
    private Date createTime;
    private Date updateTime;

    public Allocation(){

    }

    public String getRongCloudUserId(){
        return rongCloudUserId;
    }

    public void setRongCloudUserId(String rongCloudUserId){
        this.rongCloudUserId = rongCloudUserId;
    }

    public Double getProbability(){
        return probability;
    }

    public void setProbability(Double probability){
        this.probability = probability;
    }

    public Double getDefaultProbability(){
        return defaultProbability;
    }

    public void setDefaultProbability(Double defaultProbability){
        this.defaultProbability = defaultProbability;
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
