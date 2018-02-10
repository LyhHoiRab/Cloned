package org.wah.cloned.core.account.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.core.account.service.UserService;
import org.wah.doraemon.entity.User;
import org.wah.doraemon.security.response.Response;

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/user")
public class UserRestController{

    @Autowired
    private UserService userService;

    /**
     * 根据微信ID查询为客服的用户信息
     */
    @RequestMapping(value = "/service/{wechatId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<User>> findIsServiceByWechatId(@PathVariable("wechatId") String wechatId){
        List<User> list = userService.findIsServiceByWechatId(wechatId);

        return new Response<List<User>>("查询成功", list);
    }

    /**
     * 根据微信ID查询没有添加客服的用户信息
     */
    @RequestMapping(value = "/notService/{wechatId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<User>> findIsNotServiceByWechatId(@PathVariable("wechatId") String wechatId){
        List<User> list = userService.findIsNotServiceByWechatId(wechatId);

        return new Response<List<User>>("查询成功", list);
    }
}
