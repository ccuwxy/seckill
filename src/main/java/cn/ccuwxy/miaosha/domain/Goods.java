package cn.ccuwxy.miaosha.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;

/**
 * (Goods)实体类
 *
 * @author makejava
 * @since 2020-03-21 23:17:12
 */

public class Goods implements Serializable {
    private static final long serialVersionUID = -48323033816411192L;
    /**
    * 商品ID
    */
    private Long id;
    /**
    * 商品名称
    */
    private String goodsName;
    /**
    * 商品标题
    */
    private String goodsTitle;
    /**
    * 商品的图片
    */
    private String goodsImg;
    /**
    * 商品的详情介绍
    */
    private String goodsDetail;
    /**
    * 商品单价
    */
    private Double goodsPrice;
    /**
    * 商品库存, -1表示没有限制
    */
    private Integer goodsStock;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Object getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

}