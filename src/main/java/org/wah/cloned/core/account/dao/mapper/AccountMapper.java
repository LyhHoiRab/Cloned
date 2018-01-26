package org.wah.cloned.core.account.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.doraemon.entity.Account;
import org.wah.doraemon.mybatis.Criteria;

import java.util.List;

@Repository
public interface AccountMapper{

    /**
     * 保存
     */
    void save(Account account);

    /**
     * 更新
     */
    void update(Account account);

    /**
     * 根据条件查询
     */
    Account getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<Account> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
