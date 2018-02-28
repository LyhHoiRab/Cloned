package org.wah.cloned.im.tencent.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wah.cloned.im.tencent.consts.IMMessageType;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
public class IMMessage extends Entity implements Createable, Updateable{

    @SerializedName("MsgType")
    private IMMessageType type;
    @SerializedName("MsgContent")
    private Map<String, String> content;
    private Date createTime;
    private Date updateTime;
}
