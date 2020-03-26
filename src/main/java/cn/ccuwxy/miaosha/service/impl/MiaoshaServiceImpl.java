package cn.ccuwxy.miaosha.service.impl;

import cn.ccuwxy.miaosha.dao.GoodsDao;
import cn.ccuwxy.miaosha.domain.Goods;
import cn.ccuwxy.miaosha.domain.MiaoshaOrder;
import cn.ccuwxy.miaosha.domain.OrderInfo;
import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.redis.MiaoshaKey;
import cn.ccuwxy.miaosha.redis.RedisService;
import cn.ccuwxy.miaosha.service.GoodsService;
import cn.ccuwxy.miaosha.service.MiaoshaOrderService;
import cn.ccuwxy.miaosha.service.MiaoshaService;
import cn.ccuwxy.miaosha.service.OrderInfoService;
import cn.ccuwxy.miaosha.util.MD5Util;
import cn.ccuwxy.miaosha.util.UUIDUtil;
import cn.ccuwxy.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class MiaoshaServiceImpl implements MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    MiaoshaOrderService miaoshaOrderService;

    @Autowired
    RedisService redisService;

    @Transactional
    @Override
    public OrderInfo miaosha(User user, GoodsVo goodsVo) {
        //减库存 下订单  写入秒杀订单
        boolean success = goodsService.reduceStock(goodsVo);

        if (success) {
            return orderInfoService.createOrder(user, goodsVo);
        } else {
            setGoodsOver(goodsVo.getId());
            return null;
        }
    }


    @Override
    public long getMiaoshaResult(Long id, long goodsId) {
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.getMiaoshaOrderByUserIdGoodsId(id, goodsId);
        if (miaoshaOrder != null) {
            return miaoshaOrder.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public boolean checkPath(User user, long goodsId, String path) {
        if (user == null || path == null)
            return false;
        String pathOld = redisService.get(MiaoshaKey.getMiaoshaPath, user.getId() + "_" + +goodsId, String.class);
        return path.equals(pathOld);

    }

    @Override
    public String createMiaoshaPath(User user, long goodsId) {

        String str = MD5Util.md5(UUIDUtil.uuid() + "123465");
        redisService.set(MiaoshaKey.getMiaoshaPath, user.getId() + "_" + goodsId, str);
        return str;
    }

    @Override
    public BufferedImage createMiaoshaVerifyCode(User user, long goodsId) {
        if (user == null || goodsId <= 0)
            return null;
        int width = 80;
        int height = 32;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width - 1, height - 1);

        Random rdm = new Random();

        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x,y,width-1,height-1);
        }
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.verity_code, user.getId() + "," + goodsId, rnd);
        return image;
    }

    @Override
    public boolean checkVerifyCode(User user, long goodsId, int verifyCode) {
        if (user == null || goodsId <= 0)
            return false;
        Integer codeOld = redisService.get(MiaoshaKey.verity_code, user.getId() + "," + goodsId, Integer.class);
        if (codeOld==null || codeOld-verifyCode!=0){
            return false;
        }
        redisService.del(MiaoshaKey.verity_code, user.getId() + "," + goodsId);
        return true;

    }

    //    public static void main(String[] args) {
//        String s = generateVerifyCode(new Random());
//        System.out.println(s);
//        System.out.println(calc(s));
//    }
    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);

        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    private static char[] ops = new char[]{'+', '-', '*'};

    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);

        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        return num1+"" + op1 +""+ num2 +""+ op2 +""+ num3 + "";

    }


    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, goodsId + "");

    }
}
