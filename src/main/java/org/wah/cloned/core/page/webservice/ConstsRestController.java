package org.wah.cloned.core.page.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.core.page.service.ConstsService;
import org.wah.doraemon.security.response.Response;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/1.0/consts")
public class ConstsRestController{

    @Autowired
    private ConstsService constsService;

    @RequestMapping(value = "/wechatStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<Object, Object>> wechatStatus(){
        Map<Object, Object> result = constsService.wechatStatus();

        return new Response<Map<Object, Object>>("查询成功", result);
    }

    @RequestMapping(value = "/appStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<Object, Object>> appStatus(){
        Map<Object, Object> result = constsService.appStatus();

        return new Response<Map<Object, Object>>("查询成功", result);
    }

    @RequestMapping(value = "/sex", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<Object, Object>> sex(){
        Map<Object, Object> result = constsService.sex();

        return new Response<Map<Object, Object>>("查询成功", result);
    }

    @RequestMapping(value = "/messageType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<Object, Object>> messageType(){
        Map<Object, Object> result = constsService.messageType();

        return new Response<Map<Object, Object>>("查询成功", result);
    }
}