package com.xq.tmall.util.baidu_face.com.baidu.ai.aip.utils;



import com.xq.tmall.util.baidu_face.AuthService.AuthService;

import java.util.HashMap;
import java.util.Map;

/**
* 人脸搜索
 * @author HP
 */
public class FaceSearch {

    private static String select;
    private static Double score = null;
    private static Integer num;

    public static Double getScore() {
        return score;
    }

    public static String getSelect() {
        return select;
    }

    public static String faceSearch(String filepath) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            //指定图片转Base64格式
            map.put("image", filepath);
            //不需要活体检测
            map.put("liveness_control", "NONE");
            //用户组名自己定义
            map.put("group_id_list", "group_1");
            //图片格式为Base64
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}