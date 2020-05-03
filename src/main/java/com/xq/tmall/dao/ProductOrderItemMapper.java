package com.xq.tmall.dao;

import com.xq.tmall.entity.OrderGroup;
import com.xq.tmall.entity.ProductOrderItem;
import com.xq.tmall.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @author HP
 */
@Repository
public interface ProductOrderItemMapper {
    /**
     * 添加订单条目
     * @param productOrderItem 订单条目
     * @return 添加成功与否
     */
    Integer insertOne(@Param("productOrderItem") ProductOrderItem productOrderItem);
    /**
     *  更改订单条目
     * @param productOrderItem 产品订单条目
     * @return 更改订单条目成功与否
     */
    Integer updateOne(@Param("productOrderItem") ProductOrderItem productOrderItem);
    /**
     * 删除订单条目
     * @param productOrderItem_id_list 订单条目id集合
     * @return 删除成功与否
     */
    Integer deleteList(@Param("productOrderItem_id_list") Integer[] productOrderItem_id_list);
    /**
     * 获取产品订单条目集合
     * @param pageUtil 获取总数
     * @return 获取产品订单条目集合
     */
    List<ProductOrderItem> select(@Param("pageUtil") PageUtil pageUtil);
    /**
     * 根据订单id获取产品订单条目集合
     * @param order_id 订单id
     * @param pageUtil 获取总数
     * @return 获取产品订单条目集合
     */
    List<ProductOrderItem> selectByOrderId(@Param("order_id") Integer order_id, @Param("pageUtil") PageUtil pageUtil);
    /**
     *  根据用户id获取产品订单条目集合
     * @param user_id  用户id
     * @param pageUtil 获取总数
     * @return 获取产品订单条目集合
     */
    List<ProductOrderItem> selectByUserId(@Param("user_id") Integer user_id, @Param("pageUtil") PageUtil pageUtil);
    /**
     * 根据产品id获取产品订单条目集合
     * @param product_id 产品id
     * @param pageUtil 获取总数
     * @return 获取产品订单条目集合
     */
    List<ProductOrderItem> selectByProductId(@Param("product_id") Integer product_id, @Param("pageUtil") PageUtil pageUtil);
    /**
     *  根据订单条目id获取产品订单条目
     * @param productOrderItem_id 产品订单条目id
     * @return 获取产品订单条目
     */
    ProductOrderItem selectOne(@Param("productOrderItem_id") Integer productOrderItem_id);
    /**
     *  获取订单总量
     * @return 获取订单总量
     */
    Integer selectTotal();
    /**
     *  根据订单id获取订单总量
     * @param order_id 订单id
     * @return
     */
    Integer selectTotalByOrderId(@Param("order_id") Integer order_id);
    /**
     *  根据用户id 获取订单总量
     * @param user_id 用户id
     * @return 获取订单总量
     */
    Integer selectTotalByUserId(@Param("user_id") Integer user_id);
    /**
     *  根据产品id获取销售数量
     * @param product_id 产品id
     * @return 获取销售数量
     */
    Integer selectSaleCount(@Param("product_id") Integer product_id);
    /**
     * 根据产品id获取所有的订单组
     * @param product_id 产品id
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return 获取订单组集合
     */
    List<OrderGroup> getTotalByProductId(@Param("product_id") Integer product_id, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
}
