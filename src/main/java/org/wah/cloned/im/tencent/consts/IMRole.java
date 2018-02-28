package org.wah.cloned.im.tencent.consts;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wah.doraemon.consts.base.EnumType;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum IMRole implements EnumType{

    @SerializedName("0")
    SERVICE(0, "客服"),

    @SerializedName("1")
    CLIENT(1, "客户"),

    @SerializedName("2")
    WECHAT(2, "微信"),

    @SerializedName("3")
    ADMIN(3, "管理员");

    @Getter
    private int id;
    @Getter
    private String description;

    public static IMRole getById(int id){
        for(IMRole role : IMRole.values()){
            if(role.getId() == id){
                return role;
            }
        }

        throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", IMRole.class, id);
    }
}
