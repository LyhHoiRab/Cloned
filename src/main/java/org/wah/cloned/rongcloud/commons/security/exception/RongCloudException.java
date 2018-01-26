package org.wah.cloned.rongcloud.commons.security.exception;

import java.text.MessageFormat;

/**
 * 融云服务异常
 */
public class RongCloudException extends RuntimeException{

    public RongCloudException(){
    }

    public RongCloudException(Throwable cause){
        super(cause);
    }

    public RongCloudException(String message, Throwable cause){
        super(message, cause);
    }

    public RongCloudException(String message, Object... args){
        this(MessageFormat.format(message, args));
    }

    public RongCloudException(Enum clazz, Throwable cause, Object... args){
        this(MessageFormat.format(clazz.toString(), args), cause);
    }

    public RongCloudException(String message){
        super(message);
    }

    public RongCloudException(Enum clazz, Object... args){
        super(MessageFormat.format(clazz.toString(), args));
    }
}
