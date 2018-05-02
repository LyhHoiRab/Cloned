package org.wah.cloned.core.wechat.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.core.wechat.entity.Message;
import org.wah.cloned.core.wechat.service.MessageService;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.security.response.Response;

@RestController
@RequestMapping(value = "/api/1.0/message")
public class MessageRestController{

    @Autowired
    private MessageService messageService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Message>> page(Long pageNum, Long pageSize, String remarkname, String serviceName, String nickname){
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Message> page = messageService.page(pageRequest, remarkname, serviceName, nickname);

        return new Response<Page<Message>>("查询成功", page);
    }
}
