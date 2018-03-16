package org.wah.cloned.core.wechat.entity;

import lombok.Data;
import org.wah.cloned.core.wechat.consts.MessageType;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

@Data
public class Message extends Entity implements Createable, Updateable{

    private String wechatId;
    private String remarkname;
    private String serviceId;
    private String nickname;
    private String headImgUrl;
    private String text;
    private MessageType type;
    private Boolean sendByService;
    private Date createTime;
    private Date updateTime;
}
