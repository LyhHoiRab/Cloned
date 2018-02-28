package org.wah.cloned.im.tencent.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class IMMessageBody extends Entity implements Createable, Updateable{

    private String fromAccount;
    private String toAccount;
    private List<IMMessage> messages;
    private Date createTime;
    private Date updateTime;
}
