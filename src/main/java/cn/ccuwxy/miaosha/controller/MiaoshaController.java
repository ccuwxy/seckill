package cn.ccuwxy.miaosha.controller;

import cn.ccuwxy.miaosha.access.AccessLimit;
import cn.ccuwxy.miaosha.domain.MiaoshaOrder;
import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.rabbitmq.MQSender;
import cn.ccuwxy.miaosha.rabbitmq.MiaoshaMessage;
import cn.ccuwxy.miaosha.redis.AccessKey;
import cn.ccuwxy.miaosha.redis.GoodsKey;
import cn.ccuwxy.miaosha.redis.RedisService;
import cn.ccuwxy.miaosha.result.CodeMsg;
import cn.ccuwxy.miaosha.result.Result;
import cn.ccuwxy.miaosha.service.*;
import cn.ccuwxy.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    //    private static Logger log = LoggerFactory.getLogger(MiaoshaController.class);
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    MiaoshaOrderService miaoshaOrderService;

    @Autowired
    MQSender mqSender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();


    /**
     * 系统初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVos = goodsService.queryGoodsVoList();
        if (goodsVos == null)
            return;
        for (GoodsVo goodsVo : goodsVos) {
            redisService.set(GoodsKey.getMiaoshaGoodsDetail, goodsVo.getId() + "", goodsVo.getStockCount());
            localOverMap.put(goodsVo.getId(), false);
        }
    }

    /**
     * GET POST区别？
     * GET 幂等
     * POST 不是
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @AccessLimit(seconds = 5,maxCount = 5,needLogin = true)
    @RequestMapping(value = "/{path}/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model,
                                   User user,
                                   @RequestParam("goodsId") long goodsId,
                                   @PathVariable("path") String path) {
        if (user == null) {
            return Result.error(CodeMsg.USER_NOT_LOGIN);
        }
        //验证path
        boolean check = miaoshaService.checkPath(user, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        //内存标记，减少redis访问
        Boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //snowflake
        model.addAttribute("user", user);

        //预减库存
        Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsDetail, goodsId + "");
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断是否已经秒杀到
        MiaoshaOrder order = miaoshaOrderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_MIAOSHA);
        }

        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setGoodsId(goodsId);
        mm.setUser(user);
        mqSender.sendMiaoshaMessage(mm);
        return Result.success(0);



        /*
        //判断库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        Integer stockCount = goodsVo.getStockCount();
        if (stockCount <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断是否已经秒杀到
        MiaoshaOrder order = miaoshaOrderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_MIAOSHA);
        }
        //秒杀   减库存 下订单  写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);

        return Result.success(orderInfo);
         */

    }

    @AccessLimit(seconds = 5,maxCount = 5,needLogin = true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaPath(HttpServletRequest request,
                                         User user,
                                         @RequestParam("goodsId") long goodsId,
                                         @RequestParam(value = "verifyCode" ,defaultValue = "0") int verifyCode) {
//        if (user == null) {
//            return Result.error(CodeMsg.USER_NOT_LOGIN);
//        }

        boolean check = miaoshaService.checkVerifyCode(user,goodsId,verifyCode);
        if(!check){
            //
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String path = miaoshaService.createMiaoshaPath(user, goodsId);

        return Result.success(path);

    }

    /**
     * @param model
     * @param user
     * @param goodsId
     * @return orderId  成功
     * -1       失败
     * 0        排队中
     */
    @AccessLimit(seconds = 1,maxCount = 10,needLogin = true)
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, User user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.USER_NOT_LOGIN);
        }

        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);

        return Result.success(result);
    }


    @AccessLimit(seconds = 5,maxCount = 5,needLogin = true)
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> getMiaoshaVerifyCode(HttpServletResponse response, User user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.USER_NOT_LOGIN);
        }

        BufferedImage image = miaoshaService.createMiaoshaVerifyCode(user, goodsId);
        try {
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(CodeMsg.FAIL_MIAOSHA);
        }
    }

}
