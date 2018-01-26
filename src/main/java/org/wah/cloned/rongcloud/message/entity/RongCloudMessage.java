package org.wah.cloned.rongcloud.message.entity;

import org.wah.cloned.rongcloud.commons.security.consts.MessageType;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;
import java.util.Map;

public class RongCloudMessage extends Entity implements Createable, Updateable{

    private String fromUserId;
    private String toUserId;
    private Map<String, Object> content;
    private MessageType objectName;
    private Date sendTime;
    private Date receiveTime;
    private Date createTime;
    private Date updateTime;

    public RongCloudMessage(){

    }

    public String getFromUserId(){
        return fromUserId;
    }

    public void setFromUserId(String fromUserId){
        this.fromUserId = fromUserId;
    }

    public String getToUserId(){
        return toUserId;
    }

    public void setToUserId(String toUserId){
        this.toUserId = toUserId;
    }

    public Map<String, Object> getContent(){
        return content;
    }

    public void setContent(Map<String, Object> content){
        this.content = content;
    }

    public MessageType getObjectName(){
        return objectName;
    }

    public void setObjectName(MessageType objectName){
        this.objectName = objectName;
    }

    public Date getSendTime(){
        return sendTime;
    }

    public void setSendTime(Date sendTime){
        this.sendTime = sendTime;
    }

    public Date getReceiveTime(){
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime){
        this.receiveTime = receiveTime;
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
