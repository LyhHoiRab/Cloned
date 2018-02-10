package org.wah.cloned.core.page.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.wah.cloned.commons.security.consts.SessionParamName;
import org.wah.cloned.core.account.service.UserService;
import org.wah.doraemon.entity.User;
import org.wah.doraemon.security.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = {"", "/page/cloned/index"})
public class IndexController{

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(HttpServletRequest request, ModelMap content){
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute(SessionParamName.ACCOUNT_ID);

        User user = userService.getByAccountId(accountId);
        if(user == null){
            throw new UserNotFoundException("用户信息未获");
        }

        session.setAttribute(SessionParamName.USER_NICKNAME, user.getNickname());
        session.setAttribute(SessionParamName.USER_NAME, user.getName());
        session.setAttribute(SessionParamName.USER_HEAD_IMG, user.getHeadImgUrl());

        return new ModelAndView("cloned/index", content);
    }
}
