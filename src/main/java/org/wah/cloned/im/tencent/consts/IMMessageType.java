package org.wah.cloned.im.tencent.consts;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wah.doraemon.consts.base.EnumType;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;

/**
 * 通过服务端集成的Rest API接口，只能发送
 * TIMTextElem
 * TIMLocationElem
 * TIMLocationElem
 * TIMCustomElem
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum IMMessageType implements EnumType{

    @SerializedName("TIMTextElem")
    TEXT(0, "文本消息"),

    @SerializedName("TIMLocationElem")
    LOCATION(1, "地理位置消息"),

    @SerializedName("TIMFaceElem")
    FACE(2, "表情消息"),

    @SerializedName("TIMCustomElem")
    CUSTOM(3, "自定义消息"),

    @SerializedName("TIMSoundElem")
    SOUND(4, "语音消息"),

    @SerializedName("TIMImageElem")
    IMAGE(5, "图像消息"),

    @SerializedName("TIMFileElem")
    FILE(6, "文件消息");

    @Getter
    private int id;
    @Getter
    private String description;

    public static IMMessageType getById(int id){
        for(IMMessageType type : IMMessageType.values()){
            if(type.getId() == id){
                return type;
            }
        }

        throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", IMMessageType.class, id);
    }
}
