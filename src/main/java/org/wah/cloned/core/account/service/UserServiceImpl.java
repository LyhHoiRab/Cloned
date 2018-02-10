package org.wah.cloned.core.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.wah.cloned.commons.utils.UpyunUtils;
import org.wah.cloned.core.account.dao.UserDao;
import org.wah.doraemon.entity.User;
import org.wah.doraemon.utils.FileUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(User user){
        Assert.notNull(user, "用户信息不能为空");
        Assert.hasText(user.getAccountId(), "账户ID不能为空");
        Assert.hasText(user.getNickname(), "用户昵称不能为空");
        Assert.hasText(user.getHeadImgUrl(), "用户头像不能为空");

        userDao.saveOrUpdate(user);
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(User user){
        Assert.notNull(user, "用户信息不能为空");
        Assert.hasText(user.getId(), "用户ID不能为空");

        userDao.saveOrUpdate(user);
    }

    /**
     * 根据ID查询
     */
    @Override
    public User getById(String id){
        Assert.hasText(id, "用户ID不能为空");

        return userDao.getById(id);
    }

    /**
     * 根据账户ID查询
     */
    @Override
    public User getByAccountId(String accountId){
        Assert.hasText(accountId, "用户账户ID不能为空");

        return userDao.getByAccountId(accountId);
    }

    /**
     * 上传头像
     */
    @Override
    public String upload(CommonsMultipartFile file){
        Assert.notNull(file, "上传文件不能为空");

        //文件名称
        String originalFilename = file.getOriginalFilename();
        //MD5
        String md5 = FileUtils.getMD5(file.getBytes(), false);
        //文件后缀
        String suffix = FileUtils.getSuffix(originalFilename);
        //上传名称
        String name = md5 + suffix;
        //上传路径
        String uploadPath = "/user/headImgUrl/" + name;

        boolean result = UpyunUtils.upload(UpyunUtils.Upyun.KULIAO, uploadPath, null, file);

        return result ? UpyunUtils.Upyun.KULIAO.getUrl() + uploadPath : "";
    }

    /**
     * 根据微信ID查询为客服的用户信息
     */
    public List<User> findIsServiceByWechatId(String wechatId){
        Assert.hasText(wechatId, "微信ID不能为空");

        return userDao.findIsServiceByWechatId(wechatId);
    }

    /**
     * 根据微信ID查询没有添加客服的用户信息
     */
    public List<User> findIsNotServiceByWechatId(String wechatId){
        Assert.hasText(wechatId, "微信ID不能为空");

        return userDao.findIsNotServiceByWechatId(wechatId);
    }
}
