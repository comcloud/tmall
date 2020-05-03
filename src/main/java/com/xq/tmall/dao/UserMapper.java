package com.xq.tmall.dao;

import com.xq.tmall.entity.User;
import com.xq.tmall.util.OrderUtil;
import com.xq.tmall.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HP
 * @Respository注解 用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean
 * 用户数据库操作层
 */
@Repository
public interface UserMapper {
    /**
     * @param user 添加用户
     * @return 添加用户
     */
    Integer insertOne(@Param("user") User user);

    /**
     * @param user 待更新用户信息
     * @return 更新用户
     */
    Integer updateOne(@Param("user") User user);

    List<User> select(@Param("user") User user, @Param("orderUtil") OrderUtil orderUtil, @Param("pageUtil") PageUtil pageUtil);

    /**
     * @param user_id 用户id
     * @return 根据用户id查询出来的用户
     */
    User selectOne(@Param("user_id") Integer user_id);

    /**
     * @param user_name 用户名
     * @param user_password 用户密码
     * @return 用户名加上密码返回的用户对象
     */
    User selectByLogin(@Param("user_name") String user_name, @Param("user_password") String user_password);

    /**
     *
     * @param user 用户信息
     * @return 返回数据库中存在此用户的数量
     */
    Integer selectTotal(@Param("user") User user);

    /**
     * 根据用户uuid值获取用户信息
     * @param uuid 查询用户uuid值
     * @return 用户信息
     */
    User selectByUuid(@Param("uuid") String uuid);
}
