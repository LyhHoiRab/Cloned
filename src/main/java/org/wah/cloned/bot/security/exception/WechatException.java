package org.wah.cloned.bot.security.exception;

import org.wah.doraemon.security.exception.ApplicationException;

import java.text.MessageFormat;

public class WechatException extends ApplicationException{

    public WechatException(){
    }

    public WechatException(Throwable cause){
        super(cause);
    }

    public WechatException(String message, Throwable cause){
        super(message, cause);
    }

    public WechatException(String message, Object... args){
        this((String) MessageFormat.format(message, args));
    }

    public WechatException(Enum clazz, Throwable cause, Object... args){
        this((String)MessageFormat.format(clazz.toString(), args), (Throwable)cause);
    }

    public WechatException(String message){
        super(message);
    }

    public WechatException(Enum clazz, Object... args){
        super(MessageFormat.format(clazz.toString(), args));
    }
}
