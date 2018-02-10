package org.wah.cloned.core.service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

@Data
@NoArgsConstructor
public class Service extends Entity implements Createable, Updateable{

    //微信ID
    private String wechatId;
    //账户ID
    private String accountId;
    //客服名称
    private String name;
    //头像
    private String headImgUrl;
    private Date createTime;
    private Date updateTime;
}
