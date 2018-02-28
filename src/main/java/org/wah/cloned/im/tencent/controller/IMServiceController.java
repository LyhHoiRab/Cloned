package org.wah.cloned.im.tencent.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/page/cloned/im/tencent/service")
public class IMServiceController{

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        return new ModelAndView("/cloned/im/tencent/service/index", content);
    }
}
