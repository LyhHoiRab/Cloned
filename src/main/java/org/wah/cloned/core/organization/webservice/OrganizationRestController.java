package org.wah.cloned.core.organization.webservice;

import org.bouncycastle.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.wah.cloned.core.organization.entity.Organization;
import org.wah.cloned.core.organization.service.OrganizationService;
import org.wah.doraemon.security.exception.ApplicationException;
import org.wah.doraemon.security.response.Page;
import org.wah.doraemon.security.response.PageRequest;
import org.wah.doraemon.security.response.Response;

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/organization")
public class OrganizationRestController{

    private Logger logger = LoggerFactory.getLogger(OrganizationRestController.class);

    @Autowired
    private OrganizationService organizationService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Organization> save(@RequestBody Organization organization){
        try{
            organizationService.save(organization);

            return new Response<Organization>("保存成功", organization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Organization> update(@RequestBody Organization organization){
        try{
            organizationService.update(organization);

            return new Response<Organization>("更新成功", organization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Organization> getById(@PathVariable("id") String id){
        try{
            Organization organization = organizationService.getById(id);

            return new Response<Organization>("查询成功", organization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据条件查询
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Organization>> find(String token, String name, String companyName){
        try{
            List<Organization> list = organizationService.find(token, name, companyName);

            return new Response<List<Organization>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Organization>> page(Long pageNum, Long pageSize, String token, String name, String companyName){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Organization> page = organizationService.page(pageRequest, token, name, companyName);

            return new Response<Page<Organization>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
