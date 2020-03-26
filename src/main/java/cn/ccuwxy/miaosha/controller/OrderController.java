package cn.ccuwxy.miaosha.controller;

import cn.ccuwxy.miaosha.access.AccessLimit;
import cn.ccuwxy.miaosha.domain.OrderInfo;
import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.redis.RedisService;
import cn.ccuwxy.miaosha.result.CodeMsg;
import cn.ccuwxy.miaosha.result.Result;
import cn.ccuwxy.miaosha.service.GoodsService;
import cn.ccuwxy.miaosha.service.OrderInfoService;
import cn.ccuwxy.miaosha.service.UserService;
import cn.ccuwxy.miaosha.vo.GoodsVo;
import cn.ccuwxy.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    GoodsService goodsService;

    @AccessLimit(seconds = 5,maxCount = 5,needLogin = true)
    @RequestMapping("/detail")
    @ResponseBody
//    @NeedLogin
    public Result<OrderDetailVo> detail(Model model, User user, @RequestParam("orderId") long orderId) {

        OrderInfo orderInfo = orderInfoService.queryById(orderId);

        if (orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        Long goodsId = orderInfo.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoodsVo(goodsVo);
        orderDetailVo.setOrderInfo(orderInfo);

        return Result.success(orderDetailVo);
    }


}
