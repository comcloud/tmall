package com.xq.tmall.service;

import com.xq.tmall.entity.OrderGroup;
import com.xq.tmall.entity.ProductOrder;
import com.xq.tmall.util.OrderUtil;
import com.xq.tmall.util.PageUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author HP
 */
public interface ProductOrderService {
    /**
     * 添加产品订单
     * @param productOrder 产品订单
     * @return 添加成功与否
     */
    boolean add(ProductOrder productOrder);

    /**
     *  更改产品
     * @param productOrder 产品订单
     * @return 更改产品订单成功与否
     */
    boolean update(ProductOrder productOrder);

    /**
     * 删除一系列产品订单
     * @param productOrder_id_list 产品订单id集合
     * @return 删除成功否与
     */
    boolean deleteList(Integer[] productOrder_id_list);

    /**
     *  获取产品订单集合
     * @param productOrder 产品订单
     * @param productOrder_status_array 产品订单状态集合
     * @param orderUtil 排序工具
     * @param pageUtil 获取总数
     * @return 产品订单集合
     */
    List<ProductOrder> getList(ProductOrder productOrder, Byte[] productOrder_status_array, OrderUtil orderUtil, PageUtil pageUtil);

    /**
     * 获取订单组集合
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return 获取符合此日期间的订单组集合
     */
    List<OrderGroup> getTotalByDate(Date beginDate, Date endDate);

    /**
     * 根据产品订单id获取产品订单
     * @param productOrder_id 产品订单id
     * @return 产品订单
     */
    ProductOrder get(Integer productOrder_id);

    /**
     * 根据产品订单码获取产品订单
     * @param productOrder_code 产品订单码
     * @return 产品顶顶那
     */
    ProductOrder getByCode(String productOrder_code);

    /**
     * 获取产品订单总数
     * @param productOrder 产品订单
     * @param productOrder_status_array 产品订单状态数组
     * @return 获取订单总数
     */
    Integer getTotal(ProductOrder productOrder,Byte[] productOrder_status_array);
}
