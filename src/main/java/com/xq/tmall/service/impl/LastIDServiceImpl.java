package com.xq.tmall.service.impl;

import com.xq.tmall.dao.LastIDMapper;
import com.xq.tmall.service.LastIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author HP
 */
@Service
public class LastIDServiceImpl implements LastIDService {
    /**
     * 最后的id服务层
     */
    @Autowired
    private LastIDMapper lastIDMapper;

    /**
     * @return 返回最后一个INSERT或 UPDATE 问询为 AUTO_INCREMENT列设置的第一个 发生的值。
     */
    @Override
    public int selectLastID() {
        return lastIDMapper.selectLastID();
    }
}
