package com.xq.tmall.controller.fore;

import com.alibaba.fastjson.JSONObject;
import com.xq.tmall.controller.BaseController;
import com.xq.tmall.entity.Category;
import com.xq.tmall.entity.Product;
import com.xq.tmall.entity.User;
import com.xq.tmall.service.CategoryService;
import com.xq.tmall.service.ProductImageService;
import com.xq.tmall.service.ProductService;
import com.xq.tmall.service.UserService;
import com.xq.tmall.util.OrderUtil;
import com.xq.tmall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 主页
 *
 * @author HP
 */
@Controller
public class ForeHomeController extends BaseController {
    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;
    /**
     * 目录服务层
     */
    @Autowired
    private CategoryService categoryService;
    /**
     * 产品信息服务层
     */
    @Autowired
    private ProductService productService;
    /**
     * 产品图片服务层
     */
    @Autowired
    private ProductImageService productImageService;

    /**
     * @param session 用户与服务通讯
     * @param map     存储用户信息，商品分类，促销商品列表
     * @return 商城主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goToPage(HttpSession session, Map<String, Object> map) {
        /**
         * 1.检查用户是否已经登录
         *  - 登录调用用户服务层获取用户信息然后保存到map中
         * 2.获取产品目录列表
         * 3.获取每个产品分类下的产品列表然后设置到目录列表中
         * 4.获取促销产品集合
         * 5.目录集合以及促销产品集合中存储map
         * 6.返回到主页
         * */
        logger.info("检查用户是否登录");
        Object userId = checkUser(session);
        if (userId != null) {
            logger.info("获取用户信息");
            User user = userService.get(Integer.parseInt(userId.toString()));
            map.put("user", user);
        }
        logger.info("获取产品分类列表");
        List<Category> categoryList = categoryService.getList(null, null);
        logger.info("获取每个分类下的产品列表");
        for (Category category : categoryList) {
            logger.info("获取分类id为{}的产品集合，按产品ID倒序排序", category.getCategory_id());
            List<Product> productList = productService.getList(new Product().setProduct_category(category),
                    new Byte[]{0, 2},
                    new OrderUtil("product_id", true),
                    new PageUtil(0, 8));
            if (productList != null) {
                //遍历产品，设置这个产品下的所有图片集合
                for (Product product : productList) {
                    Integer product_id = product.getProduct_id();
                    logger.info("获取产品id为{}的产品预览图片信息", product_id);
                    product.setSingleProductImageList(productImageService.getList(product_id,(byte) 0, new PageUtil(0, 1)));
                }
            }
            category.setProductList(productList);
        }
        map.put("categoryList", categoryList);
        logger.info("获取促销产品列表");
        List<Product> specialProductList = productService.getList(null, new Byte[]{2}, null, new PageUtil(0, 6));
        map.put("specialProductList", specialProductList);

        logger.info("转到前台主页");
        return "fore/homePage";
    }

    /**
     * @return 商城错误页面
     */
    @RequestMapping(value = "error", method = RequestMethod.GET)
    public String goToErrorPage() {
        return "fore/errorPage";
    }

    /**
     * @param category_id 分类id
     * @return 主页分类下产品信息
     */
    @ResponseBody
    @RequestMapping(value = "product/nav/{category_id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String getProductByNav(@PathVariable("category_id") Integer category_id) {
        /**
         * 1.判断获取的目录id是否为空，为空的存储失败信息然后返回
         * 2.获取此id的产品集合
         * 3.对此集合判断先按五个一组存储二维集合中，最后多余不足5个的为一组存储二维集合中
         * 4.最后将成功信息存储，把目录存储然后返回
         * */
        JSONObject object = new JSONObject();
        if (category_id == null) {
            object.put("success", false);
            return object.toJSONString();
        }
        logger.info("获取分类ID为{}的产品标题数据", category_id);
        List<Product> productList = productService.getTitle(new Product().setProduct_category(new Category().setCategory_id(category_id)), new PageUtil(0, 40));
        List<List<Product>> complexProductList = new ArrayList<>(8);
        List<Product> products = new ArrayList<>(5);
        //实现一个目录存储五个产品
        for (int i = 0; i < productList.size(); i++) {
            //如果临时集合中产品数达到5个，加入到产品二维集合中，并重新实例化临时集合
            if (i % 5 == 0) {
                complexProductList.add(products);
                products = new ArrayList<>(5);
            }
            products.add(productList.get(i));
        }
        //把对%5剩下的产品存储二维集合
        complexProductList.add(products);
        Category category = new Category().setCategory_id(category_id).setComplexProductList(complexProductList);
        object.put("success", true);
        object.put("category", category);
        return object.toJSONString();
    }
}
