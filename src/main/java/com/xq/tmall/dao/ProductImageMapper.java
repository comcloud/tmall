package com.xq.tmall.dao;

import com.xq.tmall.entity.ProductImage;
import com.xq.tmall.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author HP
 */
@Repository
public interface ProductImageMapper {
    /**
     * 添加
     * @param productImage 产品图片
     * @return 添加成功与否
     */
    Integer insertOne(@Param("productImage") ProductImage productImage);
    /**
     * 添加一组
     * @param productImageList 产品图片集合
     * @return 添加成功与否
     */
    Integer insertList(@Param("productImage_list") List<ProductImage> productImageList);
    /**
     * 更改
     * @param productImage 产品图片
     * @return 更改产品图片
     */
    Integer updateOne(@Param("productImage") ProductImage productImage);
    /**
     *  删除
     * @param productImage_id_list 产品图片id集合
     * @return 删除产品图片
     */
    Integer deleteList(@Param("productImage_id_list") Integer[] productImage_id_list);
    /**
     * 产品图片集合
     * @param product_id 要获取的产品id
     * @param productImage_type 图片类型---这里用01区分图片
     * @param pageUtil 页面封装体
     * @return 对应的产品图片集合
     */
    List<ProductImage> select(@Param("product_id") Integer product_id, @Param("productImage_type") Byte productImage_type, @Param("pageUtil") PageUtil pageUtil);
    /**
     * 根据产品图片id获取产品图片
     * @param productImage_id  产品图片id
     * @return 产品图片
     */
    ProductImage selectOne(@Param("productImage_id") Integer productImage_id);
    /**
     * 获取总数
     * @param product_id 产品id
     * @param productImage_type 产品图片类型
     * @return 获取总数
     */
    Integer selectTotal(@Param("product_id") Integer product_id, @Param("productImage_type") Byte productImage_type);
}
