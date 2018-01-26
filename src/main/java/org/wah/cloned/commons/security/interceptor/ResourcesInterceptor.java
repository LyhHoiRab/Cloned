package org.wah.cloned.commons.security.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.wah.cloned.commons.security.consts.ResourcesName;
import org.wah.cloned.commons.utils.URLUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourcesInterceptor extends HandlerInterceptorAdapter{

    private static final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        String url = request.getRequestURI().substring(request.getContextPath().length());
        String basePath = URLUtils.getRequestPath(request);

        ModelMap content = modelAndView.getModelMap();
        //填充
        content.put(ResourcesName.BASE_PATH, basePath);
    }
}
