package com.xq.tmall.service;

import com.xq.tmall.entity.PropertyValue;
import com.xq.tmall.util.PageUtil;

import java.util.List;

/**
 * @author HP
 */
public interface PropertyValueService {
    /**
     *  添加
     * @param propertyValue 属性值
     * @return 添加成功与否
     */
    boolean add(PropertyValue propertyValue);

    /**
     *  添加
     * @param propertyValueList 属性值集合
     * @return 添加成功与否
     */
    boolean addList(List<PropertyValue> propertyValueList);

    /**
     *  更改
     * @param propertyValue 属性值
     * @return 更改成功标志
     */
    boolean update(PropertyValue propertyValue);

    /**
     *  删除
     * @param propertyValue_id_list 属性值id集合
     * @return 删除成功与否
     */
    boolean deleteList(Integer[] propertyValue_id_list);

    /**
     *  获取属性值集合
     * @param propertyValue 属性值
     * @param pageUtil 获取总数
     * @return 获取属性值集合
     */
    List<PropertyValue> getList(PropertyValue propertyValue, PageUtil pageUtil);

    /**
     *  获取属性值
     * @param propertyValue_id 属性值id
     * @return 获取属性值
     */
    PropertyValue get(Integer propertyValue_id);

    /**
     *  获取总数
     * @param propertyValue 属性值
     * @return 获取总数
     */
    Integer getTotal(PropertyValue propertyValue);
}
