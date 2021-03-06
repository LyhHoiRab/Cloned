package org.wah.cloned.im.tencent.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.wah.cloned.im.tencent.consts.IMRole;
import org.wah.cloned.im.tencent.consts.IMType;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

@Data
@NoArgsConstructor
public class IMUser extends Entity implements Createable, Updateable{

    private String name;
    private String nickname;
    private String headImgUrl;
    private String sig;
    private String appletId;
    private String appId;
    private IMType type;
    private IMRole role;
    private Date createTime;
    private Date updateTime;
}
