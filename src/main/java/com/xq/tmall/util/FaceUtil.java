package com.xq.tmall.util;


import com.xq.tmall.entity.User;
import com.xq.tmall.util.baidu_face.com.baidu.ai.aip.utils.FaceSearch;
import com.xq.tmall.util.baidu_face.com.baidu.ai.aip.utils.GsonUtils;
import com.xq.tmall.util.baidu_face.com.besjon.pojo.JsonRootBean;
import com.xq.tmall.util.baidu_face.com.besjon.pojo.Result;
import com.xq.tmall.util.baidu_face.com.besjon.pojo.User_list;

/**
 * @author HP
 */
public class FaceUtil {
    /**
     * 人脸评测分数
     */
    public static Double score;
    /**
     * 对应的user_id
     */
    public static String uuid;

    /**
     * 检测人脸返回错误码，0表示success等
     */
    public static String error_code;

    /**
     * 设置用户uuid
     */
    public static User user ;

    /**
     * @param file base64字符串
     * @return 人脸库中是否存在此人脸
     */
    public static boolean checkFace(String file){
        String resultjson = FaceSearch.faceSearch(file);
        JsonRootBean rootBean = GsonUtils.fromJson(resultjson,JsonRootBean.class);
        Result res = rootBean.result;
        if(res == null){
            //此时表示匹配人脸模糊等问题
            return false;
        }
        User_list user_list = res.user_list[0];
        User_list list = user_list;
        score = list.getScore();
        uuid = list.getUser_id();


        //获取返回的端口（管理员或者用户）和 匹配得分（100为满分）
        return list.getScore()>90;
    }

    /**
     * @param user 写好注册信息的用户，但是还没有设置uuid值
     * @return 设置后的user
     */
    public static User set(User user){
        return user.setUuid(uuid);
    }
}
