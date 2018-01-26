package org.wah.cloned.rongcloud.message.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.rongcloud.message.entity.RongCloudMessage;
import org.wah.cloned.rongcloud.message.service.RongCloudMessageService;
import org.wah.doraemon.security.exception.ApplicationException;
import org.wah.doraemon.security.response.Response;

@RestController
@RequestMapping(value = "/api/1.0/rongcloud/message")
public class RongCloudMessageRestController{

    private Logger logger = LoggerFactory.getLogger(RongCloudMessageRestController.class);

    @Autowired
    private RongCloudMessageService rongCloudMessageService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<RongCloudMessage> save(@RequestBody RongCloudMessage message){
        try{
            rongCloudMessageService.save(message);

            return new Response<RongCloudMessage>("添加成功", message);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<RongCloudMessage> update(@RequestBody RongCloudMessage message){
        try{
            rongCloudMessageService.update(message);

            return new Response<RongCloudMessage>("修改成功", message);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
