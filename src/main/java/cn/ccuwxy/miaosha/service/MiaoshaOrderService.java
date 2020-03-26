package cn.ccuwxy.miaosha.service;

import cn.ccuwxy.miaosha.domain.MiaoshaOrder;
import java.util.List;

/**
 * (MiaoshaOrder)表服务接口
 *
 * @author makejava
 * @since 2020-03-21 23:17:38
 */
public interface MiaoshaOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MiaoshaOrder queryById(Long id);

    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MiaoshaOrder> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param miaoshaOrder 实例对象
     * @return 实例对象
     */
    MiaoshaOrder insert(MiaoshaOrder miaoshaOrder);

    /**
     * 修改数据
     *
     * @param miaoshaOrder 实例对象
     * @return 实例对象
     */
    MiaoshaOrder update(MiaoshaOrder miaoshaOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}