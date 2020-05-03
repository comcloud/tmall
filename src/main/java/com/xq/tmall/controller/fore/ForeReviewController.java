package com.xq.tmall.controller.fore;

import com.alibaba.fastjson.JSONObject;
import com.xq.tmall.controller.BaseController;
import com.xq.tmall.entity.*;
import com.xq.tmall.service.*;
import com.xq.tmall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author HP
 */
@Controller
public class ForeReviewController extends BaseController {
    /**
     * 反馈服务层
     */
    @Autowired
    private ReviewService reviewService;
    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;
    /**
     * 产品订单条目层
     */
    @Autowired
    private ProductOrderItemService productOrderItemService;
    /**
     * 产品订单层
     */
    @Autowired
    private ProductOrderService productOrderService;
    /**
     * 产平服务层
     */
    @Autowired
    private ProductService productService;
    /**
     * 产品图片服务层
     */
    @Autowired
    private ProductImageService productImageService;

    /**
     * @param session 用户与服务器通讯
     * @param map map存储值
     * @param orderItem_id 订单条目id
     * @return 评论添加页面
     */
    @RequestMapping(value = "review/{orderItem_id}", method = RequestMethod.GET)
    public String goToPage(HttpSession session, Map<String, Object> map,
                           @PathVariable("orderItem_id") Integer orderItem_id) {
        /**
         * 1.检查用户是否已经登录，登录则保存用户信息到map中，否则转发到登录界面
         * 2.根据订单项获取订单条目信息
         * 3.若订单不存在返回订单页面
         * 4.判断订单与用户，订单项与用户不匹配，返回订单页
         * 5.判断订单状态，若有误返回订单页
         * 6.获取订单项所属产品信息和产品评论信息
         * 7.保存信息到map然后转到评论添加页面
         * */
        logger.info("检查用户是否登录");
        Object userId = checkUser(session);
        User user;
        if (userId != null) {
            logger.info("获取用户信息");
            user = userService.get(Integer.parseInt(userId.toString()));
            map.put("user", user);
        } else {
            return "redirect:/login";
        }
        logger.info("获取订单项信息");
        ProductOrderItem orderItem = productOrderItemService.get(orderItem_id);
        if (orderItem == null) {
            logger.warn("订单项不存在，返回订单页");
            return "redirect:/order/0/10";
        }
        if (!orderItem.getProductOrderItem_user().getUser_id().equals(userId)) {
            logger.warn("订单项与用户不匹配，返回订单页");
            return "redirect:/order/0/10";
        }
        if (orderItem.getProductOrderItem_order() == null) {
            logger.warn("订单项状态有误，返回订单页");
            return "redirect:/order/0/10";
        }
        ProductOrder order = productOrderService.get(orderItem.getProductOrderItem_order().getProductOrder_id());
        if (order == null || order.getProductOrder_status() != 3) {
            logger.warn("订单项状态有误，返回订单页");
            return "redirect:/order/0/10";
        }
        if (reviewService.getTotalByOrderItemId(orderItem_id) > 0) {
            logger.warn("订单项所属商品已被评价，返回订单页");
            return "redirect:/order/0/10";
        }
        logger.info("获取订单项所属产品信息和产品评论信息");
        Product product = productService.get(orderItem.getProductOrderItem_product().getProduct_id());
        product.setProduct_review_count(reviewService.getTotalByProductId(product.getProduct_id()));
        product.setSingleProductImageList(productImageService.getList(product.getProduct_id(), (byte) 0, new PageUtil(0, 1)));
        orderItem.setProductOrderItem_product(product);

        map.put("orderItem", orderItem);

        logger.info("转到前台真食惠-评论添加页");
        return "fore/addReview";
    }

    /**
     * 添加评论，转发到产品详情页面
     * @param session 用户与服务通讯
     * @param map map存储值
     * @param orderItem_id 订单项id
     * @param review_content 反馈内容
     * @return 添加评论，转发到产品详情页面
     * @throws UnsupportedEncodingException
     */
    //添加一条评论
    @RequestMapping(value = "review", method = RequestMethod.POST)
    public String addReview(HttpSession session, Map<String, Object> map,
                            @RequestParam Integer orderItem_id,
                            @RequestParam String review_content) throws UnsupportedEncodingException {
        logger.info("检查用户是否登录");
        Object userId = checkUser(session);
        User user;
        if (userId != null) {
            logger.info("获取用户信息");
            user = userService.get(Integer.parseInt(userId.toString()));
            map.put("user", user);
        } else {
            return "redirect:/login";
        }
        logger.info("获取订单项信息");
        ProductOrderItem orderItem = productOrderItemService.get(orderItem_id);
        if (orderItem == null) {
            logger.warn("订单项不存在，返回订单页");
            return "redirect:/order/0/10";
        }
        if (!orderItem.getProductOrderItem_user().getUser_id().equals(userId)) {
            logger.warn("订单项与用户不匹配，返回订单页");
            return "redirect:/order/0/10";
        }
        if (orderItem.getProductOrderItem_order() == null) {
            logger.warn("订单项状态有误，返回订单页");
            return "redirect:/order/0/10";
        }
        ProductOrder order = productOrderService.get(orderItem.getProductOrderItem_order().getProductOrder_id());
        if (order == null || order.getProductOrder_status() != 3) {
            logger.warn("订单项状态有误，返回订单页");
            return "redirect:/order/0/10";
        }
        if (reviewService.getTotalByOrderItemId(orderItem_id) > 0) {
            logger.warn("订单项所属商品已被评价，返回订单页");
            return "redirect:/order/0/10";
        }
        logger.info("整合评论信息");
        Review review = new Review()
                .setReview_product(orderItem.getProductOrderItem_product())
                .setReview_content(new String(review_content.getBytes("ISO-8859-1"), "UTF-8"))
                .setReview_createDate(new Date())
                .setReview_user(user)
                .setReview_orderItem(orderItem);
        logger.info("添加评论");
        Boolean yn = reviewService.add(review);
        if (!yn) {
            throw new RuntimeException();
        }

        return "redirect:/product/" + orderItem.getProductOrderItem_product().getProduct_id();
    }

    /**
     * 获取产品评论信息-ajax
     * @param product_id 产品id
     * @param index 页数
     * @param count 行数
     * @return 获取产品评论信息-ajax
     */
    @ResponseBody
    @RequestMapping(value = "review", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String getReviewInfo(@RequestParam("product_id") Integer product_id,
                                @RequestParam("index") Integer index/* 页数 */,
                                @RequestParam("count") Integer count/* 行数*/) {
        /**
         * 1.根据产品id调用反馈服务层获取反馈集合
         * 2.对每个反馈调用用户服务层设置用户id
         * 3.获取反馈信息总量根据当前产品id
         * 4.返回jsonObject对象
         * */
        logger.info("获取产品评论信息");
        List<Review> reviewList = reviewService.getListByProductId(product_id, new PageUtil(index, 10));
        if (reviewList != null) {
            for (Review review : reviewList) {
                review.setReview_user(userService.get(review.getReview_user().getUser_id()));
            }
        }
        Integer total = reviewService.getTotalByProductId(product_id);

        JSONObject object = new JSONObject();
        object.put("reviewList", reviewList);
        object.put("pageUtil", new PageUtil().setTotal(total).setIndex(index).setCount(count));

        return object.toJSONString();
    }
}
