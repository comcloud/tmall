package com.xq.tmall.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.xq.tmall.controller.BaseController;
import com.xq.tmall.entity.Admin;
import com.xq.tmall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 后台管理-登录页
 * @author HP
 */
@Controller
public class AdminLoginController extends BaseController {
    /**
     * 管理员服务层
     */
    @Autowired
    private AdminService adminService;

    /**
     * 转到后台管理-登录页
     * @return 前往登录页面
     */
    @RequestMapping("admin/login")
    public String goToPage(){
        logger.info("转到后台管理-登录页");
        return "admin/loginPage";
    }

    /**
     * 登陆验证-ajax
     * @param session 用户与服务器通讯
     * @param username 用户名
     * @param password 密码
     * @return 登录信息
     */
    @ResponseBody
    @RequestMapping(value = "admin/login/doLogin",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String checkLogin(HttpSession session, @RequestParam String username, @RequestParam String password) {
        logger.info("管理员登录验证");
        Integer admin = adminService.login(username,password);

        JSONObject object = new JSONObject();
        if(admin == null){
            logger.info("登录验证失败");
            object.put("success",false);
        } else {
            logger.info("登录验证成功，管理员ID传入会话");
            session.setAttribute("adminId",admin);
            object.put("success",true);
        }

        return object.toJSONString();
    }

    /**
     * 获取管理员头像路径-ajax
     * @param username 用户名
     * @return 存储获取头像信息
     */
    @ResponseBody
    @RequestMapping(value = "admin/login/profile_picture",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public String getAdminProfilePicture(@RequestParam String username){
        logger.info("根据用户名获取管理员头像路径");
        Admin admin = adminService.get(username,null);

        JSONObject object = new JSONObject();
        if(admin == null){
            logger.info("未找到头像路径");
            object.put("success",false);
        } else {
            logger.info("成功获取头像路径");
            object.put("success",true);
            object.put("srcString",admin.getAdmin_profile_picture_src());
        }

        return object.toJSONString();
    }
}
