package cn.ccuwxy.miaosha.domain;

import java.util.Date;
import java.io.Serializable;

/**
 * (MiaoshaGoods)实体类
 *
 * @author makejava
 * @since 2020-03-21 23:17:27
 */
public class MiaoshaGoods implements Serializable {
    private static final long serialVersionUID = 339872815910194201L;
    /**
    * 秒杀的商品表
    */
    private Long id;
    /**
    * 商品ld
    */
    private Long goodsId;
    /**
    * 秒杀价
    */
    private Double miaoshaPrice;
    /**
    * 库存数量
    */
    private Integer stockCount;
    /**
    * 秒杀开始时间
    */
    private Date startDate;
    /**
    * 秒杀结束时间
    */
    private Date endDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Double getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(Double miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}