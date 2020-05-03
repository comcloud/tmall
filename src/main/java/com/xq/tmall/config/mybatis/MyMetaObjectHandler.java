package com.xq.tmall.config.mybatis;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Timestamp;

/**
 * Describe: 自定义填充公共 name 字段
 * 自定义填充策略接口实现
 * @author HP
 */
public class MyMetaObjectHandler extends MetaObjectHandler {

    /**
     * 测试 user 表 name 字段为空自动填充
     * @param metaObject 填充工具
     */
	 @Override
    public void insertFill(MetaObject metaObject) {
        // 更多查看源码测试用例
        System.out.println("*************************");
        System.out.println("insert fill");
        System.out.println("*************************");

        // 测试下划线
        Object testType = getFieldValByName("gmtCreate", metaObject);//mybatis-plus版本2.0.9+
        System.out.println("gmtCreate=" + testType);
        if (testType == null) {
            setFieldValByName("gmtCreate", new Timestamp(System.currentTimeMillis()), metaObject);//mybatis-plus版本2.0.9+
        }
    }

    /**
     * @param metaObject 填充公寓
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //更新填充
        System.out.println("*************************");
        System.out.println("update fill");
        System.out.println("*************************");
        //mybatis-plus版本2.0.9+
        setFieldValByName("gmtModified", new Timestamp(System.currentTimeMillis()), metaObject);
    }

}
