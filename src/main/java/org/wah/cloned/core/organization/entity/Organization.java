package org.wah.cloned.core.organization.entity;

import org.wah.doraemon.entity.base.Createable;
import org.wah.doraemon.entity.base.Entity;
import org.wah.doraemon.entity.base.Updateable;

import java.util.Date;

public class Organization extends Entity implements Createable, Updateable{

    private String name;
    private String token;
    //公司名称
    private String companyName;
    //营业执照注册号
    private String licenseNumber;
    //法定代表人
    private String legalPerson;
    //公司地址
    private String companyAddress;
    //公司联系电话
    private String companyPhone;
    //公司邮箱
    private String companyEmail;
    //公司网址
    private String companyWebsite;
    private Date createTime;
    private Date updateTime;

    public Organization(){

    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getLicenseNumber(){
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber = licenseNumber;
    }

    public String getLegalPerson(){
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson){
        this.legalPerson = legalPerson;
    }

    public String getCompanyAddress(){
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress){
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone(){
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone){
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail(){
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail){
        this.companyEmail = companyEmail;
    }

    public String getCompanyWebsite(){
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite){
        this.companyWebsite = companyWebsite;
    }

    @Override
    public Date getCreateTime(){
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime(){
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}
