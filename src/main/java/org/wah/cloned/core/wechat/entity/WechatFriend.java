package org.wah.cloned.core.wechat.entity;

import lombok.Getter;
import lombok.Setter;
import org.wah.doraemon.consts.Sex;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

@Getter
@Setter
public class WechatFriend extends Entity implements Createable, Updateable{

    //微信ID
    private String wechatId;
    //微信信息
    private Wechat wechat;
    //昵称
    private String nickname;
    //微信号
    private String wxno;
    //备注名称
    private String remarkname;
    //头像
    private String headImgUrl;
    //分配的客服
    private String serviceId;
    //性别
    private Sex sex;
    private Date createTime;
    private Date updateTime;

    public WechatFriend(){

    }
}
