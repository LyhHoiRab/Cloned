package org.wah.cloned.core.wechat.consts;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MessageType{

    @SerializedName("0")
    TEXT(0, "文本"),

    @SerializedName("1")
    IMAGE(1, "图片"),

    @SerializedName("2")
    VOICE(2, "语音"),

    @SerializedName("3")
    RED_PACKET(3, "红包"),

    @SerializedName("4")
    EMOTICONS(4, "表情"),

    @SerializedName("5")
    SYSTEM(5, "系统"),

    @SerializedName("6")
    TRANSFER(6, "转账"),

    @SerializedName("7")
    VIDEO(7, "视频");

    @Getter
    private int id;
    @Getter
    private String description;

    public static MessageType getById(int id){
        for(MessageType type : MessageType.values()){
            if(type.getId() == id){
                return type;
            }
        }

        throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", MessageType.class, id);
    }
}
