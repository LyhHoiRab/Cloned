package org.wah.cloned.im.tencent.consts;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wah.doraemon.consts.base.EnumType;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum IMType implements EnumType{

    GENERAL(0, "普通账号"),
    BOT(1, "机器人");

    @Getter
    private int id;
    @Getter
    private String description;

    public static IMType getById(int id){
        for(IMType type : IMType.values()){
            if(type.getId() == id){
                return type;
            }
        }

        throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", IMType.class, id);
    }
}
