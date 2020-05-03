package com.xq.tmall.service;

/**
 * @author HP
 */
public interface LastIDService {
    /**
     *  返回最后一个INSERT或 UPDATE 问询为 AUTO_INCREMENT列设置的第一个 发生的值。
     * @return 返回最后一个INSERT或 UPDATE 问询为 AUTO_INCREMENT列设置的第一个 发生的值。
     */
    int selectLastID();
}
