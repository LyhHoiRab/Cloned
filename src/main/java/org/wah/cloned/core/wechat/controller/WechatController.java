package org.wah.cloned.core.wechat.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/page/cloned/wechat")
public class WechatController{

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        return new ModelAndView("cloned/wechat/index", content);
    }

    @RequestMapping(value = "/list/{organizationId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView list(ModelMap content){
        return new ModelAndView("cloned/wechat/list", content);
    }

    @RequestMapping(value = "/add/{organizationId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView add(ModelMap content){
        return new ModelAndView("cloned/wechat/edit", content);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView edit(ModelMap content){
        return new ModelAndView("cloned/wechat/edit", content);
    }

    @RequestMapping(value = "/service/{wechatId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView service(ModelMap content){
        return new ModelAndView("cloned/wechat/service", content);
    }

    @RequestMapping(value = "/service/add/{wechatId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView serviceAdd(ModelMap content){
        return new ModelAndView("cloned/wechat/serviceEdit", content);
    }

    @RequestMapping(value = "/allocation/{wechatId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView allocation(ModelMap content){
        return new ModelAndView("cloned/wechat/allocation", content);
    }

    @RequestMapping(value = "/im/{wechatId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView im(ModelMap content){
        return new ModelAndView("cloned/wechat/im", content);
    }
}
