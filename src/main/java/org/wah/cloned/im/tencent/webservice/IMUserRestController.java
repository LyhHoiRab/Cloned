package org.wah.cloned.im.tencent.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.im.tencent.entity.IMUser;
import org.wah.cloned.im.tencent.service.IMUserService;
import org.wah.doraemon.security.response.Response;

@RestController
@RequestMapping(value = "/api/1.0/im/user")
public class IMUserRestController{

    @Autowired
    private IMUserService imUserService;

    /**
     * 保存管理员
     */
    @RequestMapping(value = "/admin", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public Response<IMUser> saveAdmin(String identifier, String appletId){
        IMUser user = imUserService.saveAdmin(identifier, appletId);

        return new Response<IMUser>("保存成功", user);
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public Response<IMUser> getById(@PathVariable("id") String id){
        IMUser user = imUserService.getById(id);

        return new Response<IMUser>("查询成功", user);
    }

    /**
     * 根据应用ID查询管理员
     */
    @RequestMapping(value = "/admin/{appletId}")
    public Response<IMUser> getAdminByAppletId(@PathVariable("appletId") String appletId){
        IMUser user = imUserService.getAdminByAppletId(appletId);

        return new Response<IMUser>("查询成功", user);
    }
}
