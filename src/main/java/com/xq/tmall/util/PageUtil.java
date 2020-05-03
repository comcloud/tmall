package com.xq.tmall.util;

/**
 * @author HP
 */
public final class PageUtil {
    /**
     * 索引存储在，作用：计算从数据库中第几组取
     * eg:index为1，此时取得数量为5，则表示从数据库中第1 * 5 行开始取 5 个
     */
    private Integer index;
    /**
     * 取得数量
     */
    private Integer count;
    /**
     * 总数
     */
    private Integer total;
    /**
     * 获取数据库数据行得开始位置
     */
    private Integer pageStart;

    public PageUtil(Integer index, Integer count) {
        this.index = index;
        this.count = count;
    }

    public Boolean isHasPrev(){
        return index >= 1;
    }

    public Boolean isHasNext(){
        return index + 1 < getTotalPage();
    }

    public Integer getTotalPage(){
        return (int) Math.ceil((double) total / count);
    }

    public PageUtil(){

    }

    public Integer getPageStart() {
        if (index != null) {
            return index * count;
        } else {
            return pageStart;
        }
    }

    public PageUtil setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public PageUtil setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public PageUtil setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public PageUtil setTotal(Integer total) {
        this.total = total;
        return this;
    }
}
