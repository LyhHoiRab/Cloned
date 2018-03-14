package org.wah.cloned.bot.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.bot.service.WechatBotService;
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
}
