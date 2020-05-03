package com.xq.tmall.util.baidu_face.com.baidu.ai.aip.utils;


import com.xq.tmall.util.FaceUtil;
import com.xq.tmall.util.baidu_face.AuthService.AuthService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 人脸注册
 * @author HP
 */
public class FaceAdd {

    private static String user_id,user_info;
    /**
     * @param filepath base64字符串
     * @return 对此人脸进行识别判断后的json串
     */
    public static String add(String filepath) {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        UUID u = UUID.randomUUID();
        String s = u.toString().replace('-','_');
        FaceUtil.uuid = s;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            // filepath 传入BASE64的字符串
            map.put("image", filepath);
            //百度人脸库的用户组名
            map.put("group_id", "group_1");
            //用户注册id
            map.put("user_id", s);
            //用户注册端口
            map.put("user_info", "user's info");
            //非活体检测
            map.put("liveness_control", "NONE");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，
            // 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


}