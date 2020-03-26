package cn.ccuwxy.miaosha.service;

import cn.ccuwxy.miaosha.domain.MiaoshaGoods;
import java.util.List;

/**
 * (MiaoshaGoods)表服务接口
 *
 * @author makejava
 * @since 2020-03-21 23:17:27
 */
public interface MiaoshaGoodsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MiaoshaGoods queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MiaoshaGoods> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param miaoshaGoods 实例对象
     * @return 实例对象
     */
    MiaoshaGoods insert(MiaoshaGoods miaoshaGoods);

    /**
     * 修改数据
     *
     * @param miaoshaGoods 实例对象
     * @return 实例对象
     */
    MiaoshaGoods update(MiaoshaGoods miaoshaGoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}