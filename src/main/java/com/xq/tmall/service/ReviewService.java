package com.xq.tmall.service;

import com.xq.tmall.entity.Review;
import com.xq.tmall.util.PageUtil;

import java.util.List;

/**
 * @author HP
 */
public interface ReviewService {
    /**
     * 添加用户
     * @param review 反馈
     * @return 添加反馈成功与否
     */
    boolean add(Review review);

    /**
     * 更新反馈
     * @param review 反馈
     * @return 更新反馈成功与否
     */
    boolean update(Review review);

    /**
     * 根据反馈id集合删除对应的反馈
     * @param review_id_list 反馈id集合
     * @return 删除成功与否
     */
    boolean deleteList(Integer[] review_id_list);

    /**
     * 获取反馈
     * @param review 反馈
     * @param pageUtil 获取总数
     * @return 获取一定数量反馈集合
     */
    List<Review> getList(Review review, PageUtil pageUtil);

    /**
     * 根据用户id获取反馈集合
     * @param user_id 用户id
     * @param pageUtil 获取总数
     * @return 根据用户id获取反馈集合
     */
    List<Review> getListByUserId(Integer user_id, PageUtil pageUtil);

    /**
     * 根据产品id获取反馈id
     * @param product_id 产品id
     * @param pageUtil 获取总数
     * @return 反馈集合
     */
    List<Review> getListByProductId(Integer product_id, PageUtil pageUtil);

    /**
     * 根据反馈id获取对应的反馈
     * @param review_id 反馈id
     * @return 根据反馈id获取对应的反馈
     */
    Review get(Integer review_id);

    /**
     * 根据反馈获取总数
     * @param review 反馈
     * @return 根据反馈获取总数
     */
    Integer getTotal(Review review);

    /**
     * @param user_id 用户id
     * @return 根据用户id获取
     */
    Integer getTotalByUserId(Integer user_id);
    /**
     *  根据产品id获取对应产品的评价数量
     * @param product_id 产品id
     * @return 获取此产品存在所有反馈总数
     */
    Integer getTotalByProductId(Integer product_id);

    /**
     * 获取订单条目存在评价的总数
     * @param productOrderItem_id 产品订单条目id
     * @return 获取订单条目存在评价的总数
     */
    Integer getTotalByOrderItemId(Integer productOrderItem_id);
}
