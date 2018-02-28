package org.wah.cloned.im.tencent.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

@Data
@NoArgsConstructor
public class IMApplet extends Entity implements Createable, Updateable{

    private String organizationId;
    private String appId;
    private String privateKeyPath;
    private String publicKeyPath;
    private Date createTime;
    private Date updateTime;
}
