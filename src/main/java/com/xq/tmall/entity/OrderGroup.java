package com.xq.tmall.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author HP
 */
public class OrderGroup {
    /**
     * 产品订单支付时间
     */
    private Date productOrder_pay_date;
    /**
     * 订单数量
     */
    private Integer productOrder_count;
    /**
     * 订单状态
     */
    private Byte productOrder_status;

    public String getProductOrder_pay_date() {
        if (productOrder_pay_date != null) {
            SimpleDateFormat time = new SimpleDateFormat("MM/dd", Locale.UK);
            return time.format(productOrder_pay_date);
        }
        return null;
    }

    public void setProductOrder_pay_date(Date productOrder_pay_date) {
        this.productOrder_pay_date = productOrder_pay_date;
    }

    public Integer getProductOrder_count() {
        return productOrder_count;
    }

    public void setProductOrder_count(Integer productOrder_count) {
        this.productOrder_count = productOrder_count;
    }

    public Byte getProductOrder_status() {
        return productOrder_status;
    }

    public void setProductOrder_status(Byte productOrder_status) {
        this.productOrder_status = productOrder_status;
    }

    @Override
    public String toString() {
        return "OrderGroup{" +
                "productOrder_pay_date=" + productOrder_pay_date +
                ", productOrder_count=" + productOrder_count +
                ", productOrder_status=" + productOrder_status +
                '}';
    }
}
