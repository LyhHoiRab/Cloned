package org.wah.cloned.im.tencent.consts;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wah.doraemon.consts.base.EnumType;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Operation implements EnumType{

    @SerializedName("AppName")
    APP_NAME(0, "应用名称"),

    @SerializedName("AppId")
    APP_ID(1, "应用AppId"),

    @SerializedName("Company")
    COMPANY(2, "所属客户名称"),

    @SerializedName("ActiveUserNum")
    ACTIVE_USER_NUM(3, "活跃用户数"),

    @SerializedName("RegistUserNumOneDay")
    REGIST_USER_NUM_ONE_DAY(4, "新增注册人数"),

    @SerializedName("RegistUserNumTotal")
    REGIST_USER_NUM_TOTAL(5, "累计注册人数"),

    @SerializedName("LoginTimes")
    LOGIN_TIMES(6, "登录次数"),

    @SerializedName("LoginUserNum")
    LOGIN_USER_NUM(7, "登录人数"),

    @SerializedName("SendMsgUserNum")
    SEND_MSG_USER_NUM(8, "发消息人数"),

    @SerializedName("C2CSendMsgUserNum")
    C2C_SEND_MSG_USER_NUM(9, "发消息人数"),

    @SerializedName("MaxOnlineNum")
    MAX_ONLINE_NUM(10, "最高在线人数");

    @Getter
    private int id;
    @Getter
    private String description;

    public static Operation getById(int id){
        for(Operation operation : Operation.values()){
            if(operation.getId() == id){
                return operation;
            }
        }

        throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", Operation.class, id);
    }
}
