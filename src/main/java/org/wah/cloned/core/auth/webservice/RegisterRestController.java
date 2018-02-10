package org.wah.cloned.core.auth.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.core.auth.service.RegisterService;
import org.wah.doraemon.security.response.Response;

@RestController
@RequestMapping(value = "/api/1.0/register")
public class RegisterRestController{

    @Autowired
    private RegisterService registerService;

    /**
     * 注册
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response register(String username, String password, String organizationId){
        registerService.register(username, password, organizationId);

        return new Response("注册成功", null);
    }
}
