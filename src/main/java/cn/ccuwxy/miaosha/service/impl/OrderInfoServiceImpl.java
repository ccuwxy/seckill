package cn.ccuwxy.miaosha.service.impl;

import cn.ccuwxy.miaosha.domain.MiaoshaOrder;
import cn.ccuwxy.miaosha.domain.OrderInfo;
import cn.ccuwxy.miaosha.dao.OrderInfoDao;
import cn.ccuwxy.miaosha.domain.User;
import cn.ccuwxy.miaosha.redis.OrderKey;
import cn.ccuwxy.miaosha.redis.RedisService;
import cn.ccuwxy.miaosha.service.MiaoshaOrderService;
import cn.ccuwxy.miaosha.service.OrderInfoService;
import cn.ccuwxy.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (OrderInfo)表服务实现类
 *
 * @author makejava
 * @since 2020-03-21 23:20:49
 */

@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
    @Resource
    private OrderInfoDao orderInfoDao;

    @Autowired
    MiaoshaOrderService miaoshaOrderService;

    @Autowired
    RedisService redisService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderInfo queryById(Long id) {
        return this.orderInfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<OrderInfo> queryAllByLimit(int offset, int limit) {
        return this.orderInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param orderInfo 实例对象
     * @return 实例对象
     */
    @Override
    public OrderInfo insert(OrderInfo orderInfo) {
        this.orderInfoDao.insert(orderInfo);
        return orderInfo;
    }

    /**
     * 修改数据
     *
     * @param orderInfo 实例对象
     * @return 实例对象
     */
    @Override
    public OrderInfo update(OrderInfo orderInfo) {
        this.orderInfoDao.update(orderInfo);
        return this.queryById(orderInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.orderInfoDao.deleteById(id) > 0;
    }



    @Transactional
    @Override
    public OrderInfo createOrder(User user, GoodsVo goodsVo) {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrJid(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());

        orderInfoDao.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrderService.insert(miaoshaOrder);

        redisService.set(OrderKey.getMiaoshaOrderByUidGid,""+user.getId()+"_"+goodsVo.getId(),miaoshaOrder);
        return orderInfo;
    }
}