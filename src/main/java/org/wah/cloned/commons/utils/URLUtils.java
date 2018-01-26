package org.wah.cloned.commons.utils;

import org.wah.doraemon.security.exception.UtilsException;

import javax.servlet.http.HttpServletRequest;

public class URLUtils{

    public URLUtils(){

    }

    /**
     * 查询请求全路径
     */
    public static String getRequestPath(HttpServletRequest request){
        if(request == null){
            throw new UtilsException("请求信息不能为空");
        }

        //端口
        int port = request.getServerPort();

        return request.getScheme() + "://" + request.getServerName() + (port == 80 ? "" : ":" + port) + request.getContextPath();
    }
}
