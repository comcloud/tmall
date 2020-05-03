package com.xq.tmall.controller.fore;

import com.alibaba.fastjson.JSONObject;
import com.xq.tmall.controller.BaseController;
import com.xq.tmall.entity.Address;
import com.xq.tmall.entity.User;
import com.xq.tmall.service.AddressService;
import com.xq.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author HP
 * 用户层，提供用户
 */
@Controller
public class ForeUserController extends BaseController{
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
     * 用户前台页面
     * @param session 用户与服务器间的通讯
     * @param map 用于存放各级信息
     * @return 转到用户详情页
     */
    @RequestMapping(value = "userDetails", method = RequestMethod.GET)
    public String goToUserDetail(HttpSession session, Map<String,Object> map){
        /**
         * 1.检查用户是否已经登录
         *  - 登录成功
         * 2.通过用户服务层获取用户信息
         * 3.获取用户所在地区级地址
         * 4.获取市区级地址
         * 5.获取其他地址信息--这里使用地址服务层来服务，分别有根列表，城市列表，区列表
         * 6.将这些信息全部放入Map中
         *  - 登录失败
         * 2.返回登录
         * */
        logger.info("检查用户是否登录");
        Object userId = checkUser(session);
        if (userId != null) {
            logger.info("获取用户信息");
            User user = userService.get(Integer.parseInt(userId.toString()));
            map.put("user", user);

            logger.info("获取用户所在地区级地址");
            String districtAddressId = user.getUser_address().getAddress_areaId();
            Address districtAddress = addressService.get(districtAddressId);
            logger.info("获取市级地址信息");
            Address cityAddress = addressService.get(districtAddress.getAddress_regionId().getAddress_areaId());
            logger.info("获取其他地址信息");
            List<Address> addressList = addressService.getRoot();
            List<Address> cityList = addressService.getList(null,cityAddress.getAddress_regionId().getAddress_areaId());
            List<Address> districtList = addressService.getList(null,cityAddress.getAddress_areaId());

            map.put("addressList", addressList);
            map.put("cityList", cityList);
            map.put("districtList", districtList);
            map.put("addressId", cityAddress.getAddress_regionId().getAddress_areaId());
            map.put("cityAddressId", cityAddress.getAddress_areaId());
            map.put("districtAddressId", districtAddressId);
            return  "fore/userDetails";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * 用户更换头像
     * @param file 用于获取网页中原有的一些文件，这里用于获取头像图片
     * @param session 用户和服务器通讯
     * @return 存放更换信息---成功与文件名  或   失败与失败标志
     */
    @ResponseBody
    @RequestMapping(value = "user/uploadUserHeadImage", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public  String uploadUserHeadImage(@RequestParam MultipartFile file, HttpSession session
    ){
        /**
         * 1.先获取原始的文件名并取其文件后缀
         * 2.获取文件然后然后上传
         * 3.采用JSONObejct这个类用来存储上传信息
         * 4.将json对象保存的信息返回
         * */
        String originalFileName = file.getOriginalFilename();
        logger.info("获取图片原始文件名：{}", originalFileName);
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        //拼接新的文件名，规则是随机一个UUID值加上原始文件名的后缀
        String fileName = UUID.randomUUID() + extension;
        //这个时候相当于拿到了webapp路径
        String filePath = session.getServletContext().getRealPath("/") + "res/images/item/userProfilePicture/" + fileName;
        logger.info("文件上传路径：{}", filePath);
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("文件上传中...");
            file.transferTo(new File(filePath));
            logger.info("文件上传成功！");
            jsonObject.put("success", true);
            jsonObject.put("fileName", fileName);
        } catch (IOException e) {
            logger.warn("文件上传失败！");
            e.printStackTrace();
            jsonObject.put("success", false);
        }
        return jsonObject.toJSONString();
    }

    /**
     * 用户详情更新
     * @param session 用户与服务器通讯
     * @param map 存放用户信息
     * @param user_nickname 用户昵称
     * @param user_realname 用户真实姓名
     * @param user_gender 用户性别
     * @param user_birthday 用户生日
     * @param user_address 用户所在地
     * @param user_profile_picture_src 用户头像
     * @param user_password 用户密码
     * @return 用户详情页--这个时候已经更新  如果这时候用户没有登录则依旧跳转掉登录界面
     * @throws ParseException 解析异常，对于用户Id的解析
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    @RequestMapping(value="user/update",method=RequestMethod.POST,produces ="application/json;charset=utf-8")
    public String userUpdate(HttpSession session, Map<String,Object> map,
                             @RequestParam(value = "user_nickname") String user_nickname  /*用户昵称 */,
                             @RequestParam(value = "user_realname") String user_realname  /*真实姓名*/,
                             @RequestParam(value = "user_gender") String user_gender  /*用户性别*/,
                             @RequestParam(value = "user_birthday") String user_birthday /*用户生日*/,
                             @RequestParam(value = "user_address") String user_address  /*用户所在地 */,
                             @RequestParam(value = "user_profile_picture_src", required = false) String user_profile_picture_src /* 用户头像*/,
                             @RequestParam(value = "user_password") String user_password/* 用户密码 */
    ) throws ParseException, UnsupportedEncodingException {
        /**
         * 1.检查用户是否已经登录，这里使用继承父类的方法检查用户登录
         * 2.通过用户服务层获取用户对象，并把此对象放入map中
         * 3.判断获取的用户照片字符串是否有值，有值的话赋值为null
         * 4.调用用户服务层将新建的用户对象传入进行更新数据
         * 5.更新成功跳转到用户详情界面
         * */
        logger.info("检查用户是否登录");
        Object userId = checkUser(session);
        if (userId != null) {
            logger.info("获取用户信息");
            User user = userService.get(Integer.parseInt(userId.toString()));
            map.put("user", user);
        } else {
            return "redirect:/login";
        }
        logger.info("创建用户对象");
        if (user_profile_picture_src != null && "".equals(user_profile_picture_src)) {
            user_profile_picture_src = null;
        }
        User userUpdate = new User()
                .setUser_id(Integer.parseInt(userId.toString()))
                .setUser_nickname(new String(user_nickname.getBytes("ISO8859-1"),"UTF-8"))
                .setUser_realname(new String(user_realname.getBytes("ISO8859-1"),"UTF-8"))
                .setUser_gender(Byte.valueOf(user_gender))
                .setUser_birthday(new SimpleDateFormat("yyyy-MM-dd").parse(user_birthday))
                .setUser_address(new Address().setAddress_areaId(user_address))
                .setUser_profile_picture_src(user_profile_picture_src)
                .setUser_password(user_password);
        logger.info("执行修改");
        if (userService.update(userUpdate)){
             logger.info("修改成功!跳转到用户详情页面");
             return "redirect:/userDetails";
         }
         throw new RuntimeException();
    }


}
