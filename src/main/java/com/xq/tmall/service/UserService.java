package com.xq.tmall.service;

import com.xq.tmall.entity.User;
import com.xq.tmall.util.OrderUtil;
import com.xq.tmall.util.PageUtil;

import java.util.List;

/**
 * 用户数据库添加字段uuid，User类已经添加此属性
 * @author HP
 */
public interface UserService {
    /**
     * 添加用户方法
     * @param user 待添加的用户
     * @return 添加成功与否
     */
    boolean add(User user);

    /**
     * 更新方法
     * @param user 待更新的用户
     * @return 更新成功与否
     */
    boolean update(User user);

    /**
     * @param user
     * @param orderUtil
     * @param pageUtil
     * @return
     */
    List<User> getList(User user, OrderUtil orderUtil, PageUtil pageUtil);

    /**
     * @param user_id 用户id
     * @return 根据用户id获取的用户信息
     */
    User get(Integer user_id);
    /**
     * @param uuid 用户人脸登录的uuid值
     * @return 用户对象
     */
    User get(String uuid);

    /**
     * @param user_name 用户名
     * @param user_password 用户密码
     * @return 用户登录验证
     */
    User login(String user_name, String user_password);

    /**
     * @param user 用户信息
     * @return 获取数据库中存在此用户的数量
     */
    Integer getTotal(User user);

}
