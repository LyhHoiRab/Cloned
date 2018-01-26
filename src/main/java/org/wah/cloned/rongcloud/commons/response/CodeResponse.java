package org.wah.cloned.rongcloud.commons.response;

import org.wah.cloned.rongcloud.commons.security.consts.BusinessCode;

/**
 * 业务代码响应对象
 */
public class CodeResponse{

    //返回码
    public BusinessCode code;
    //错误信息
    public String errorMessage;

    public CodeResponse(){

    }

    public BusinessCode getCode(){
        return code;
    }

    public void setCode(BusinessCode code){
        this.code = code;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
