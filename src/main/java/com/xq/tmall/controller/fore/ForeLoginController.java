package com.xq.tmall.controller.fore;

import com.alibaba.fastjson.JSONObject;
import com.xq.tmall.controller.BaseController;
import com.xq.tmall.entity.User;
import com.xq.tmall.service.UserService;
import com.xq.tmall.util.FaceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * 前台真食惠-登陆页
 *
 * @author HP
 */
@Controller
public class ForeLoginController extends BaseController {
    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     *
     * @param session 用户与服务器通讯
     * @param map     存放信息
     * @return 登录页面
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String goToPage(HttpSession session, Map<String, Object> map) {
        logger.info("转到前台真食惠-登录页");
        return "fore/loginPage";
    }

    @RequestMapping("faceLogin")
    public String goToFaceLogin() {
        return "fore/faceLogin";
    }

    /**
     * @param base64  base64图片串
     * @param session 用户与服务器通讯
     * @return 人脸验证信息
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    @RequestMapping("/login/receiveData")
    @ResponseBody
    public String get(@RequestBody String base64,
                      HttpSession session
    ) throws UnsupportedEncodingException {
        /**
         * 1.根据请求到的base64串进行处理获取纯净的串
         * 2.调用FaceUtil对此串进行检查
         *  - 验证成功，拿到FaceUtil中的uuid值--这个是每张人脸的唯一凭据，然后调用数据库获取用户信息
         *  - 将用户信息保存并返回成功信息
         *
         *  - 验证失败：保存错误信息然后返回
         * */
        String decode = URLDecoder.decode(base64, "UTF-8");
        int startIndex = decode.indexOf("base64,") + 7;
        int endIndex = decode.lastIndexOf("\"");
        String baseString = decode.substring(startIndex, endIndex);
        JSONObject json = new JSONObject();
        if (FaceUtil.checkFace(baseString)) {
            //此时表示已经验证成功
            String uuid = FaceUtil.uuid;
            User user = userService.get(uuid);
            session.setAttribute("userId", user.getUser_id());
            json.put("msg", "登录成功");
            json.put("success", true);
            return json.toJSONString();
        } else {
            Double score = FaceUtil.score;
            if (score == null) {
                json.put("msg", "人脸模糊,请将正脸靠近摄像头");
                json.put("success", false);
                return json.toJSONString();
            }

            if (score == 222202) {
                json.put("msg", "图片中没有人脸");
            } else if (score == 222207) {
                json.put("msg", "未找到匹配的用户");
            } else if (score == 223114) {
                json.put("msg", "人脸模糊,请将正脸靠近摄像头");
            } else {
                json.put("msg", "未查询到符合人脸，请将正脸靠近摄像头");
            }
            //验证失败
            json.put("success", false);
            return json.toJSONString();
        }
    }

    /**
     * 登录验证
     *
     * @param session  用户与服务器通讯
     * @param username 用户名
     * @param password 密码
     * @return 登录验证信息，使用JSONObject类
     */
    @ResponseBody
    @RequestMapping(value = "login/doLogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String checkLogin(HttpSession session, @RequestParam String username, @RequestParam String password) {
        /**
         * 1.根据用户名和密码调用用户服务层进行获取用户信息
         * 2.判断获取到的用户信息是否为null
         * 3.为null保存错误信息然后返回
         * 4.不为null保存成功信息并且保存用户id到session中返回
         * */
        logger.info("用户验证登录");
        User user = userService.login(username, password);

        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            logger.info("登录验证失败");
            jsonObject.put("success", false);
        } else {
            logger.info("登录验证成功,用户ID传入会话");
            session.setAttribute("userId", user.getUser_id());
            jsonObject.put("success", true);
        }
        return jsonObject.toJSONString();
    }

    /**
     * 用户退出登录
     *
     * @param session 用户与服务器通讯
     * @return 登录页面
     */
    @RequestMapping(value = "login/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        /**
         * 1.获取session中的用户id
         * 2.如果此时用户id不为null的话使用session移除此属性
         * 3.将session设置为失效
         * 4.转发到登录页面
         * */
        Object o = session.getAttribute("userId");
        if (o != null) {
            session.removeAttribute("userId");
            session.invalidate();
            logger.info("登录信息已清除，返回用户登录页");
        }
        return "redirect:/login";
    }
}
