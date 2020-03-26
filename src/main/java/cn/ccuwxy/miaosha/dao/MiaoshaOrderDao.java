package cn.ccuwxy.miaosha.dao;

import cn.ccuwxy.miaosha.domain.MiaoshaOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * (MiaoshaOrder)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-21 23:17:38
 */
@Mapper
@Component
public interface MiaoshaOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MiaoshaOrder queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MiaoshaOrder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param miaoshaOrder 实例对象
     * @return 对象列表
     */
    List<MiaoshaOrder> queryAll(MiaoshaOrder miaoshaOrder);

    /**
     * 新增数据
     *
     * @param miaoshaOrder 实例对象
     * @return 影响行数
     */
    @Insert("insert into miaosha_order(user_id,goods_id,order_id) values(#{userId},#{goodsId},#{orderId})")
    int insert(MiaoshaOrder miaoshaOrder);

    /**
     * 修改数据
     *
     * @param miaoshaOrder 实例对象
     * @return 影响行数
     */
    int update(MiaoshaOrder miaoshaOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


}