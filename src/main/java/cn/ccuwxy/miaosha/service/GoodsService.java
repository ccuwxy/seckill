package cn.ccuwxy.miaosha.service;

import cn.ccuwxy.miaosha.domain.Goods;
import cn.ccuwxy.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Goods)表服务接口
 *
 * @author makejava
 * @since 2020-03-21 23:17:14
 */

public interface GoodsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Goods queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Goods> queryAllByLimit(int offset, int limit);

    /**
     *  从商品表查询秒杀商品 信息
     * @return
     */
    List<GoodsVo> queryGoodsVoList();

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    Goods insert(Goods goods);

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    Goods update(Goods goods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    GoodsVo getGoodsVoByGoodsId(long goodsId);

    boolean reduceStock(GoodsVo goodsVo);
}