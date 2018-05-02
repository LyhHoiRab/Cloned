package org.wah.cloned.core.wechat.consts;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wah.doraemon.consts.base.EnumType;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum WechatStatus implements EnumType{

    @SerializedName(value = "0")
    OFFLINE(0, "离线"),

    @SerializedName(value = "1")
    ONLINE(1, "在线"),

    @SerializedName("2")
    UNUSUAL(2, "异常"),

    @SerializedName("3")
    LOGGING(3, "登录中");

    @Getter
    private int id;
    @Getter
    private String description;

    public static WechatStatus getById(int id){
        for(WechatStatus status : WechatStatus.values()){
            if(status.getId() == id){
                return status;
            }
        }

        throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", WechatStatus.class, id);
    }
}
