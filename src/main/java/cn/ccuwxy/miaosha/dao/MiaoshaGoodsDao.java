package cn.ccuwxy.miaosha.dao;

import cn.ccuwxy.miaosha.domain.MiaoshaGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * (MiaoshaGoods)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-21 23:17:27
 */
@Mapper
@Component
public interface MiaoshaGoodsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MiaoshaGoods queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MiaoshaGoods> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param miaoshaGoods 实例对象
     * @return 对象列表
     */
    List<MiaoshaGoods> queryAll(MiaoshaGoods miaoshaGoods);

    /**
     * 新增数据
     *
     * @param miaoshaGoods 实例对象
     * @return 影响行数
     */
    int insert(MiaoshaGoods miaoshaGoods);

    /**
     * 修改数据
     *
     * @param miaoshaGoods 实例对象
     * @return 影响行数
     */
    int update(MiaoshaGoods miaoshaGoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}