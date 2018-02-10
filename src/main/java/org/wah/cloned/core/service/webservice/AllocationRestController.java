package org.wah.cloned.core.service.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.core.service.entity.Allocation;
import org.wah.cloned.core.service.service.AllocationService;
import org.wah.doraemon.security.response.Response;

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/allocation")
public class AllocationRestController{

    @Autowired
    private AllocationService allocationService;

    /**
     * 根据微信ID查询
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Allocation>> find(String wechatId, String name){
        List<Allocation> list = allocationService.find(wechatId, name);

        return new Response<List<Allocation>>("查询成功", list);
    }
}
