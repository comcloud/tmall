package com.xq.tmall.dao;

import com.xq.tmall.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author HP
 */
@Repository
public interface AddressMapper {
    /**
     *  添加
     * @param address 定制
     * @return 添加地址成功与否
     */
    Integer insertOne(@Param("address") Address address);
    /**
     * 更改
     * @param address 地址
     * @return 更改成功与否标志
     */
    Integer updateOne(@Param("address") Address address);
    /**
     *  获取地址集合
     * @param address_name 地址名
     * @param address_regionId 地址区域id
     * @return 地址集合
     */
    List<Address> select(@Param("address_name") String address_name, @Param("address_regionId") String address_regionId);
    /**
     *  根据地址区id获取地址
     * @param address_areaId 地址区id
     * @return 地址
     */
    Address selectOne(@Param("address_areaId") String address_areaId);
    /**
     *  获取地址集合
     * @return 地址集合
     */
    List<Address> selectRoot();
}