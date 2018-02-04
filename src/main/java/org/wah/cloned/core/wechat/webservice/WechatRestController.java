package org.wah.cloned.core.wechat.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wah.cloned.core.wechat.entity.Wechat;
import org.wah.cloned.core.wechat.service.WechatService;
import org.wah.doraemon.security.exception.ApplicationException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.security.response.Response;

@RestController
@RequestMapping(value = "/api/1.0/wechat")
public class WechatRestController{

    private Logger logger = LoggerFactory.getLogger(WechatRestController.class);

    @Autowired
    private WechatService wechatService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Wechat> save(@RequestBody Wechat wechat){
        try{
            wechatService.save(wechat);

            return new Response<Wechat>("保存成功", wechat);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Wechat> update(@RequestBody Wechat wechat){
        try{
            wechatService.update(wechat);

            return new Response<Wechat>("更新成功", wechat);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Wechat> getById(@PathVariable("id") String id){
        try{
            Wechat wechat = wechatService.getById(id);

            return new Response<Wechat>("查询成功", wechat);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Wechat>> page(Long pageNum, Long pageSize, String organizationId, String wxno){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Wechat> page = wechatService.page(pageRequest, organizationId, wxno);

            return new Response<Page<Wechat>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
