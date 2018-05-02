package org.wah.cloned.bot.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.bot.service.WechatBotService;
import org.wah.cloned.core.wechat.entity.Message;
import org.wah.doraemon.security.response.Response;

@RestController
@RequestMapping(value = "/api/1.0/wechatBot")
public class WechatBotRestController{

    @Autowired
    private WechatBotService wechatBotService;

    /**
     * 微信登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response login(String id){
        wechatBotService.login(id);

        return new Response("登录成功", null);
    }

    /**
     * 金额消息回调
     */
    @RequestMapping(value = "/amount/callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response amountCallback(@RequestBody Message message){
        wechatBotService.amountCallback(message);

        return new Response("回调成功", null);
    }

    /**
     * 获取微信号回调
     */
    @RequestMapping(value = "/wxno/callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getWxnoCallback(@RequestBody Message message){
        wechatBotService.getWxnoCallback(message);

        return new Response("回调成功", null);
    }

    /**
     * 重置微信机器人
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response reset(String wechatId){
        wechatBotService.reset(wechatId);

        return new Response("重置成功", null);
    }

    /**
     * 关闭socket
     */
    @RequestMapping(value = "/close/socket", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response closeSocket(String wechatId){
        wechatBotService.closeSocket(wechatId);

        return new Response("关闭成功", null);
    }
}
