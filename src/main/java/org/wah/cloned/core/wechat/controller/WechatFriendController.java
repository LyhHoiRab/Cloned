package org.wah.cloned.core.wechat.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/page/cloned/wechatFriend")
public class WechatFriendController{

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        return new ModelAndView("cloned/wechatFriend/index", content);
    }

    @RequestMapping(value = "/list/{organizationId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView list(ModelMap content){
        return new ModelAndView("cloned/wechatFriend/list", content);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView edit(ModelMap content){
        return new ModelAndView("cloned/wechatFriend/edit", content);
    }

    @RequestMapping(value = "/message/{remarkname}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView message(ModelMap content){
        return new ModelAndView("cloned/wechatFriend/message", content);
    }
}
