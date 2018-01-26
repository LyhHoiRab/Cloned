package org.wah.cloned.core.auth.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.core.auth.controller.RegisterController;
import org.wah.cloned.core.auth.service.RegisterService;
import org.wah.doraemon.entity.Account;
import org.wah.doraemon.security.exception.ApplicationException;
import org.wah.doraemon.security.response.Response;

@RestController
@RequestMapping(value = "/api/1.0/cloned/auth/register")
public class RegisterRestController{

    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;

    /**
     * 注册
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response register(@RequestBody Account account){
        try{
            registerService.register(account);

            return new Response("注册成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
