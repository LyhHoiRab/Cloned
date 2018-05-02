package org.wah.cloned.core.service.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wah.cloned.core.service.entity.Service;
import org.wah.cloned.core.service.service.ServiceService;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.security.response.Response;

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/service")
public class ServiceRestController{

    @Autowired
    private ServiceService serviceService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Service> save(@RequestBody Service service){
        serviceService.save(service);

        return new Response<Service>("保存成功", service);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Service> update(@RequestBody Service service){
        serviceService.update(service);

        return new Response<Service>("更新成功", service);
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Service> getById(@PathVariable("id") String id){
        Service service = serviceService.getById(id);

        return new Response<Service>("查询成功", service);
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Service>> page(Long pageNum, Long pageSize, String organizationId, String wxno, String wechatId, String name){
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Service> page = serviceService.page(pageRequest, organizationId, wxno, wechatId, name);

        return new Response<Page<Service>>("查询成功", page);
    }

    /**
     * 根据IM应用ID查询
     */
    @RequestMapping(value = "/find/applet/{imAppletId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Service>> findByIMAppletId(@PathVariable("imAppletId") String imAppletId){
        List<Service> list = serviceService.findByIMAppletId(imAppletId);

        return new Response<List<Service>>("查询成功", list);
    }
}
