package cn.ccuwxy.miaosha.domain;

import java.util.Date;
import java.io.Serializable;

/**
 * (OrderInfo)实体类
 *
 * @author makejava
 * @since 2020-03-21 23:20:49
 */
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = -71160560017318789L;
    
    private Long id;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 商品ID
    */
    private Long goodsId;
    /**
    * 收获地址ID
    */
    private Long deliveryAddrJid;
    /**
    * 冗余过来的商品名称
    */
    private String goodsName;
    /**
    * 商品数量
    */
    private Integer goodsCount;
    /**
    * 商品单价 
    */
    private Double goodsPrice;
    /**
    *  1pc, 2android, 3ios 
    */
    private Integer orderChannel;
    /**
    * 订单状态, 0新建未支付，1已支付, 2已发货, 3已收货, 4已退款, 5已完成
    */
    private Integer status;
    /**
    * 订单的创建时间
    */
    private Date createDate;
    /**
    *  支付时间
    */
    private Date payDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getDeliveryAddrJid() {
        return deliveryAddrJid;
    }

    public void setDeliveryAddrJid(Long deliveryAddrJid) {
        this.deliveryAddrJid = deliveryAddrJid;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

}