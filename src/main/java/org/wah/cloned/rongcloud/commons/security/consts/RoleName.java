package org.wah.cloned.rongcloud.commons.security.consts;


import com.google.gson.annotations.SerializedName;
import org.wah.doraemon.consts.base.EnumType;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;

public enum RoleName implements EnumType{

    @SerializedName("0")
    SERVICE(0, "客服"),

    @SerializedName("1")
    CUSTOMER(1, "客户");

    private int id;
    private String description;

    private RoleName(int id, String description){
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

    public static RoleName getById(int id){
        for(RoleName name : RoleName.values()){
            if(name.getId() == id){
                return name;
            }
        }

        throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", RoleName.class, id);
    }
}
