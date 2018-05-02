package org.wah.cloned.core.wechat.entity;

import lombok.Getter;
import lombok.Setter;
import org.wah.cloned.core.service.entity.Service;
import org.wah.cloned.core.wechat.consts.MessageType;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

@Getter
@Setter
public class Message extends Entity implements Createable, Updateable{

    private String wechatId;
    private Service service;
    private WechatFriend friend;
    private String nickname;
    private String remarkname;
    private String headImgUrl;
    private String text;
    private MessageType type;
    private Boolean sendByService;
    private Date createTime;
    private Date updateTime;
}
