package org.wah.cloned.core.wechat.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.cloned.core.wechat.service.WechatService;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.security.response.Response;

@RestController
@RequestMapping(value = "/api/1.0/wechat")
public class WechatRestController{

    @Autowired
    private WechatService wechatService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Wechat> save(@RequestBody Wechat wechat){
        wechatService.save(wechat);

        return new Response<Wechat>("保存成功", wechat);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Wechat> update(@RequestBody Wechat wechat){
        wechatService.update(wechat);

        return new Response<Wechat>("更新成功", wechat);
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Wechat> getById(@PathVariable("id") String id){
        Wechat wechat = wechatService.getById(id);

        return new Response<Wechat>("查询成功", wechat);
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Wechat>> page(Long pageNum, Long pageSize, String organizationId, String wxno){
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Wechat> page = wechatService.page(pageRequest, organizationId, wxno);

        return new Response<Page<Wechat>>("查询成功", page);
    }

    /**
     * 微信网页登录
     */
    @RequestMapping(value = "/login/{wechatId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response login(@PathVariable("wechatId") String wechatId){
        wechatService.login(wechatId);

        return new Response("登录成功", null);
    }
}
