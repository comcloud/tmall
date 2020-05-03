package com.xq.tmall.dao;

import com.xq.tmall.entity.Review;
import com.xq.tmall.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author HP
 */
@Repository
public interface ReviewMapper {
    /**
     * 插入一条反馈
     * @param review 反馈
     * @return 插入一条反馈
     */
    Integer insertOne(@Param("review") Review review);

    /**
     * 更改一条反馈
     * @param review 反馈
     * @return 更改反馈
     */
    Integer updateOne(@Param("review") Review review);

    /**
     * 删除一系列反馈
     * @param review_id_list 反馈id集合
     * @return 删除一系列反馈
     */
    Integer deleteList(@Param("review_id_list") Integer[] review_id_list);

    /**
     * 查询到的反馈集合
     * @param review 反馈
     * @param pageUtil 获取总数
     * @return 查询到的反馈集合
     */
    List<Review> select(@Param("review") Review review, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 根据用户id获取用户反馈的集合
     * @param user_id 用户id
     * @param pageUtil 获取总数
     * @return 获取反馈的集合
     */
    List<Review> selectByUserId(@Param("user_id") Integer user_id, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 根据产品id获取反馈集合
     * @param product_id 产品id
     * @param pageUtil 获取总数
     * @return 获取反馈集合
     */
    List<Review> selectByProductId(@Param("product_id") Integer product_id, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 根据反馈id获取一条反馈信息
     * @param review_id 反馈id
     * @return 获取一条反馈
     */
    Review selectOne(@Param("review_id") Integer review_id);

    /**
     * 根据反馈获取数据库中对应的数量
     * @param review 反馈
     * @return 此条反馈的数量
     */
    Integer selectTotal(@Param("review") Review review);

    /**
     * 根据用户id获取此用户发的反馈数量
     * @param user_id 用户id
     * @return 获取此用户发的反馈总数
     */
    Integer selectTotalByUserId(@Param("user_id") Integer user_id);

    /**
     * 根据产品id获取对应产品的评价数量
     * @param product_id 产品id
     * @return 获取此产品存在所有反馈总数
     */
    Integer selectTotalByProductId(@Param("product_id") Integer product_id);

    /**
     * 获取订单条目存在评价的总数
     * @param productOrderItem_id 产品订单条目id
     * @return 获取订单条目存在评价的总数
     */
    Integer selectTotalByOrderItemId(@Param("productOrderItem_id") Integer productOrderItem_id);
}
