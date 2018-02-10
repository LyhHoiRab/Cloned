package org.wah.cloned.core.organization.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wah.cloned.commons.security.consts.SessionParamName;
import org.wah.cloned.core.organization.entity.Organization;
import org.wah.cloned.core.organization.service.OrganizationService;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.security.response.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/organization")
public class OrganizationRestController{

    @Autowired
    private OrganizationService organizationService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Organization> save(@RequestBody Organization organization){
        organizationService.save(organization);

        return new Response<Organization>("保存成功", organization);
    }

    /**
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Organization> update(@RequestBody Organization organization){
        organizationService.update(organization);

        return new Response<Organization>("更新成功", organization);
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Organization> getById(@PathVariable("id") String id){
        Organization organization = organizationService.getById(id);

        return new Response<Organization>("查询成功", organization);
    }

    /**
     * 根据条件查询
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Organization>> find(HttpServletRequest request, String token, String name, String companyName, Boolean auth){
        String accountId = Boolean.TRUE.equals(auth) ? (String) request.getSession().getAttribute(SessionParamName.ACCOUNT_ID) : null;

        List<Organization> list = organizationService.find(token, name, companyName, accountId);

        return new Response<List<Organization>>("查询成功", list);
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Organization>> page(HttpServletRequest request, Long pageNum, Long pageSize, String token, String name, String companyName, Boolean auth){
        String accountId = Boolean.TRUE.equals(auth) ? (String) request.getSession().getAttribute(SessionParamName.ACCOUNT_ID) : null;

        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Organization> page = organizationService.page(pageRequest, token, name, companyName, accountId);

        return new Response<Page<Organization>>("查询成功", page);
    }
}
