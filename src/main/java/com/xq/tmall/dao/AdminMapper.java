package com.xq.tmall.dao;

import com.xq.tmall.entity.Admin;
import com.xq.tmall.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author HP
 */
@Repository
public interface AdminMapper {
    /**
     *  添加
     * @param admin 管理员
     * @return 添加成功与否
     */
    Integer insertOne(@Param("admin") Admin admin);
    /**
     * 更改
     * @param admin 管理员
     * @return 更改成功标志
     */
    Integer updateOne(@Param("admin") Admin admin);
    /**
     *  获取指定数量的管理员
     * @param admin_name 管理员名
     * @param pageUtil 获取总数
     * @return 获取管理员集合
     */
    List<Admin> select(@Param("admin_name") String admin_name, @Param("pageUtil") PageUtil pageUtil);
    /**
     *  获取管理员
     * @param admin_name 管理员名
     * @param admin_id 管理员id
     * @return 获取管理员
     */
    Admin selectOne(@Param("admin_name") String admin_name, @Param("admin_id") Integer admin_id);
    /**
     * 管理员登录
     * @param admin_name 管理员名
     * @param admin_password 管理员密码
     * @return 成功与否
     */
    Integer selectByLogin(@Param("admin_name") String admin_name, @Param("admin_password") String admin_password);
    /**
     *  获取此管理员名总数
     * @param admin_name 管理员名
     * @return 管理员名总数
     */
    Integer selectTotal(@Param("admin_name") String admin_name);
}