package cn.ccuwxy.miaosha.service.impl;

import cn.ccuwxy.miaosha.domain.MiaoshaGoods;
import cn.ccuwxy.miaosha.dao.MiaoshaGoodsDao;
import cn.ccuwxy.miaosha.service.MiaoshaGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MiaoshaGoods)表服务实现类
 *
 * @author makejava
 * @since 2020-03-21 23:17:27
 */
@Service("miaoshaGoodsService")
public class MiaoshaGoodsServiceImpl implements MiaoshaGoodsService {
    @Resource
    private MiaoshaGoodsDao miaoshaGoodsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MiaoshaGoods queryById(Long id) {
        return this.miaoshaGoodsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<MiaoshaGoods> queryAllByLimit(int offset, int limit) {
        return this.miaoshaGoodsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param miaoshaGoods 实例对象
     * @return 实例对象
     */
    @Override
    public MiaoshaGoods insert(MiaoshaGoods miaoshaGoods) {
        this.miaoshaGoodsDao.insert(miaoshaGoods);
        return miaoshaGoods;
    }

    /**
     * 修改数据
     *
     * @param miaoshaGoods 实例对象
     * @return 实例对象
     */
    @Override
    public MiaoshaGoods update(MiaoshaGoods miaoshaGoods) {
        this.miaoshaGoodsDao.update(miaoshaGoods);
        return this.queryById(miaoshaGoods.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.miaoshaGoodsDao.deleteById(id) > 0;
    }
}