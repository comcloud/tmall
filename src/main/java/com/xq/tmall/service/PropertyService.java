package com.xq.tmall.service;

import com.xq.tmall.entity.Property;
import com.xq.tmall.util.PageUtil;

import java.util.List;

/**
 * @author HP
 */
public interface PropertyService {
    /**
     *  添加
     * @param property 属性值
     * @return 添加属性值
     */
    boolean add(Property property);

    /**
     *  更改
     * @param property 属性值
     * @return 更改属性
     */
    boolean update(Property property);

    /**
     *  删除
     * @param property_id_list 属性id集合
     * @return 删除成功与否
     */
    boolean deleteList(Integer[] property_id_list);

    /**
     *  获取属性集合
     * @param property 属性
     * @param pageUtil 获取总数
     * @return 获取属性集合
     */
    List<Property> getList(Property property, PageUtil pageUtil);

    /**
     *  根据属性id获取属性
     * @param property_id 属性id
     * @return 获取属性
     */
    Property get(Integer property_id);

    /**
     *  获取总数
     * @param property 属性
     * @return 获取总数
     */
    Integer getTotal(Property property);
}
