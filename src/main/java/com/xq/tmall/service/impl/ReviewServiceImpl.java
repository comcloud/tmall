package com.xq.tmall.service.impl;

import com.xq.tmall.dao.ReviewMapper;
import com.xq.tmall.entity.Review;
import com.xq.tmall.service.ReviewService;
import com.xq.tmall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HP
 */
@Service
public class ReviewServiceImpl implements ReviewService{
    /**
     * 反馈操作数据库层
     */
    @Autowired
    private ReviewMapper reviewMapper;

    /**
     * @param review 反馈
     * @return 添加成功与否
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean add(Review review) {
        return reviewMapper.insertOne(review)>0;
    }

    /**
     * @param review 反馈
     * @return 更改成功与否
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean update(Review review) {
        return reviewMapper.updateOne(review)>0;
    }

    /**
     * @param review_id_list 反馈id集合
     * @return 删除指定所有的反馈
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean deleteList(Integer[] review_id_list) {
        return reviewMapper.deleteList(review_id_list)>0;
    }

    /**
     * @param review   反馈
     * @param pageUtil 获取总数
     * @return 获取指定的反馈集合
     */
    @Override
    public List<Review> getList(Review review, PageUtil pageUtil) {
        return reviewMapper.select(review,pageUtil);
    }

    /**
     * @param user_id  用户id
     * @param pageUtil 获取总数
     * @return 获取指定的反馈集合
     */
    @Override
    public List<Review> getListByUserId(Integer user_id, PageUtil pageUtil) {
        return reviewMapper.selectByUserId(user_id,pageUtil);
    }

    /**
     * @param product_id 产品id
     * @param pageUtil   获取总数
     * @return 获取反馈集合
     */
    @Override
    public List<Review> getListByProductId(Integer product_id, PageUtil pageUtil) {
        return reviewMapper.selectByProductId(product_id,pageUtil);
    }

    /**
     * @param review_id 反馈id
     * @return 获取反馈
     */
    @Override
    public Review get(Integer review_id) {
        return reviewMapper.selectOne(review_id);
    }

    /**
     * @param review 反馈
     * @return 获取反馈
     */
    @Override
    public Integer getTotal(Review review) {
        return reviewMapper.selectTotal(review);
    }

    /**
     * @param user_id 用户id
     * @return 获取此用户发的反馈数量
     */
    @Override
    public Integer getTotalByUserId(Integer user_id) {
        return reviewMapper.selectTotalByUserId(user_id);
    }

    /**
     * @param product_id 产品id
     * @return 获取此产品的评价数量
     */
    @Override
    public Integer getTotalByProductId(Integer product_id) {
        return reviewMapper.selectTotalByProductId(product_id);
    }

    /**
     * @param productOrderItem_id 产品订单条目id
     * @return 获取此订单条目对应的评价数量
     */
    @Override
    public Integer getTotalByOrderItemId(Integer productOrderItem_id) {
        return reviewMapper.selectTotalByOrderItemId(productOrderItem_id);
    }
}
