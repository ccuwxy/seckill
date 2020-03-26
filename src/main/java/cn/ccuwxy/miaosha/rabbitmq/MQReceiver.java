package cn.ccuwxy.miaosha.rabbitmq;

import cn.ccuwxy.miaosha.domain.MiaoshaOrder;
import cn.ccuwxy.miaosha.domain.OrderInfo;
import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.redis.RedisService;
import cn.ccuwxy.miaosha.result.CodeMsg;
import cn.ccuwxy.miaosha.result.Result;
import cn.ccuwxy.miaosha.service.GoodsService;
import cn.ccuwxy.miaosha.service.MiaoshaOrderService;
import cn.ccuwxy.miaosha.service.MiaoshaService;
import cn.ccuwxy.miaosha.service.OrderInfoService;
import cn.ccuwxy.miaosha.vo.GoodsVo;
import com.mysql.jdbc.log.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

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

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receive(String message){
        logger.info(message);
        MiaoshaMessage msg = RedisService.stringToBean(message, MiaoshaMessage.class);
        Long goodsId = msg.getGoodsId();
        User user = msg.getUser();

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        //判断库存
        Integer stockCount = goodsVo.getStockCount();
        if (stockCount <= 0) {
            return;
        }

        //判断是否已经秒杀到
        MiaoshaOrder order = miaoshaOrderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        //秒杀   减库存 下订单  写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
    }


//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message) {
//        logger.info("receive message:"+message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message) {
//        logger.info("receiveTopic1 message:"+message);
//    }
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message) {
//        logger.info("receiveTopic2 message:"+message);
//    }
//
//    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
//    public void headersTopic(byte[] message) {
//        logger.info("headersTopic message:"+new String(message));
//    }

}
