package com.xq.tmall.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

/**
 * 基控制器
 */
public class BaseController {
    /**
     * 日志类，记录日志
     */
    protected Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    /**
     * 检查管理员权限
     * @param session 用于会话的接收
     * @return 管理员权限
     */
    protected Object checkAdmin(HttpSession session){
        Object o = session.getAttribute("adminId");
        if(o==null){
            logger.info("无管理权限，返回管理员登陆页");
            return null;
        }
        logger.info("权限验证成功，管理员ID：{}",o);
        return o;
    }

    /**
     * 判断用户是否已经登录
     * @param session 用于会话的接收
     * @return 返回取到userId属性的查找
     */
    protected Object checkUser(HttpSession session){
        Object o = session.getAttribute("userId");
        if(o==null){
            logger.info("用户未登录");
            return null;
        }
        logger.info("用户已登录，用户ID：{}", o);
        return o;
    }
}
