package com.xq.tmall.dao;

import com.xq.tmall.entity.Category;
import com.xq.tmall.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author HP
 */
@Repository
public interface CategoryMapper {
    /**
     *  添加目录，返回成功标志
     * @param category 目录对象
     * @return 添加成功与否
     */
    Integer insertOne(@Param("category") Category category);
    /**
     * 更改目录信息
     * @param category 目录对象
     * @return 更改成功与否
     */
    Integer updateOne(@Param("category") Category category);
    /**
     * 获取目录集合
     * @param category_name 目录名
     * @param pageUtil 获取条数限制
     * @return 对应得目录集合
     */
    List<Category> select(@Param("category_name") String category_name, @Param("pageUtil") PageUtil pageUtil);
    /**
     * 获取对应id得目录对象
     * @param category_id 目录id
     * @return 根据目录id获取得目录对象
     */
    Category selectOne(@Param("category_id") Integer category_id);
    /**
     * 根据目录名获取对应得数量
     * @param category_name 目录名
     * @return 根据目录名获取对应得数量
     */
    Integer selectTotal(@Param("category_name") String category_name);
}