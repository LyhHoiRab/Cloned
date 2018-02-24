package org.wah.cloned.bot.annotation;

import org.wah.cloned.bot.enums.AccountType;
import org.wah.cloned.bot.enums.MsgType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bind{

    /**
     * 接受聊天的消息类型
     *
     * @return
     */
    MsgType[] msgType() default {MsgType.TEXT};

    /**
     * 接受聊天的账户类型
     *
     * @return
     */
    AccountType[] accountType() default {AccountType.TYPE_FRIEND, AccountType.TYPE_GROUP};
}
