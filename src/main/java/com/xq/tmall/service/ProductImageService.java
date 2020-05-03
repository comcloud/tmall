package com.xq.tmall.service;

import com.xq.tmall.entity.ProductImage;
import com.xq.tmall.util.PageUtil;

import java.util.List;

/**
 * @author HP
 */
public interface ProductImageService {
    /**
     *  添加
     * @param productImage 产品图片
     * @return 添加成功与否
     */
    boolean add(ProductImage productImage);

    /**
     *  添加一组
     * @param productImageList 产品图片集合
     * @return 添加成功与否
     */
    boolean addList(List<ProductImage> productImageList);

    /**
     *  更改
     * @param productImage 产品图片
     * @return 更改产品图片
     */
    boolean update(ProductImage productImage);

    /**
     *  删除
     * @param productImage_id_list 产品图片id集合
     * @return 删除产品图片
     */
    boolean deleteList(Integer[] productImage_id_list);

    /**
     * 产品图片集合
     * @param product_id 要获取的产品id
     * @param productImage_type 图片类型---这里用01区分图片
     * @param pageUtil 页面封装体
     * @return 对应的产品图片集合
     */
    List<ProductImage> getList(Integer product_id, Byte productImage_type, PageUtil pageUtil);

    /**
     *  根据产品图片id获取产品图片
     * @param productImage_id  产品图片id
     * @return 产品图片
     */
    ProductImage get(Integer productImage_id);

    /**
     *  获取总数
     * @param product_id 产品id
     * @param productImage_type 产品图片类型
     * @return 获取总数
     */
    Integer getTotal(Integer product_id, Byte productImage_type);
}
