package com.xq.tmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 主启动类
 * springboot工程
 * 开启事物支持
 * @author HP
 */
@SpringBootApplication
@EnableTransactionManagement
public class TfbApplication {

    /**
     * 主动类方法
     * @param args 主函数参数
     */
    public static void main(String[] args) {
        SpringApplication.run(TfbApplication.class, args);
    }


}
