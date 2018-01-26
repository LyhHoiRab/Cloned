package org.wah.cloned.rongcloud.commons.security.consts;

import com.google.gson.annotations.SerializedName;
import org.wah.doraemon.consts.base.EnumType;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;

public enum OnlineStatus implements EnumType{

    @SerializedName("0")
    OFFLINE(0, "离线"),

    @SerializedName("1")
    ONLINE(1, "在线");

    private int id;
    private String description;

    private OnlineStatus(int id, String description){
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

    public static OnlineStatus getById(int id){
        for(OnlineStatus status : OnlineStatus.values()){
            if(status.getId() == id){
                return status;
            }
        }

        throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", OnlineStatus.class, id);
    }
}
