package com.xq.tmall.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xq.tmall.controller.BaseController;
import com.xq.tmall.entity.Category;
import com.xq.tmall.entity.Property;
import com.xq.tmall.service.CategoryService;
import com.xq.tmall.service.LastIDService;
import com.xq.tmall.service.PropertyService;
import com.xq.tmall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 后台管理-分类页
 * @author HP
 */
@Controller
public class CategoryController extends BaseController {
    /**
     *
     */
    @Autowired
    private CategoryService categoryService;
    /**
     * 返回最后一个INSERT或 UPDATE 问询为 AUTO_INCREMENT列设置的第一个 发生的值。
     */
    @Autowired
    private LastIDService lastIDService;
    /**
     * 属性服务层
     */
    @Autowired
    private PropertyService propertyService;

    /**
     * 转到后台管理-分类页-ajax
     * @param session 用户与服务器通讯
     * @param map 存储值
     * @return 后台管理分类层
     */
    @RequestMapping(value = "admin/category", method = RequestMethod.GET)
    public String goToPage(HttpSession session, Map<String, Object> map) {
        logger.info("检查管理员权限");
        Object adminId = checkAdmin(session);
        if (adminId == null) {
            return "admin/include/loginMessage";
        }

        logger.info("获取前10条分类列表");
        PageUtil pageUtil = new PageUtil(0, 10);
        List<Category> categoryList = categoryService.getList(null, pageUtil);
        map.put("categoryList", categoryList);
        logger.info("获取分类总数量");
        Integer categoryCount = categoryService.getTotal(null);
        map.put("categoryCount", categoryCount);
        logger.info("获取分页信息");
        pageUtil.setTotal(categoryCount);
        map.put("pageUtil", pageUtil);

        logger.info("转到后台管理-分类页-ajax方式");
        return "admin/categoryManagePage";
    }

    /**
     * 转到后台管理-分类详情页-ajax
     * @param session  用户与服务器通讯
     * @param map 存储值
     * @param cid 分类id
     * @return 分类详情页面
     */
    @RequestMapping(value = "admin/category/{cid}", method = RequestMethod.GET)
    public String goToDetailsPage(HttpSession session, Map<String, Object> map, @PathVariable Integer cid/* 分类ID */) {
        logger.info("检查管理员权限");
        Object adminId = checkAdmin(session);
        if (adminId == null) {
            return "admin/include/loginMessage";
        }
        logger.info("获取category_id为{}的分类信息", cid);
        Category category = categoryService.get(cid);
        logger.info("获取分类子信息-属性列表");
        category.setPropertyList(propertyService.getList(new Property().setProperty_category(category), null));
        map.put("category", category);

        logger.info("转到后台管理-分类详情页-ajax方式");
        return "admin/include/categoryDetails";
    }

    /**
     * 转到后台管理-分类添加页-ajax
     * @param session 用户与服务器通讯
     * @param map 存储值
     * @return 分类添加页面
     */
    @RequestMapping(value = "admin/category/new", method = RequestMethod.GET)
    public String goToAddPage(HttpSession session, Map<String, Object> map) {
        logger.info("检查管理员权限");
        Object adminId = checkAdmin(session);
        if (adminId == null) {
            return "admin/include/loginMessage";
        }

        logger.info("转到后台管理-分类添加页-ajax方式");
        return "admin/include/categoryDetails";
    }

    /**
     * 添加分类信息-ajax
     * @param category_name  分类名
     * @param category_image_src 分类图片
     * @return 添加分类信息
     */
    @ResponseBody
    @RequestMapping(value = "admin/category", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String addCategory(@RequestParam String category_name/* 分类名称 */,
                              @RequestParam String category_image_src/* 分类图片路径 */) {
        JSONObject jsonObject = new JSONObject();
        logger.info("整合分类信息");
        Category category = new Category()
                .setCategory_name(category_name)
                .setCategory_image_src(category_image_src.substring(category_image_src.lastIndexOf("/") + 1));
        logger.info("添加分类信息");
        boolean yn = categoryService.add(category);
        if (yn) {
            int category_id = lastIDService.selectLastID();
            logger.info("添加成功！,新增分类的ID值为：{}", category_id);
            jsonObject.put("success", true);
            jsonObject.put("category_id", category_id);
        } else {
            jsonObject.put("success", false);
            logger.warn("添加失败！事务回滚");
            throw new RuntimeException();
        }

        return jsonObject.toJSONString();
    }

    /**
     * 更新分类信息-ajax
     * @param category_name 分类名
     * @param category_image_src 分类图片
     * @param category_id 分类id
     * @return 更新分类信息
     */
    @ResponseBody
    @RequestMapping(value = "admin/category/{category_id}", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public String updateCategory(@RequestParam String category_name/* 分类名称 */,
                                 @RequestParam String category_image_src/* 分类图片路径 */,
                                 @PathVariable("category_id") Integer category_id/* 分类ID */) {
        JSONObject jsonObject = new JSONObject();
        logger.info("整合分类信息");
        Category category = new Category()
                .setCategory_id(category_id)
                .setCategory_name(category_name)
                .setCategory_image_src(category_image_src.substring(category_image_src.lastIndexOf("/") + 1));
        logger.info("更新分类信息，分类ID值为：{}", category_id);
        boolean yn = categoryService.update(category);
        if (yn) {
            logger.info("更新成功！");
            jsonObject.put("success", true);
            jsonObject.put("category_id", category_id);
        } else {
            jsonObject.put("success", false);
            logger.info("更新失败！事务回滚");
            throw new RuntimeException();
        }

        return jsonObject.toJSONString();
    }

    /**
     * 按条件查询分类-ajax
     * @param category_name 分类名
     * @param index 页数
     * @param count 行数
     * @return 查询你的分类信息
     * @throws UnsupportedEncodingException 不支持编码信息
     */
    @ResponseBody
    @RequestMapping(value = "admin/category/{index}/{count}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String getCategoryBySearch(@RequestParam(required = false) String category_name/* 分类名称 */,
                                      @PathVariable Integer index/* 页数 */,
                                      @PathVariable Integer count/* 行数 */) throws UnsupportedEncodingException {
        //移除不必要条件
        if (category_name != null) {
            //如果为非空字符串则解决中文乱码：URLDecoder.decode(String,"UTF-8");
            category_name = "".equals(category_name) ? null : URLDecoder.decode(category_name, "UTF-8");
        }

        JSONObject object = new JSONObject();
        logger.info("按条件获取第{}页的{}条分类", index + 1, count);
        PageUtil pageUtil = new PageUtil(index, count);
        List<Category> categoryList = categoryService.getList(category_name, pageUtil);
        object.put("categoryList", JSONArray.parseArray(JSON.toJSONString(categoryList)));
        logger.info("按条件获取分类总数量");
        Integer categoryCount = categoryService.getTotal(category_name);
        object.put("categoryCount", categoryCount);
        logger.info("获取分页信息");
        pageUtil.setTotal(categoryCount);
        object.put("totalPage", pageUtil.getTotalPage());
        object.put("pageUtil", pageUtil);

        return object.toJSONString();
    }

    /**
     * 上传分类图片-ajax
     * @param file 分类图片
     * @param session 用户与服务器通讯
     * @return 上传信息
     */
    @ResponseBody
    @RequestMapping(value = "admin/uploadCategoryImage", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String uploadCategoryImage(@RequestParam MultipartFile file, HttpSession session) {
        String originalFileName = file.getOriginalFilename();
        logger.info("获取图片原始文件名:  {}", originalFileName);
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String fileName = UUID.randomUUID() + extension;
        String filePath = session.getServletContext().getRealPath("/") + "res/images/item/categoryPicture/" + fileName;

        logger.info("文件上传路径：{}", filePath);
        JSONObject object = new JSONObject();
        try {
            logger.info("文件上传中...");
            file.transferTo(new File(filePath));
            logger.info("文件上传完成");
            object.put("success", true);
            object.put("fileName", fileName);
        } catch (IOException e) {
            logger.warn("文件上传失败!");
            e.printStackTrace();
            object.put("success", false);
        }

        return object.toJSONString();
    }
}