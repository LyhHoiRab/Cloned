package org.wah.cloned.core.auth.service;

import org.wah.doraemon.entity.Account;

public interface RegisterService{

    /**
     * 注册
     */
    Account register(String username, String password, String organizationId);
}
