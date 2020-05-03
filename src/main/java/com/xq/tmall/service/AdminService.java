package com.xq.tmall.service;

import com.xq.tmall.entity.Admin;
import com.xq.tmall.util.PageUtil;

import java.util.List;

/**
 * @author HP
 */
public interface AdminService {
    /**
     *   添加
     * @param admin 管理员
     * @return 添加成功与否
     */
    boolean add(Admin admin);

    /**
     *  更改
     * @param admin 管理员
     * @return 更改成功标志
     */
    boolean update(Admin admin);

    /**
     *  获取指定数量的管理员
     * @param admin_name 管理员名
     * @param pageUtil 获取总数
     * @return 获取管理员集合
     */
    List<Admin> getList(String admin_name, PageUtil pageUtil);

    /**
     *  获取管理员
     * @param admin_name 管理员名
     * @param admin_id 管理员id
     * @return 获取管理员
     */
    Admin get(String admin_name, Integer admin_id);

    /**
     *  管理员登录
     * @param admin_name 管理员名
     * @param admin_password 管理员密码
     * @return 成功与否
     */
    Integer login(String admin_name, String admin_password);

    /**
     *  获取此管理员名总数
     * @param admin_name 管理员名
     * @return 管理员名总数
     */
    Integer getTotal(String admin_name);
}
