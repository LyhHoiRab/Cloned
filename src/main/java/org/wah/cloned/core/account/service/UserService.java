package org.wah.cloned.core.account.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.wah.doraemon.entity.User;

import java.util.List;

public interface UserService{

    /**
     * 保存
     */
    void save(User user);

    /**
     * 更新
     */
    void update(User user);

    /**
     * 根据ID查询
     */
    User getById(String id);

    /**
     * 根据账户ID查询
     */
    User getByAccountId(String accountId);

    /**
     * 上传头像
     */
    String upload(CommonsMultipartFile file);

    /**
     * 根据微信ID查询为客服的用户信息
     */
    List<User> findIsServiceByWechatId(String wechatId);

    /**
     * 根据微信ID查询没有添加客服的用户信息
     */
    List<User> findIsNotServiceByWechatId(String wechatId);
}
