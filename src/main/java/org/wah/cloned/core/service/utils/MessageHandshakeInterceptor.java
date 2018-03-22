package org.wah.cloned.core.service.utils;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

public class MessageHandshakeInterceptor extends HttpSessionHandshakeInterceptor{

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception{
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        Enumeration<String> names = servletRequest.getParameterNames();

        while(names.hasMoreElements()){
            String name = names.nextElement();
            attributes.put(name, servletRequest.getParameter(name));
        }

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex){
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
