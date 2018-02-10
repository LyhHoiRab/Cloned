package org.wah.cloned.core.wechat.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wah.cloned.core.wechat.entity.Device;
import org.wah.cloned.core.wechat.service.DeviceService;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.security.response.Response;

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/device")
public class DeviceRestController{

    @Autowired
    private DeviceService deviceService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Device> save(@RequestBody Device device){
        deviceService.save(device);

        return new Response<Device>("保存成功", device);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Device> update(@RequestBody Device device){
        deviceService.update(device);

        return new Response<Device>("更新成功", device);
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Device> getById(@PathVariable("id") String id){
        Device device = deviceService.getById(id);

        return new Response<Device>("查询成功", device);
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Device>> page(Long pageNum, Long pageSize, String organizationId, String type, String phone, String imei){
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Device> page = deviceService.page(pageRequest, organizationId, type, phone, imei);

        return new Response<Page<Device>>("查询成功", page);
    }

    /**
     * 查询
     */
    @RequestMapping(value =  "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Device>> find(String organizationId, String type, String phone, String imei){
        List<Device> list = deviceService.find(organizationId, type, phone, imei);

        return new Response<List<Device>>("查询成功", list);
    }
}
