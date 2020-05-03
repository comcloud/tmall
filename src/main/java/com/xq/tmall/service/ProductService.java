package com.xq.tmall.service;

import com.xq.tmall.entity.Product;
import com.xq.tmall.util.OrderUtil;
import com.xq.tmall.util.PageUtil;

import java.util.List;

/**
 * @author HP
 */
public interface ProductService {
    /**
     * 添加产品
     * @param product 产品对象
     * @return 添加成功标志
     */
    boolean add(Product product);

    /**
     * 更改产品
     * @param product 产品对象
     * @return 更改成功标志
     */
    boolean update(Product product);

    /**
     *  Product集合
     * @param product 产品对象
     * @param product_isEnabled_array 产品是否依旧可用
     * @param orderUtil 排序封装体--封装了排序方式以及按照谁排序
     * @param pageUtil 获取数量封装体--封装了起始
     * @return 符合要求的Product集合
     */
    List<Product> getList(Product product, Byte[] product_isEnabled_array, OrderUtil orderUtil, PageUtil pageUtil);

    /**
     * 获取产品集合
     * @param product 产品对象，用于
     * @param pageUtil 获取得条目数量
     * @return 产品集合
     */
    List<Product> getTitle(Product product, PageUtil pageUtil);
    Product get(Integer product_Id);
    Integer getTotal(Product product,Byte[] product_isEnabled_array);

    List<Product> getMoreList(Product product, Byte[] bytes, OrderUtil orderUtil, PageUtil pageUtil, String[] product_name_split);

    Integer getMoreListTotal(Product product, Byte[] bytes, String[] product_name_split);
}
