package org.wah.cloned.im.tencent.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wah.cloned.im.tencent.consts.IMType;
import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

@Data
@NoArgsConstructor
public class IMUser extends Entity implements Createable, Updateable{

    @SerializedName("Identifier")
    private String name;
    @SerializedName("Nick")
    private String nickname;
    @SerializedName("FaceUrl")
    private String headImgUrl;
    @SerializedName("Type")
    private IMType type;
    private Date createTime;
    private Date updateTime;
}
