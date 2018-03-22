package org.wah.cloned.core.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Service extends Entity implements Createable, Updateable{

    //账号
    private String username;
    //密码
    private String password;
    //微信ID
    private String wechatId;
    //客服名称
    private String name;
    //头像
    private String headImgUrl;
    private Date createTime;
    private Date updateTime;
}
