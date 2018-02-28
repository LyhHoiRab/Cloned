package org.wah.cloned.im.tencent.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/page/cloned/im/tencent/applet")
public class IMAppletController{

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        return new ModelAndView("cloned/im/tencent/applet/index", content);
    }

    @RequestMapping(value = "/list/{organizationId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView list(ModelMap content){
        return new ModelAndView("cloned/im/tencent/applet/list", content);
    }

    @RequestMapping(value = "/add/{organizationId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView add(ModelMap content){
        return new ModelAndView("cloned/im/tencent/applet/edit", content);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView edit(ModelMap content){
        return new ModelAndView("cloned/im/tencent/applet/edit", content);
    }

    @RequestMapping(value = "/admin/{appletId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView admin(ModelMap content){
        return new ModelAndView("cloned/im/tencent/applet/admin", content);
    }
}
