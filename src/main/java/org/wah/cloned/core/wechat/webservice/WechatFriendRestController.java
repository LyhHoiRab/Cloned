package org.wah.cloned.core.wechat.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wah.cloned.core.wechat.entity.WechatFriend;
import org.wah.cloned.core.wechat.service.WechatFriendService;
import org.wah.doraemon.consts.Sex;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.security.response.Response;

@RestController
@RequestMapping(value = "/api/1.0/wechatFriend")
public class WechatFriendRestController{

    @Autowired
    private WechatFriendService wechatFriendService;

    /**
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response update(@RequestBody WechatFriend friend){
        wechatFriendService.update(friend);

        return new Response("更新成功", null);
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<WechatFriend> getById(@PathVariable("id") String id){
        WechatFriend friend = wechatFriendService.getById(id);

        return new Response<WechatFriend>("查询成功", friend);
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<WechatFriend>> page(Long pageNum, Long pageSize, String organizationId, String wechatId, String wxno, String serviceName, String nickname, String remarkname, Sex sex){
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<WechatFriend> page = wechatFriendService.page(pageRequest, organizationId, wechatId, wxno, serviceName, nickname, remarkname, sex);

        return new Response<Page<WechatFriend>>("查询成功", page);
    }
}
