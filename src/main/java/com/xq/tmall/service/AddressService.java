package com.xq.tmall.service;

import com.xq.tmall.entity.Address;

import java.util.List;

/**
 * @author HP
 */
public interface AddressService {
    /**
     *  添加
     * @param address 地址
     * @return 添加地址成功与否
     */
    boolean add(Address address);

    /**
     *  更改
     * @param address 地址
     * @return 更改成功与否标志
     */
    boolean update(Address address);

    /**
     *  获取地址集合
     * @param address_name 地址名
     * @param address_regionId 地址区域id
     * @return 地址集合
     */
    List<Address> getList(String address_name, String address_regionId);

    /**
     *  根据地址区id获取地址
     * @param address_areaId 地址区id
     * @return 地址
     */
    Address get(String address_areaId);

    /**
     *  获取地址集合
     * @return 地址集合
     */
    List<Address> getRoot();
}
