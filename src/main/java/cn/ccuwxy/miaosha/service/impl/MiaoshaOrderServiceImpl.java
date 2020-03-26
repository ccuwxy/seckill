package cn.ccuwxy.miaosha.service.impl;

import cn.ccuwxy.miaosha.domain.MiaoshaOrder;
import cn.ccuwxy.miaosha.dao.MiaoshaOrderDao;
import cn.ccuwxy.miaosha.redis.OrderKey;
import cn.ccuwxy.miaosha.redis.RedisService;
import cn.ccuwxy.miaosha.service.MiaoshaOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MiaoshaOrder)表服务实现类
 *
 * @author makejava
 * @since 2020-03-21 23:17:38
 */
@Service("miaoshaOrderService")
public class MiaoshaOrderServiceImpl implements MiaoshaOrderService {
    @Autowired
    private MiaoshaOrderDao miaoshaOrderDao;

    @Autowired
    RedisService redisService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MiaoshaOrder queryById(Long id) {
        return this.miaoshaOrderDao.queryById(id);
    }


    @Override
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {

//        return this.orderInfoDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+userId+"_"+goodsId,MiaoshaOrder.class);
    }
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<MiaoshaOrder> queryAllByLimit(int offset, int limit) {
        return this.miaoshaOrderDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param miaoshaOrder 实例对象
     * @return 实例对象
     */
    @Transactional
    @Override
    public MiaoshaOrder insert(MiaoshaOrder miaoshaOrder) {
        this.miaoshaOrderDao.insert(miaoshaOrder);
        return miaoshaOrder;
    }

    /**
     * 修改数据
     *
     * @param miaoshaOrder 实例对象
     * @return 实例对象
     */
    @Override
    public MiaoshaOrder update(MiaoshaOrder miaoshaOrder) {
        this.miaoshaOrderDao.update(miaoshaOrder);
        return this.queryById(miaoshaOrder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.miaoshaOrderDao.deleteById(id) > 0;
    }

}