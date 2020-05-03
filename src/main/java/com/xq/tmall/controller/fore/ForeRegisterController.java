package com.xq.tmall.controller.fore;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xq.tmall.controller.BaseController;
import com.xq.tmall.entity.Address;
import com.xq.tmall.entity.User;
import com.xq.tmall.service.AddressService;
import com.xq.tmall.service.UserService;
import com.xq.tmall.util.FaceUtil;
import com.xq.tmall.util.baidu_face.com.baidu.ai.aip.utils.FaceAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 注册类
 * 关于人脸注册和账号密码注册关系
 * 账号密码注册的话必须要有人脸注册
 * 人脸注册的话可以不用账号密码注册
 *
 * @author HP
 */
@Controller
public class ForeRegisterController extends BaseController {
    /**
     * 地址服务层
     */
    @Autowired
    private AddressService addressService;
    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;

    /**
     * 跳转到用户注册页面
     *
     * @param map 存放用户注册值
     * @return 用户注册界面
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String goToPage(Map<String, Object> map) {
        /**
         * 1.通过地址服务层获取省份信息
         * 2.根据自己构建的地址id通过地址服务类获取市级地址
         * 3.根据自己构建的区级id通过地址服务类获取区级地址信息
         * 4.将这些信息存放在map中然后转发到用户注册页面
         * */
        String addressId = "110000";
        String cityAddressId = "110100";
        logger.info("获取省份信息");
        List<Address> addressList = addressService.getRoot();
        logger.info("获取addressId为{}的市级地址信息", addressId);
        List<Address> cityAddress = addressService.getList(null, addressId);
        logger.info("获取cityAddressId为{}的区级地址信息", cityAddressId);
        List<Address> districtAddress = addressService.getList(null, cityAddressId);
        map.put("addressList", addressList);
        map.put("cityList", cityAddress);
        map.put("districtList", districtAddress);
        map.put("addressId", addressId);
        map.put("cityAddressId", cityAddressId);
        logger.info("转到前台-用户注册页");
        return "fore/register";
    }

    /**
     * @return 前往人脸页面
     */
    @RequestMapping("/register/faceRegister")
    public String goToFace() {
        return "fore/faceRegister";
    }

    /**
     * 实现人脸注册，将人脸录入人脸库中，当人脸不符合要求时
     * 返回信息提示用户将摄像头对准并且保持光线明亮，可以尝试离电脑距离变小
     *
     * @param base64 base64人脸图片字符串信息
     * @return 响应数据
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    @RequestMapping("/register/receiveData")
    @ResponseBody
    public String get(@RequestBody String base64) throws UnsupportedEncodingException {
        /**
         * 1.对base64数据进行解码然后处理获取纯净的base64串
         * 2.调用FaceAdd类对此串进行解析判断是否符合要求
         *  - 符合则录入然后返回到首页
         *  - 不符合则提示用户用户将摄像头对准并且保持光线明亮，可以尝试离电脑距离变小
         * */
        String decode = URLDecoder.decode(base64, "utf-8");
        int startIndex = decode.indexOf("base64,") + 7;
        int endIndex = decode.lastIndexOf("\"");
        String baseString = decode.substring(startIndex, endIndex);
        String add = FaceAdd.add(baseString);
        String substring = add.substring(add.indexOf("result:") + 1);
        int error_code = (int) JSONObject.parseObject(substring).get("error_code");
        JSONObject jsonObject = new JSONObject();
        if (error_code != 0) {
            String tip = "将摄像头对准并且保持光线明亮，可以尝试离电脑距离变小";
            jsonObject.put("success", false);
            jsonObject.put("msg", tip);

        } else {
            String tip = "录入成功,将前往登录界面";
            User user = FaceUtil.user.setUuid(FaceUtil.uuid);
            userService.add(user);
            jsonObject.put("success", true);
            jsonObject.put("msg", tip);
        }
        return jsonObject.toJSONString();
    }

    /**
     * 使用ajax的用户注册
     *
     * @param user_name     用户名
     * @param user_nickname 用户昵称
     * @param user_password 用户密码
     * @param user_gender   用户性别
     * @param user_birthday 用户生日
     * @param user_address  用户地址
     * @return 存放的用户注册信息
     * @throws ParseException 解析异常
     */
    @ResponseBody
    @RequestMapping(value = "register/doRegister", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String register(
            @RequestParam(value = "user_name") String user_name  /*用户名 */,
            @RequestParam(value = "user_nickname") String user_nickname  /*用户昵称 */,
            @RequestParam(value = "user_password") String user_password  /*用户密码*/,
            @RequestParam(value = "user_gender") String user_gender  /*用户性别*/,
            @RequestParam(value = "user_birthday") String user_birthday /*用户生日*/,
            @RequestParam(value = "user_address") String user_address  /*用户所在地 */
    ) throws ParseException {
        /**
         * 1.判断用户是否已经存在---调用用户服务层方法判断数据中有此用户名的用户数量
         *  - 比0大的话表示用户已经存在则使用JSONObject类保存信息返回
         * 2.此时表示用户名不存在则创建用户对象
         * 3.调用用户服务层添加用户
         * 4.使用JSONObject类保存注册信息返回
         * */
        logger.info("验证用户名是否存在");
        Integer count = userService.getTotal(new User().setUser_name(user_name));
        if (count > 0) {
            logger.info("用户名已存在，返回错误信息!");
            JSONObject object = new JSONObject();
            object.put("success", false);
            object.put("msg", "用户名已存在，请重新输入！");
            return object.toJSONString();
        }
        logger.info("创建用户对象");
        User user = new User()
                .setUser_name(user_name)
                .setUser_nickname(user_nickname)
                .setUser_password(user_password)
                .setUser_gender(Byte.valueOf(user_gender))
                .setUser_birthday(new SimpleDateFormat("yyyy-MM-dd").parse(user_birthday))
                .setUser_address(new Address().setAddress_areaId(user_address))
                .setUser_homeplace(new Address().setAddress_areaId("130000"));
        logger.info("用户注册");
        //先在工具类中保存此用户，然后在人脸录入后添加uuid值再添加用户到数据库
        FaceUtil.user = user;
        logger.info("注册成功");
        JSONObject object = new JSONObject();
        object.put("success", true);
        return object.toString();
    }
}
