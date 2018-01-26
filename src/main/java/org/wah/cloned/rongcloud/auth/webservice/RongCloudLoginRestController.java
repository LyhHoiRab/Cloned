package org.wah.cloned.rongcloud.auth.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.commons.security.consts.SessionParamName;
import org.wah.cloned.rongcloud.auth.service.RongCloudLoginService;
import org.wah.cloned.rongcloud.token.entity.RongCloudToken;
import org.wah.doraemon.security.exception.ApplicationException;
import org.wah.doraemon.security.response.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/1.0/rongcloud/auth/login")
public class RongCloudLoginRestController{

    private Logger logger = LoggerFactory.getLogger(RongCloudLoginRestController.class);

    @Autowired
    private RongCloudLoginService rongCloudLoginService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response login(HttpServletRequest request, HttpServletResponse response, String keyword, String password){
        try{
            RongCloudToken token = rongCloudLoginService.login(keyword, password);

            request.getSession().setAttribute(SessionParamName.ACCOUNT_ID, token.getAccountId());
//            request.getSession().setAttribute(SessionParamName.RONG_CLOUD_TOKEN, token.getToken());
//            request.getRequestDispatcher("/page/rongcloud/chat").forward(request, response);

            return new Response("登录成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
