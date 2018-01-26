package org.wah.cloned.rongcloud.commons.security.consts;

import com.google.gson.annotations.SerializedName;
import org.wah.doraemon.consts.base.EnumType;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;

public enum MessageType implements EnumType{

    @SerializedName("RC:TxtMsg")
    TXT(0, "文本消息"),

    @SerializedName("RC:ImgMsg")
    IMG(1, "图片消息"),

    @SerializedName("RC:VcMsg")
    VC(2, "语音消息"),

    @SerializedName("RC:ImgTextMsg")
    IMG_TEXT(3, "图文消息"),

    @SerializedName("RC:FileMsg")
    FILE(4, "文件消息"),

    @SerializedName("RC:ContactNtf")
    CONTACT_NTF(5, "添加联系人消息"),

    @SerializedName("RC:InfoNtf")
    INFO_NTF(6, "提示条(小灰条)通知消息"),

    @SerializedName("RC:ProfileNtf")
    PROFILE_NTF(7, "资料通知消息"),

    @SerializedName("RC:GrpNtf")
    GRP_NTF(8, "群组通知消息"),

    @SerializedName("RC:DizNtf")
    DIZ_NTF(9, "讨论组通知消息"),

    @SerializedName("RC:CmdNtf")
    CMD_NTF(10, "通用命令通知消息"),

    @SerializedName("RC:CmdMsg")
    CMD(11, "命令消息"),

    @SerializedName("RC:LBSMsg")
    LBS(12, "位置消息");

    private int id;
    private String description;

    private MessageType(int id, String description){
        this.id = id;
        this.description = description;
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public String getDescription(){
        return description;
    }

    public static MessageType getById(int id){
        for(MessageType type : MessageType.values()){
            if(type.getId() == id){
                return type;
            }
        }

        throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", MessageType.class, id);
    }
}
