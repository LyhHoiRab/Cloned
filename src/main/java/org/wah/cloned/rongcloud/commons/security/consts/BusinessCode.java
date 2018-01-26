package org.wah.cloned.rongcloud.commons.security.consts;

import com.google.gson.annotations.SerializedName;
import org.wah.doraemon.consts.base.EnumType;
import org.wah.doraemon.security.exception.UnknownEnumTypeException;

/**
 * 融云业务返回码
 */
public enum BusinessCode implements EnumType{

    @SerializedName("200")
    SUCCESS(200, "成功"),

    @SerializedName("404")
    NOT_FOUND(404, "未找到"),

    @SerializedName("1000")
    SERVER_ERROR(1000, "服务内部错误"),

    @SerializedName("1001")
    KEY_SECRET_DISMATCH(1001, "App Secret错误"),

    @SerializedName("1002")
    PARAMS_ERROR(1002, "参数错误"),

    @SerializedName("1003")
    NO_POST_PARAMS(1003, "无POST数据"),

    @SerializedName("1004")
    SIGN_ERROR(1004, "验证签名错误"),

    @SerializedName("1005")
    PARAMS_LENGTH_LIMIT_ERROR(1005, "参数长度超限"),

    @SerializedName("1006")
    APP_ERROR(1006, "App 被锁定或删除"),

    @SerializedName("1007")
    RESTRICT_INVOKE(1007, "被限制调用"),

    @SerializedName("1008")
    INVOKE_LIMIT_ERROR(1008, "调用频率超限"),

    @SerializedName("1009")
    SERVICE_NOT_OPEN(1009, "服务未开通"),

    @SerializedName("1015")
    DELETE_DATA_NOT_FOUND(1015, "删除的数据不存在"),

    @SerializedName("1016")
    KEEP_LIVING_CHATROOM_LIMIT_ERROR(1016, "设置保活聊天室个数超限"),

    @SerializedName("1050")
    SERVER_TIMEOUT(1050, "内部服务超时"),

    @SerializedName("2007")
    TEST_USER_LIMIT_ERROR(2007, "测试用户数量超限");

    private int id;
    private String description;

    private BusinessCode(int id, String description){
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

    public static BusinessCode getById(int id){
        for(BusinessCode code : BusinessCode.values()){
            if(code.getId() == id){
                return code;
            }
        }

        throw new UnknownEnumTypeException("未知的常量ID[{0}:{1}]", BusinessCode.class, id);
    }
}
