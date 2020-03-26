package cn.ccuwxy.miaosha.rabbitmq;

import cn.ccuwxy.miaosha.domain.User;

public class MiaoshaMessage {
    private User user;
    private Long goodsId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
