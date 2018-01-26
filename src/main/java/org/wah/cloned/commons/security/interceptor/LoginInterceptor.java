package org.wah.cloned.commons.security.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.wah.cloned.commons.security.consts.SessionParamName;
import org.wah.doraemon.security.exception.NotLoginStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginInterceptor extends HandlerInterceptorAdapter{

    private static final AntPathMatcher matcher = new AntPathMatcher();
    private List<String> excludes;

    public List<String> getExcludes(){
        return excludes;
    }

    public void setExcludes(List<String> excludes){
        this.excludes = excludes;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String url = request.getRequestURI().substring(request.getContextPath().length());

        if(excludes != null && !excludes.isEmpty()){
            for(String exclude : excludes){
                if(matcher.match(exclude, url)){
                    return true;
                }
            }
        }

        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute(SessionParamName.ACCOUNT_ID);

        //没有登录
        if(StringUtils.isBlank(accountId)){
            throw new NotLoginStateException("未登录用户");
        }

        return true;
    }
}
