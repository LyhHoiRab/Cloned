package org.wah.cloned.core.auth.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.commons.security.consts.SessionParamName;
import org.wah.cloned.core.auth.service.LoginService;
import org.wah.doraemon.security.response.Response;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/1.0/login")
public class LoginRestController{

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response login(HttpServletRequest request, String username, String password){
        String accountId = loginService.login(username, password);
        request.getSession().setAttribute(SessionParamName.ACCOUNT_ID, accountId);

        return new Response("登录成功", null);
    }
}
