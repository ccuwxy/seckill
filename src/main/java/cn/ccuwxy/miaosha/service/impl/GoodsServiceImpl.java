package cn.ccuwxy.miaosha.service.impl;

import cn.ccuwxy.miaosha.domain.Goods;
import cn.ccuwxy.miaosha.dao.GoodsDao;
import cn.ccuwxy.miaosha.domain.MiaoshaGoods;
import cn.ccuwxy.miaosha.service.GoodsService;
import cn.ccuwxy.miaosha.vo.GoodsVo;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Goods)表服务实现类
 *
 * @author makejava
 * @since 2020-03-21 23:17:14
 */

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Goods queryById(Long id) {
        return this.goodsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Goods> queryAllByLimit(int offset, int limit) {
        return this.goodsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 从商品表查询秒杀商品 信息
     *
     * @return
     */
    @Override
    public List<GoodsVo> queryGoodsVoList() {
        return goodsDao.queryGoodsVoList();
    }

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    @Override
    public Goods insert(Goods goods) {
        this.goodsDao.insert(goods);
        return goods;
    }

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    @Override
    public Goods update(Goods goods) {
        this.goodsDao.update(goods);
        return this.queryById(goods.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.goodsDao.deleteById(id) > 0;
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {

        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    @Transactional
    @Override
    public boolean reduceStock(GoodsVo goodsVo) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goodsVo.getId());
        int ret = goodsDao.reduceStock(g);
        return ret > 0;
    }
}