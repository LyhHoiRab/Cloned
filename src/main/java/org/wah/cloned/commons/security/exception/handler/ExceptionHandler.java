package org.wah.cloned.commons.security.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wah.doraemon.security.exception.ApplicationException;
import org.wah.doraemon.security.exception.NotLoginStateException;
import org.wah.doraemon.security.response.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandler{

    private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * 未登录异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = NotLoginStateException.class)
    public void notLoginStateException(HttpServletRequest request, HttpServletResponse response){
        try{
            String url = request.getRequestURI().substring(request.getContextPath().length());

            if(url.contains("cloned")){
                response.sendRedirect("/page/cloned/auth/login");
            }else if(url.contains("rongcloud")){
                response.sendRedirect("/page/rongcloud/auth/login");
            }
        }catch(Exception e){
            //忽略
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 应用异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ApplicationException.class)
    @ResponseBody
    public Response applicationException(ApplicationException e){
        logger.error(e.getMessage(), e);

        Response response = new Response();
        response.setSuccess(false);
        response.setMsg(e.getMessage());
        return response;
    }
}
