package com.xq.tmall.dao;

import org.springframework.stereotype.Repository;

/**
 * @author HP
 */
@Repository
public interface LastIDMapper {
    /**
     *  返回最后一个INSERT或 UPDATE 问询为 AUTO_INCREMENT列设置的第一个 发生的值。
     * @return 返回最后一个INSERT或 UPDATE 问询为 AUTO_INCREMENT列设置的第一个 发生的值。
     */
    int selectLastID();
}
