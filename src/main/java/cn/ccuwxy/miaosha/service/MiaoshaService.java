package cn.ccuwxy.miaosha.service;

import cn.ccuwxy.miaosha.domain.OrderInfo;
import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.vo.GoodsVo;

import java.awt.image.BufferedImage;

public interface MiaoshaService {
    OrderInfo miaosha(User user, GoodsVo goodsVo);

    long getMiaoshaResult(Long id, long goodsId);


    boolean checkPath(User user, long goodsId, String path);

    String createMiaoshaPath(User user,long goodsId);

    BufferedImage createMiaoshaVerifyCode(User user, long goodsId);

    boolean checkVerifyCode(User user, long goodsId, int verifyCode);
}
