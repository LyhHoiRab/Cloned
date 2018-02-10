package org.wah.cloned.core.auth.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.wah.cloned.commons.security.consts.SessionParamName;
import org.wah.cloned.core.account.service.UserService;
import org.wah.doraemon.entity.User;
import org.wah.doraemon.security.response.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/1.0/userInfo")
public class UserInfoRestController{

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response save(@RequestBody User user, HttpServletRequest request){
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute(SessionParamName.ACCOUNT_ID);
        user.setAccountId(accountId);

        userService.save(user);

        return new Response("保存成功", null);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> upload(@RequestParam("file") CommonsMultipartFile file){
        String url = userService.upload(file);

        return new Response<String>("上传成功", url);
    }
}
