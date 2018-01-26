package org.wah.cloned.rongcloud.chat.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.cloned.commons.security.consts.SessionParamName;
import org.wah.cloned.rongcloud.chat.service.RongCloudChatService;
import org.wah.cloned.rongcloud.token.entity.RongCloudToken;
import org.wah.doraemon.security.exception.ApplicationException;
import org.wah.doraemon.security.response.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/1.0/rongcloud/chat")
public class RongCloudChatRestController{

    private Logger logger = LoggerFactory.getLogger(RongCloudChatRestController.class);

    @Autowired
    private RongCloudChatService rongCloudChatService;

    /**
     * 查询用户Token
     */
    @RequestMapping(value = "/token", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<RongCloudToken> getToken(HttpServletRequest request, HttpServletResponse response){
        try{
            String accountId = (String) request.getSession().getAttribute(SessionParamName.ACCOUNT_ID);

            return new Response<RongCloudToken>("查询成功", rongCloudChatService.getToken(accountId));
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
