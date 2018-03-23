package org.wah.cloned.commons.security.exception.handler;

import io.github.biezhi.wechat.exception.WeChatException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wah.doraemon.security.exception.*;
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

            if(url.contains("cloned") || url.equals("/")){
                response.sendRedirect("/page/cloned/login");
            }
        }catch(Exception e){
            //忽略
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 用户信息未获异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = UserNotFoundException.class)
    public void userNotFoundException(HttpServletRequest request, HttpServletResponse response){
        try{
            String url = request.getRequestURI().substring(request.getContextPath().length());

            if(url.contains("cloned")){
                response.sendRedirect("/page/cloned/userInfo");
            }
        }catch(Exception e){
            //忽略
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 账户信息未获异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AccountNotFoundException.class)
    @ResponseBody
    public Response accountNotFoundException(AccountNotFoundException e){
        logger.error(e.getMessage(), e);

        Response response = new Response();
        response.setSuccess(false);
        response.setMsg(e.getMessage());
        return response;
    }

    /**
     * 微信机器人异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = WeChatException.class)
    @ResponseBody
    public Response wechatException(WeChatException e){
        logger.error(e.getMessage(), e);

        Response response = new Response();
        response.setMsg(e.getMessage());
        response.setSuccess(false);
        response.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        return response;
    }

    /**
     * 应用异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {DataAccessException.class, IllegalArgumentException.class, UtilsException.class})
    @ResponseBody
    public Response applicationException(Exception e){
        logger.error(e.getMessage(), e);

        Response response = new Response();
        response.setMsg(e.getMessage());
        response.setSuccess(false);
        response.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        return response;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response exception(Exception e){
        logger.error(e.getMessage(), e);

        Response response = new Response();
        response.setMsg(e.getMessage());
        response.setSuccess(false);
        response.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        return response;
    }
}
