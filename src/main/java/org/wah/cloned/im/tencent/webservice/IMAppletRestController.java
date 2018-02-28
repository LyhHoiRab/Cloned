package org.wah.cloned.im.tencent.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wah.cloned.im.tencent.entity.IMApplet;
import org.wah.cloned.im.tencent.service.IMAppletService;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.security.response.Response;

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/im/tencent/applet")
public class IMAppletRestController{

    @Autowired
    private IMAppletService imAppletService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<IMApplet> save(@RequestBody IMApplet applet){
        imAppletService.save(applet);

        return new Response<IMApplet>("保存成功", applet);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<IMApplet> update(@RequestBody IMApplet applet){
        imAppletService.update(applet);

        return new Response<IMApplet>("更新成功", applet);
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<IMApplet> getById(@PathVariable("id") String id){
        IMApplet applet = imAppletService.getById(id);

        return new Response<IMApplet>("查询成功", applet);
    }

    /**
     * 根据条件查询
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<IMApplet>> find(String id, String organizationId, String appId, String privateKeyPath, String publicKeyPath){
        List<IMApplet> list = imAppletService.find(id, organizationId, appId, privateKeyPath, publicKeyPath);

        return new Response<List<IMApplet>>("查询成功", list);
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<IMApplet>> page(Long pageNum, Long pageSize, String organizationId, String appId, String privateKeyPath, String publicKeyPath){
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<IMApplet> page = imAppletService.page(pageRequest, organizationId, appId, privateKeyPath, publicKeyPath);

        return new Response<Page<IMApplet>>("查询成功", page);
    }

    /**
     * 绑定IM
     */
    @RequestMapping(value = "/binding", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response binding(String wechatId, String appletId){
        imAppletService.binding(wechatId, appletId);

        return new Response("绑定成功", null);
    }
}
