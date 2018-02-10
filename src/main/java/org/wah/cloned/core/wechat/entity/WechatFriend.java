package org.wah.cloned.core.wechat.entity;

import org.wah.doraemon.consts.Sex;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

public class WechatFriend extends Entity implements Createable, Updateable{

    //微信ID
    private String wechatId;
    //昵称
    private String nickname;
    //备注名称
    private String remarkname;
    //头像
    private String headImgUrl;
    //性别
    private Sex sex;
    private Date createTime;
    private Date updateTime;

    public WechatFriend(){

    }

    public String getWechatId(){
        return wechatId;
    }

    public void setWechatId(String wechatId){
        this.wechatId = wechatId;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getRemarkname(){
        return remarkname;
    }

    public void setRemarkname(String remarkname){
        this.remarkname = remarkname;
    }

    public String getHeadImgUrl(){
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl){
        this.headImgUrl = headImgUrl;
    }

    public Sex getSex(){
        return sex;
    }

    public void setSex(Sex sex){
        this.sex = sex;
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
