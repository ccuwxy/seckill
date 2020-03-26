package cn.ccuwxy.miaosha.dao;

import cn.ccuwxy.miaosha.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-20 23:40:08
 */
@Component
@Mapper
public interface UserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select * from user where id = #{id}")
    User queryById(@Param("id") Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> queryAll(User user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    @Insert("insert into user(id,nickname,password,salt,head,register_data,last_login_data,login_count) " +
            "values(${id},${nickname},${password},${salt},${head},${registerData},${lastLoginData},${loginCount})")
    int insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    @Update("update user set last_login_date=#{lastLoginDate} where id = #{id}")
    int updateLastLoginDate(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    @Update("update user set password=#{password} where id = #{id}")
    void updatePassword(User toBeUpdate);
}