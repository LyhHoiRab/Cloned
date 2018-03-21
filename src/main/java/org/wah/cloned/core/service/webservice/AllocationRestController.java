package org.wah.cloned.core.service.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response update(@RequestBody List<Allocation> list){
        allocationService.update(list);

        return new Response("更新成功", null);
    }

    /**
     * 设置默认概率
     */
    @RequestMapping(value = "/default/probability/{wechatId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response setDefaultProbability(@PathVariable("wechatId") String wechatId){
        allocationService.setDefaultProbability(wechatId);

        return new Response("更新成功", null);
    }

    /**
     * 更新概率
     */
    @RequestMapping(value = "/update/{wechatId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateByNow(@PathVariable("wechatId") String wechatId){
        allocationService.saveOrUpdatePool(wechatId);

        return new Response("更新成功", null);
    }

    /**
     * 根据微信ID查询
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Allocation>> find(String wechatId, String name){
        List<Allocation> list = allocationService.find(wechatId, name);

        return new Response<List<Allocation>>("查询成功", list);
    }
}
